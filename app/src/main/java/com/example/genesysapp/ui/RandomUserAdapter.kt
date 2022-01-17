package com.example.genesysapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.genesysapp.R
import com.example.genesysapp.data.RandomUser
import com.example.genesysapp.databinding.ItemUserPhotoBinding

class RandomUserAdapter(private val listener: (RandomUser) -> Unit) : ListAdapter<RandomUser, RandomUserAdapter.RandomUserViewHolder>(RandomUserDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RandomUserViewHolder {
        val binding =
            ItemUserPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RandomUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RandomUserViewHolder, position: Int) {
        val item = getItem(position)

        item?.also {
            holder.bind(it)
        }
    }

    inner class RandomUserViewHolder(private val binding: ItemUserPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    item?.let{
                        listener(it)
                    }
                }
            }
        }

        fun bind(randomUser: RandomUser) {
            binding.apply {
                Glide.with(itemView)
                    .load(randomUser.picture.thumbnail)
                    .centerCrop()
                    .error(R.drawable.ic_error)
                    .into(imageView)

                val userInfo = "${randomUser.name.title}. ${randomUser.name.first} ${randomUser.name.last}"
                textViewUserName.text = userInfo
            }
        }
    }

    class RandomUserDiffUtil : DiffUtil.ItemCallback<RandomUser>() {
        override fun areItemsTheSame(oldItem: RandomUser, newItem: RandomUser) =
            oldItem.id.value == newItem.id.value

        override fun areContentsTheSame(oldItem: RandomUser, newItem: RandomUser) =
            oldItem == newItem
    }
}