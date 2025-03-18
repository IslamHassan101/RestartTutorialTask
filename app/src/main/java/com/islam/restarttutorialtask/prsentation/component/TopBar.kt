package com.islam.restarttutorialtask.prsentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.islam.restarttutorialtask.R
import com.islam.restarttutorialtask.prsentation.ui.theme.darkGreen


@Composable
fun TopBar(title: Int, icon: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stringResource(title), fontSize = 24.sp, color = darkGreen)
        Icon(
            imageVector = ImageVector.vectorResource(icon),
            contentDescription = stringResource(R.string.top_bar_icon),
            tint = Color.Unspecified
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TopBarPreview() {
    TopBar(title = R.string.home, icon = R.drawable.notification)
}