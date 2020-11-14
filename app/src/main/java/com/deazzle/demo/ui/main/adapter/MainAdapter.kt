package com.deazzle.demo.ui.main.adapter

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deazzle.demo.R
import com.deazzle.demo.data.model.Results
import com.deazzle.demo.databinding.ItemLayoutBinding
import com.deazzle.demo.utils.RecyclerViewClickListener

class MainAdapter(
    private val users: ArrayList<Results>,
    private val listener: RecyclerViewClickListener
) : RecyclerView.Adapter<MainAdapter.UserHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder =
        UserHolder(
            DataBindingUtil.inflate<ItemLayoutBinding>
                (
                LayoutInflater.from(parent.context),
                R.layout.item_layout,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.recyclerviewUserBinding.user = users[position]
        Glide.with(holder.itemView)
            .load(users[position].picture.large)
            .into(holder.recyclerviewUserBinding.imageViewAvatar)

        if (users[position].status != 0) {
            if (users[position].status == 1)
                holder.recyclerviewUserBinding.textStatus.text = "Accepted"
            else
                holder.recyclerviewUserBinding.textStatus.text = "Decline"

            holder.recyclerviewUserBinding.btnAccept.visibility = GONE
            holder.recyclerviewUserBinding.btnDecline.visibility = GONE
        } else {
            holder.recyclerviewUserBinding.btnAccept.visibility = VISIBLE
            holder.recyclerviewUserBinding.btnDecline.visibility = VISIBLE
        }

        holder.recyclerviewUserBinding.btnAccept.setOnClickListener {
            listener.onRecyclerViewItemClick(
                holder.recyclerviewUserBinding.btnAccept,
                position + 1
            )
            users[position].status = 1
            notifyItemChanged(position)
        }

        holder.recyclerviewUserBinding.btnDecline.setOnClickListener {
            listener.onRecyclerViewItemClick(
                holder.recyclerviewUserBinding.btnDecline,
                position + 1
            )
            users[position].status = 2
            notifyItemChanged(position)
        }
    }

    fun addUsers(users: List<Results>) {
        this.users.apply {
            clear()
            addAll(users)
        }
    }

    inner class UserHolder(val recyclerviewUserBinding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(recyclerviewUserBinding.root) {
    }
}