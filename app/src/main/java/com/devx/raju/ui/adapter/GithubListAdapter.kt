package com.devx.raju.ui.adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devx.raju.R
import com.devx.raju.data.local.entity.GithubEntity
import com.devx.raju.databinding.RepoListItemBinding
import com.devx.raju.ui.adapter.GithubListAdapter.CustomViewHolder
import com.devx.raju.ui.custom.recyclerview.RecyclerLayoutClickListener
import com.devx.raju.utils.AppUtils
import com.squareup.picasso.Picasso
import java.util.*

class GithubListAdapter(private val context: Context, private val listener: RecyclerLayoutClickListener) : RecyclerView.Adapter<CustomViewHolder>() {
    private val items: MutableList<GithubEntity>
    private val filteredItems: List<GithubEntity>
    override fun onCreateViewHolder(parent: ViewGroup, i: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = RepoListItemBinding.inflate(layoutInflater, parent, false)
        val customItemViewHolder = CustomViewHolder(itemBinding)
        itemBinding.cardView.setOnClickListener { v: View? -> customItemViewHolder.onLayoutButtonClick() }
        return customItemViewHolder
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    fun setItems(newItm: List<GithubEntity>) {
        items.clear()
        items.addAll(newItm)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItem(position: Int): GithubEntity {
        return items[position]
    }

    inner class CustomViewHolder(private val binding: RepoListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(githubEntity: GithubEntity) {
            Picasso.get().load(githubEntity.owner?.avatarUrl)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(binding.itemProfileImg)
            binding.itemTitle.text = githubEntity.fullName
            binding.itemTime.text = String.format(context.getString(R.string.item_date),
                    AppUtils.getDate(githubEntity.createdAt),
                    AppUtils.getTime(githubEntity.createdAt))
            binding.itemDesc.text = githubEntity.description
            if (githubEntity.language != null) {
                binding.itemImgLanguage.visibility = View.VISIBLE
                binding.itemLikes.visibility = View.VISIBLE
                binding.itemLikes.text = githubEntity.language
                val drawable = context.resources.getDrawable(R.drawable.ic_circle) as GradientDrawable
                drawable.setColor(AppUtils.getColorByLanguage(context, githubEntity.language))
                binding.itemImgLanguage.setImageDrawable(drawable)
            } else {
                binding.itemLikes.visibility = View.GONE
                binding.itemImgLanguage.visibility = View.GONE
            }
        }

        fun onLayoutButtonClick() {
            listener.redirectToDetailScreen(binding.itemProfileImg, binding.itemTitle, binding.itemImgLanguage, binding.itemLikes, getItem(layoutPosition))
        }

    }

    init {
        items = ArrayList()
        filteredItems = ArrayList()
    }
}