package com.islam.restarttutorialtask.prsentation.tools

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.islam.restarttutorialtask.R
import com.islam.restarttutorialtask.prsentation.component.TopBar


@Composable
fun ToolScreen() {
    Column(modifier = Modifier.fillMaxWidth()) {
        TopBar(R.string.tools, R.drawable.notification)
    }
}