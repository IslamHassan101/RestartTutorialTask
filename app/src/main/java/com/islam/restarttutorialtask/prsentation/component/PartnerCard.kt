package com.islam.restarttutorialtask.prsentation.component


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.islam.restarttutorialtask.R
import com.islam.restarttutorialtask.prsentation.connect.PartnerState
import com.islam.restarttutorialtask.prsentation.ui.theme.gGreen
import com.islam.restarttutorialtask.prsentation.ui.theme.lightGreen

@Composable
fun PartnerCard(
    modifier: Modifier= Modifier,
    partnerName: String,
    targetLabel: String,
    targetLevel: String,
    languages: List<String>,
    locations: List<PartnerState>,
    profileImageResId: Int
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            PartnerInfoRow(partnerName, targetLabel, targetLevel, languages, profileImageResId)
            Spacer(modifier = Modifier.height(16.dp))
            PartnerStateRow(locations)
        }
    }
}

@Composable
fun PartnerInfoRow(
    partnerName: String,
    targetLabel: String,
    targetLevel: String,
    languages: List<String>,
    profileImageResId: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ProfileImage(profileImageResId)
        Spacer(modifier = Modifier.width(8.dp))
        PartnerDetails(partnerName, targetLabel, targetLevel, languages)
    }
}

@Composable
fun ProfileImage(profileImageResId: Int) {
    Image(
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape),
        painter = painterResource(profileImageResId),
        contentDescription = stringResource(R.string.personal_img),
        contentScale = ContentScale.Crop,
    )
}

@Composable
fun PartnerDetails(
    partnerName: String, targetLabel: String, targetLevel: String, languages: List<String>
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = partnerName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = targetLabel,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(lightGreen)
                    .padding(horizontal = 6.dp, vertical = 4.dp),
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = targetLevel,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))


        LanguageRow(languages)
    }
}

@Composable
fun LanguageRow(languages: List<String>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        languages.forEach { language ->
            Text(
                text = language,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(gGreen)
                    .padding(horizontal = 6.dp, vertical = 4.dp),
                color = Color.Black
            )
        }
    }
}

@Composable
fun PartnerStateRow(locations: List<PartnerState>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(locations) { item ->
            ProfileState(item)
        }
    }
}

@Composable
fun ProfileState(item: PartnerState, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(item.icon),
            contentDescription = stringResource(R.string.icon_place)
        )
        Text(item.name, style = MaterialTheme.typography.bodyMedium)
    }
}
