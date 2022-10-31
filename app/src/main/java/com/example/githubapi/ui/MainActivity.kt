package com.example.githubapi.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapi.ItemClick
import com.example.githubapi.RepositoryListAdapter
import com.example.githubapi.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ItemClick {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val pagingAdapter =
        RepositoryListAdapter(this@MainActivity, RepositoryListAdapter.SearchItemComparator)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initRecyclerView()
        initObservers()
    }

    private fun initViews() {
        binding.btnSearch.setOnClickListener {
            if (binding.edtOwner.text.isNullOrEmpty() || binding.edtRepo.text.isNullOrEmpty()) {
                Toast.makeText(this, "Please enter repo and owner", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.updateOwner(binding.edtOwner.text.toString())
            viewModel.updateRepo(binding.edtRepo.text.toString())
        }
    }


    private fun initRecyclerView() {
        binding.rvMain.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = pagingAdapter
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    override fun onItemClick(url: String?) {
        url ?: return
        val browserIntent = Intent("android.intent.action.VIEW", Uri.parse(url))
        startActivity(browserIntent)
    }
}