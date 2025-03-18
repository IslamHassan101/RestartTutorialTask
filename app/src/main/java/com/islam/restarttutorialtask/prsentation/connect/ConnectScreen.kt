package com.islam.restarttutorialtask.prsentation.connect

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.islam.restarttutorialtask.R
import com.islam.restarttutorialtask.prsentation.component.PartnerCard
import com.islam.restarttutorialtask.prsentation.component.TopBar
import com.islam.restarttutorialtask.prsentation.ui.theme.darkGreen
import com.islam.restarttutorialtask.prsentation.ui.theme.lightGreen


data class PartnerData(
    val partnerName: String,
    val targetLabel: String,
    val targetLevel: String,
    val languages: List<String>,
    val state: List<PartnerState>,
    val profileImageResId: Int
)

data class PartnerState(val name: String, val icon: Int)

private fun generatePartnerData(): List<PartnerData> {
    return List(5) {
        PartnerData(
            partnerName = "Reem Sayed $it",
            targetLabel = "Targeting: B1",
            targetLevel = "Last seen online:yesterday",
            languages = listOf("English", "Arabic", "French"),
            state = listOf(
                PartnerState("Egypt", R.drawable.location_icon),
                PartnerState("Female", R.drawable.female_icon),
                PartnerState("25", R.drawable.cake_icon),
                PartnerState("21 June 2023", R.drawable.calendar_icon),
            ),
            profileImageResId = R.drawable._36
        )
    }
}


@Composable
fun ConnectScreen(onCardClick: (PartnerData) -> Unit = {}) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(title = R.string.connect, icon = R.drawable.notification)
        ConnectTabRow(onCardClick = onCardClick)
    }
}

@Composable
fun ConnectTabRow(onCardClick: (PartnerData) -> Unit) {
    var tabIndex by rememberSaveable { mutableIntStateOf(0) }
    val tabs = listOf(
        R.string.suggestions,
        R.string.chat
    )
    Column(modifier = Modifier.fillMaxWidth()) {
        ScrollableTabRow(
            selectedTabIndex = tabIndex,
            containerColor = Color.White,
            edgePadding = 8.dp,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                    color = lightGreen
                )
            },
            divider = {
                HorizontalDivider(color = Color.Unspecified)
            }
        ) {
            tabs.forEachIndexed { index, tabResId ->
                Tab(
                    text = { Text(text = stringResource(id = tabResId)) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    selectedContentColor = lightGreen,
                    unselectedContentColor = Color.Gray,
                )
            }
        }
        when (tabIndex) {
            0 -> SuggestionsScreen(onCardClick = onCardClick)
            1 -> ChatScreen()
        }
    }
}


@Composable
fun SuggestionsScreen(onCardClick: (PartnerData) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        SuggestionsContent(onCardClick = onCardClick)
    }
}

@Composable
private fun SuggestionsContent(modifier: Modifier = Modifier, onCardClick: (PartnerData) -> Unit) {
    val partners = rememberSaveable { generatePartnerData() }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.suggested_study_partner),
                style = MaterialTheme.typography.titleMedium,
                color = darkGreen
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.filter_list),
                tint = lightGreen,
                contentDescription = stringResource(R.string.filter_icon)
            )
        }
        PartnerCardList(partners = partners, onCardClick = onCardClick)
    }
}


@Composable
fun PartnerCardList(partners: List<PartnerData>, onCardClick: (PartnerData) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(partners) { partner ->
            PartnerCard(
                modifier = Modifier.clickable { onCardClick(partner) },
                partnerName = partner.partnerName,
                targetLabel = partner.targetLabel,
                targetLevel = partner.targetLevel,
                languages = partner.languages,
                locations = partner.state,
                profileImageResId = partner.profileImageResId
            )
        }
    }
}

@Composable
fun ChatScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.chat),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun PartnerCardListPreview() {
    val partners = remember { generatePartnerData() }
    PartnerCardList(partners = partners, onCardClick = {})
}