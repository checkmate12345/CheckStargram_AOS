package com.checkmate.checkstagram.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.checkmate.checkstagram.R
import com.checkmate.checkstagram.databinding.ItemCensuredSentenceBinding

import com.checkmate.checkstagram.presentation.model.CensuredSentence


class CensuredSentenceAdapter():ListAdapter<CensuredSentence, CensuredSentenceAdapter.ViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemCensuredSentenceBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemCensuredSentenceBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CensuredSentence) {
            Log.d("jomi", "item : $item")
            binding.itemTvCensured.text = item.text
            binding.itemTvCensured.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    if (item.censured) R.color.red else R.color.black
                )
            )
        }
    }

    companion object {
        private val diffUtil = object :DiffUtil.ItemCallback<CensuredSentence>() {
            override fun areItemsTheSame(
                oldItem: CensuredSentence,
                newItem: CensuredSentence
            ): Boolean = oldItem.text == newItem.text

            override fun areContentsTheSame(
                oldItem: CensuredSentence,
                newItem: CensuredSentence
            ): Boolean = oldItem == newItem
        }
    }
}
