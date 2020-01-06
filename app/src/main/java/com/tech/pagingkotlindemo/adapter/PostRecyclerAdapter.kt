package com.tech.pagingkotlindemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tech.pagingkotlindemo.R
import com.tech.pagingkotlindemo.models.PostItem


class PostRecyclerAdapter(private val mPostItems: MutableList<PostItem>?) :
    RecyclerView.Adapter<BaseViewHolder>() {
    private var isLoaderVisible = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
            )
            VIEW_TYPE_LOADING -> ProgressHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            )
            else -> ProgressHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoaderVisible) {
            if (position == mPostItems!!.size - 1) VIEW_TYPE_LOADING else VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_NORMAL
        }
    }

    override fun getItemCount(): Int {
        return mPostItems?.size ?: 0
    }

    fun addItems(postItems: List<PostItem>) {
        mPostItems!!.addAll(postItems)
        notifyDataSetChanged()
    }

    fun addLoading() {
        isLoaderVisible = true
        mPostItems!!.add(PostItem())
        notifyItemInserted(mPostItems.size - 1)
    }

    fun removeLoading() {
        isLoaderVisible = false
        val position = mPostItems!!.size - 1
        val item = getItem(position)
        if (item != null) {
            mPostItems.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        mPostItems!!.clear()
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): PostItem? {
        return mPostItems!![position]
    }

    inner class ViewHolder internal constructor(itemView: View) : BaseViewHolder(itemView) {
        var textViewTitle: TextView? = null
        var textViewDescription: TextView? = null

        init {
            textViewTitle = itemView.findViewById(R.id.textViewTitle)
            textViewDescription = itemView.findViewById(R.id.textViewDescription)
        }

        override fun clear() {

        }

        override fun onBind(position: Int) {
            super.onBind(position)
            val item = mPostItems!![position]

            textViewTitle!!.text = item.title
            textViewDescription!!.text = item.description
        }
    }

    inner class ProgressHolder internal constructor(itemView: View) : BaseViewHolder(itemView) {
        var progressBar: ProgressBar? = null

        init {
            progressBar = itemView.findViewById(R.id.progressBar)
        }

        override fun clear() {}
    }

    companion object {
        private val VIEW_TYPE_LOADING = 0
        private val VIEW_TYPE_NORMAL = 1
    }
}

