package com.example.whac_a_mole_game.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.whac_a_mole_game.R
import com.example.whac_a_mole_game.databinding.ItemMoleBinding
import com.example.whac_a_mole_game.models.Mole

class MoleAdapter(private val listener: OnMoleClickListener) :
    ListAdapter<Mole, MoleAdapter.MoleViewHolder>(diffUtil) {
    inner class MoleViewHolder(private val binding: ItemMoleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mole: Mole) {
            if (mole.isAppeared) {
                showMole(mole)
                binding.root.setOnClickListener {
                    val moleClicked = getItem(adapterPosition)
                    listener.onMoleClick(moleClicked)
                    hideMole()
                }
            } else {
                hideMole()
            }
        }

        private fun hideMole() {
            Glide.with(binding.root)
                .load(R.drawable.hole)
                .into(binding.ivMole)
        }

        private fun showMole(mole: Mole) {
            Glide.with(binding.root)
                .load(mole.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivMole)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Mole>() {
            override fun areItemsTheSame(oldItem: Mole, newItem: Mole): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Mole, newItem: Mole): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnMoleClickListener {
        fun onMoleClick(mole: Mole)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoleViewHolder {
        val binding = ItemMoleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}