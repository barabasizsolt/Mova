package com.barabasizsolt.seeall

import com.barabasizsolt.domain.model.WatchableItem

sealed class SeeAllListItem {

    abstract val id: String

    data class LoadMore(val nothing: Any? = null) : SeeAllListItem() {
        override val id: String = "loadMore"
    }

    data class Item(val watchableItem: WatchableItem) : SeeAllListItem() {
        override val id: String = watchableItem.id
    }
}