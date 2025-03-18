package com.islam.restarttutorialtask.prsentation.questions


import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.islam.restarttutorialtask.R
import com.islam.restarttutorialtask.prsentation.component.TopBar
import com.islam.restarttutorialtask.prsentation.component.WritingCardGrid
import com.islam.restarttutorialtask.prsentation.ui.theme.blue
import com.islam.restarttutorialtask.prsentation.ui.theme.gray
import com.islam.restarttutorialtask.prsentation.ui.theme.lightGreen
import kotlinx.coroutines.delay

@Composable
fun QuestionScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(R.string.questions, R.drawable.notification)
        QuestionTabRow()
    }
}


@Composable
fun QuestionTabRow() {
    var tabIndex by rememberSaveable { mutableIntStateOf(0) }
    val tabs = listOf(
        TabItem(R.string.writing, R.drawable.edit_icon),
        TabItem(R.string.oral_content, R.drawable.mic_icon),
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        ScrollableTabRow(
            selectedTabIndex = tabIndex,
            containerColor = Color.White,
            edgePadding = 0.dp,
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
            tabs.forEachIndexed { index, tabItem ->
                Tab(
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    icon = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = tabItem.iconResId),
                                contentDescription = stringResource(id = tabItem.titleResId)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = stringResource(id = tabItem.titleResId))
                        }
                    },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = Color.Gray,
                )
            }
        }
        when (tabIndex) {
            0 -> WritingScreen()
            1 -> OralScreen()
        }
    }
}

data class TabItem(val titleResId: Int, val iconResId: Int)

@Composable
fun WritingScreen() {
    var targetPosition by remember { mutableStateOf(Offset.Zero) }
    var targetSize by remember { mutableStateOf(IntSize.Zero) }
    var showTooltip by remember { mutableStateOf(false) }
    Box(modifier = Modifier.padding(16.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            WritingCardGrid(modifier = Modifier.onGloballyPositioned {
                targetPosition = it.localToRoot(Offset.Zero)
                targetSize = it.size
            })
        }
        if (showTooltip) {
            LaunchedEffect(Unit) {
                delay(3000)
                showTooltip = false
            }
            TooltipBox(tooltipPosition = targetPosition, targetSize = targetSize) {
                TooltipContent(text = "Cliquez ici pour voir par catégories avec progression")
            }
        }
        LaunchedEffect(targetPosition) {
            if (targetPosition != Offset.Zero) {
                showTooltip = true
            }
        }
    }

}
@Composable
fun TooltipBox(
    tooltipPosition: Offset,
    targetSize: IntSize,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val tooltipHeight = with(density) { 40.dp.toPx() }
    val tooltipPadding = with(density) { 8.dp.toPx() }
    val xOffset = tooltipPosition.x
    val yOffset = tooltipPosition.y - tooltipHeight - tooltipPadding // Adjust Y position above the target

    Box(modifier = Modifier
        .offset(
            x = with(density) { xOffset.toDp() },
            y = with(density) { yOffset.toDp() }
        )
    ) {
        content()
    }
}

@Composable
fun TooltipContent(text: String) {
    Box(modifier = Modifier
        .background(Color.Black, RoundedCornerShape(8.dp))
        .padding(8.dp)
        .pointerInput(Unit) {
            detectTapGestures(
                onTap = {}
            )
        }, contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
        )
    }
}

@Composable
fun NewQuestionButton(onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = blue,
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.filter_list),
                contentDescription = stringResource(R.string.filter_icon),
                tint = blue
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.filter),
                color = blue,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun OralScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        QuestionsList(questions = generateQuestions())
    }
}

@Composable
fun QuestionsList(questions: List<QuestionData>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(questions) { question ->
            QuestionItem(question = question)
        }
    }
}

@Composable
fun QuestionItem(question: QuestionData) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    question.tags.forEach { tag ->
                        Tag(tag = tag)
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
                IconButton(onClick = { /* Handle more icon click */ }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.more_icon),
                        contentDescription = stringResource(R.string.more_icon),
                    )
                }
            }

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = question.questionText,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.description_icon),
                        tint = gray,
                        contentDescription = stringResource(R.string.document_icon)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(R.string.answers_count, question.answerCount),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Text(
                    text = question.date,
                    style = MaterialTheme.typography.labelMedium,
                    color = gray
                )
            }
        }
    }
}


@Composable
fun Tag(tag: String) {
    Text(
        text = tag,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(gray)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        color = Color.Black,
    )
}


data class QuestionData(
    val tags: List<String>,
    val questionText: String,
    val answerCount: Int,
    val date: String,
)

@Composable
private fun generateQuestions(): List<QuestionData> {
    return List(5) { index ->
        QuestionData(
            tags = if (index % 2 == 0) listOf("events", "Task") else listOf("general"),
            questionText = stringResource(R.string.loram_epsom),
            answerCount = index + 1,
            date = "13 May 2023"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun QuestionsPreview() {
    QuestionItem(
        QuestionData(
            tags = listOf("events", "Task"),
            questionText = stringResource(R.string.loram_epsom),
            answerCount = 11,
            date = "13 May 2023"
        )
    )
}