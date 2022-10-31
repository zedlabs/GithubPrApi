package com.example.githubapi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.githubapi.data.GithubPagingSource
import com.example.githubapi.data.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val repo = MutableStateFlow("Wallportal")
    private val owner = MutableStateFlow("zedlabs")

    fun updateRepo(update: String) {
        repo.value = update
    }

    fun updateOwner(update: String) {
        owner.value = update
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    val flow = repo.flatMapLatest { user ->
        Pager(PagingConfig(pageSize = 10)) {
            GithubPagingSource(repository, owner.value, user)
        }.flow
            .cachedIn(viewModelScope)
    }

}