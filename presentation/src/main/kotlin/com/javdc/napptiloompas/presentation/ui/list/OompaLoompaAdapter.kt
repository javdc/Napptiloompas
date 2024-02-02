package com.javdc.napptiloompas.presentation.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.javdc.napptiloompas.presentation.R
import com.javdc.napptiloompas.presentation.databinding.RowOompaLoompaBinding
import com.javdc.napptiloompas.presentation.model.OompaLoompaVo

class OompaLoompaAdapter(private val onClickItem: (View, OompaLoompaVo) -> Unit) : ListAdapter<OompaLoompaVo, OompaLoompaAdapter.ViewHolder>(lineDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowOompaLoompaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { onClickItem(holder.itemView, item) }
    }

    inner class ViewHolder(private val binding: RowOompaLoompaBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: OompaLoompaVo) {
            binding.apply {
                root.transitionName = root.context.getString(R.string.row_oompa_loompa_transition_name, item.id)
                rowOompaNameTextView.text = item.name
                rowOompaProfessionTextView.text = item.profession?.ordinal?.let { root.resources.getStringArray(R.array.professions).getOrNull(it) }
                rowOompaAgeTextView.text = item.age?.let { root.resources.getQuantityString(R.plurals.age_years_old, it, it) }
                Glide
                    .with(root.context)
                    .load(item.image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .circleCrop()
                    .into(rowOompaProfilePictureImageView)
            }
        }

    }

    private companion object {
        private val lineDiffCallback = object : DiffUtil.ItemCallback<OompaLoompaVo>() {
            override fun areItemsTheSame(oldItem: OompaLoompaVo, newItem: OompaLoompaVo): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: OompaLoompaVo, newItem: OompaLoompaVo): Boolean =
                oldItem == newItem
        }
    }
}