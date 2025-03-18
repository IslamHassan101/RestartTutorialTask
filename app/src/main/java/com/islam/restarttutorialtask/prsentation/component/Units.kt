package com.islam.restarttutorialtask.prsentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.islam.restarttutorialtask.prsentation.home.UnitItem


@Composable
fun Units(
    unitItem: UnitItem
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {},
        verticalAlignment = Alignment.CenterVertically
    ) {
        UnitView(number = unitItem.number)
        Column {
            Text(unitItem.title)
            Text(unitItem.description)
        }
    }
}