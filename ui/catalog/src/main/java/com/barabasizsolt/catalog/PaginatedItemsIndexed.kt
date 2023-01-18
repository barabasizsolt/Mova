package com.barabasizsolt.catalog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

inline fun <T> LazyGridScope.paginatedItemsIndexed(
    items: List<T>,
    crossinline onLoadMoreItem: () -> Unit,
    noinline key: ((index: Int, item: T) -> Any)? = null,
    noinline span: (LazyGridItemSpanScope.(index: Int, item: T) -> GridItemSpan)? = null,
    crossinline contentType: (index: Int, item: T) -> Any? = { _, _ -> null },
    crossinline itemContent: @Composable LazyGridItemScope.(index: Int, item: T) -> Unit
) {
    itemsIndexed(
        items = items,
        key = key,
        span =  span,
        contentType = contentType
    ) { index, item ->
        itemContent(index, item)
        if (index == items.lastIndex) { SideEffect { onLoadMoreItem() } }
    }
    item(span = { GridItemSpan(currentLineSpan = 6) }) {
        LoadingContent(modifier = Modifier
            .height(height = 80.dp)
            .fillMaxWidth())
    }
}