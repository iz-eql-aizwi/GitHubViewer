package com.siyonaga.practice.githubviewer.home

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.siyonaga.practice.githubviewer.model.Order

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreenSearchBar(
    word: String,
    order: Order,
    enable: Boolean,
    modifier: Modifier = Modifier,
    onSearchClicked: (String) -> Unit,
    onSortButtonClicked: () -> Unit,
) {
    var input by rememberSaveable { mutableStateOf(word) }
    var expanded by rememberSaveable { mutableStateOf(false) }

    // 初期フォーカスを SearchBar ではなく、SearchBar より左上の仮想コンポーザブルに設定する
    Box(modifier = Modifier.size(1.dp).focusable())
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = input,
                    onQueryChange = { input = it },
                    onSearch = {
                        onSearchClicked(it)
                        expanded = false
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = {
                        Text(text = stringResource(R.string.search_placeholder))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.search_label),
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = onSortButtonClicked,
                            enabled = enable,
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.sort),
                                contentDescription = order.toContentDescription(),
                                modifier = order.toFlipModifier(),
                            )
                        }
                    },
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            // No Content
        }
    }
}

@Composable
private fun Order.toContentDescription(): String {
    return when (this) {
        Order.ASC -> stringResource(R.string.change_to_desc)
        Order.DESC -> {
            stringResource(R.string.change_to_asc)
        }
    }
}

private fun Order.toFlipModifier(): Modifier {
    return when (this) {
        Order.ASC -> Modifier.graphicsLayer { this.scaleY = -1.0f }
        Order.DESC -> Modifier
    }
}
