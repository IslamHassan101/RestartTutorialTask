package com.islam.restarttutorialtask.prsentation.component


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.islam.restarttutorialtask.R
import com.islam.restarttutorialtask.prsentation.ui.theme.gray
import com.islam.restarttutorialtask.prsentation.ui.theme.lightGreen
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.compose.Balloon
import com.skydoves.balloon.compose.BalloonWindow
import com.skydoves.balloon.compose.rememberBalloonBuilder
import com.skydoves.balloon.overlay.BalloonOverlayOval


data class CardData(
    val title: String,
    val progress: Float,
    val progressText: String,
    val imageRes: Int,
    val questions: String
)

private fun generateCardData(): List<CardData> {
    return listOf(
        CardData(
            title = "Voyage",
            progress = 0.75f,
            progressText = "Progress 75%",
            imageRes = R.drawable.fly_img,
            questions = "10 sur 10 Questions",
        ),
        CardData(
            title = "Food",
            progress = 0.5f,
            progressText = "Progress 50%",
            imageRes = R.drawable.fly_img,
            questions = "5 sur 10 Questions",

            ),
        CardData(
            title = "Work",
            progress = 0.25f,
            progressText = "Progress 25%",
            imageRes = R.drawable.fly_img,
            questions = "2 sur 10 Questions",

            ),
        CardData(
            title = "Sports",
            progress = 1.0f,
            progressText = "Progress 100%",
            imageRes = R.drawable.fly_img,
            questions = "10 sur 10 Questions",

            ),
        CardData(
            title = "Health",
            progress = 0.9f,
            progressText = "Progress 90%",
            imageRes = R.drawable.fly_img,
            questions = "9 sur 10 Questions",

            ),
        CardData(
            title = "Family",
            progress = 0.4f,
            progressText = "Progress 40%",
            imageRes = R.drawable.fly_img,
            questions = "4 sur 10 Questions",

            ),
    )
}


@Composable
fun WritingCardGrid() {
    val cardData = remember { generateCardData() }


    var balloonWindow: BalloonWindow? by remember { mutableStateOf(null) }

    val builder = rememberBalloonBuilder {
        setText("Voici les questions avec des réponses modèles!")
        setArrowSize(10)
        setWidthRatio(1.0f)
        setHeight(BalloonSizeSpec.WRAP)
        setArrowOrientation(ArrowOrientation.BOTTOM)
        setArrowPosition(0.2f)
        setPadding(12)
        setMarginHorizontal(12)
        setTextSize(15f)
        setCornerRadius(8f)
    }


    Balloon(
        builder = builder,
        onBalloonWindowInitialized = { balloonWindow = it },
        onComposedAnchor = { balloonWindow?.showAlignTop() },

    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(cardData) { card ->
                WritingCard(cardData = card)
            }
        }
    }
}

@Composable
fun WritingCard(
    cardData: CardData
) {
    ElevatedCard(modifier = Modifier) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = cardData.questions,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(gray)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                color = Color.Black,
            )
            Spacer(modifier = Modifier.size(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(cardData.imageRes),
                    contentDescription = stringResource(
                        R.string.plant_img
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(cardData.title, style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = cardData.progressText,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            LinearProgressIndicator(
                progress = { cardData.progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                color = lightGreen,
                trackColor = gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WritingCardPreview() {
    WritingCardGrid()
}