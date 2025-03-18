package com.islam.restarttutorialtask.prsentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.islam.restarttutorialtask.R
import com.islam.restarttutorialtask.prsentation.component.TopBar
import com.islam.restarttutorialtask.prsentation.component.Units
import com.islam.restarttutorialtask.prsentation.ui.theme.darkGreen

data class UnitItem(
    val number: String, val title: String, val description: String
)

@Composable
fun HomeScreen(
    unitItems: List<UnitItem> = remember { getUnitItems() }
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TopBar(R.string.home, R.drawable.notification)
        SectionTitle(text = stringResource(R.string.hi_user_name))
        Spacer(modifier = Modifier.height(16.dp))
        SectionTitle(text = stringResource(R.string.study_plan))
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(
                items = unitItems,
                 key = { it.number }
            ) { unitItem ->
                Units(unitItem = unitItem)
            }
        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        modifier = Modifier
            .padding(start = 16.dp)
            .padding(8.dp),
        text = text,
        color = darkGreen,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold
    )
}

private fun getUnitItems(): List<UnitItem> {
    return listOf(
        UnitItem(
            number = "1",
            title = "Unit 1",
            description = "what is TFC"
        ),
        UnitItem(
            number = "2",
            title = "Unit 2",
            description = "how to apply it"
        ),
        UnitItem(
            number = "3",
            title = "Unit 3",
            description = "test it"
        ),
        UnitItem(
            number = "4",
            title = "",
            description = "Writing Task"
        ),
        UnitItem(
            number = "5",
            title = "",
            description = "Oral Task"
        ),
        UnitItem(
            number = "6",
            title = "Unit 3",
            description = "test it"
        ),
        UnitItem(
            number = "7",
            title = "Unit 3",
            description = "test it"
        )
    )
}

