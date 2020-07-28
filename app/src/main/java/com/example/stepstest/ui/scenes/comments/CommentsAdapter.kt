package com.example.stepstest.ui.scenes.comments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stepstest.R
import com.example.stepstest.data.model.Comment

class CommentsAdapter(var list: ArrayList<Comment>) :
    RecyclerView.Adapter<CommentsAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun add(list: List<Comment>) {
        val start = this.list.size
        val end = list.size
        this.list.addAll(list)
        notifyItemRangeInserted(start, end)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    open inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var id: TextView = view.findViewById(R.id.tvValueId)
        var name: TextView = view.findViewById(R.id.tvValueName)
        var email: TextView = view.findViewById(R.id.tvValueEmail)
        var comment: TextView = view.findViewById(R.id.tvValueComment)


        fun bind(comment: Comment) {
            this.id.text = comment.id.toString()
            this.name.text = comment.name
            this.email.text = comment.email
            this.comment.text = comment.body
        }
    }
}