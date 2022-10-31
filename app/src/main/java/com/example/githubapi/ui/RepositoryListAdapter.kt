package com.example.githubapi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapi.databinding.ItemRepoListBinding
import com.example.githubapi.models.PrListItem

class RepositoryListAdapter(
    private val onItemClick: ItemClick,
    diffCallback: DiffUtil.ItemCallback<PrListItem>,
) : PagingDataAdapter<PrListItem, RepositoryListAdapter.ItemViewHolder>(diffCallback) {

    inner class ItemViewHolder(private val binding: ItemRepoListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PrListItem?) {
            item?.let { pullRequest ->
                // Profile Image
                Helper.loadImage(pullRequest.user?.avatar_url, binding.ivUserImage)
                // PR Title
                binding.tvRepoName.text = pullRequest.title ?: ""
                // User Name
                binding.tvUserName.text = pullRequest.user?.login ?: ""
                //Created at
                pullRequest.created_at?.let { binding.tvCreatedDate.text = Helper.getDateTime(it) }
                //Closed at
                pullRequest.closed_at?.let { binding.tvClosedDate.text = Helper.getDateTime(it) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemRepoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onItemClick.onItemClick(getItem(position)?.html_url)
        }
    }

    object SearchItemComparator : DiffUtil.ItemCallback<PrListItem>() {
        override fun areItemsTheSame(oldItem: PrListItem, newItem: PrListItem): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PrListItem, newItem: PrListItem): Boolean {
            return oldItem == newItem
        }
    }

}

interface ItemClick {
    fun onItemClick(url: String?)
}