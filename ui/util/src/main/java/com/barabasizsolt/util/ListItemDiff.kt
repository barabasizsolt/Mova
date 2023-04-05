package com.barabasizsolt.util


import androidx.recyclerview.widget.DiffUtil

/**
 * DiffUtil that can be used for all implementations of [ListItem], to reduce code duplication.
 */
class ListItemDiff<T : ListItem> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem

    override fun getChangePayload(oldItem: T, newItem: T) = ""
}
