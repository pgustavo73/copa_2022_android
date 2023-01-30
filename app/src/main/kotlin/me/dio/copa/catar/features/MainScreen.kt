package me.dio.copa.catar.features

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.dio.copa.catar.R
import me.dio.copa.catar.domain.extensions.getDate
import me.dio.copa.catar.domain.model.MatchDomain
import me.dio.copa.catar.domain.model.TeamDomain
import me.dio.copa.catar.ui.theme.Shapes

typealias NotificationOnClick = (match: MatchDomain) -> Unit

@Composable
fun MainScreen(matches: List<MatchDomain>, onNotificationClick: NotificationOnClick) {
    Background()
    Logo()
    Header()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
            .background(Color.Transparent),
    ) {
        LazyColumn(
            Modifier.padding(top = 80.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            items(matches) { match ->
                MatchInfo(match, onNotificationClick)
            }
        }
    }
}

@Composable
fun Background() {
    val gradient = Brush.verticalGradient(
        0.0f to Color.Black,
        5.0f to colorResource(R.color.Cup_color),
        startY = 0.0f,
        endY = 4500.0f
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    )
}

@Composable
fun Logo() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ){
        Image(
            painter = painterResource(id = R.drawable.ic_fifa_logo),
            modifier = Modifier
                .size(100.dp),
            contentDescription = null,
        )
    }
}

@Composable
fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(top = 15.dp)
            .background(Color.Transparent),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Copa do Mundo",
            style = MaterialTheme.typography.h4.copy(color = colorResource(R.color.Cup_color)),
        )
    }
}

@Composable
fun MatchInfo(match: MatchDomain, onNotificationClick: NotificationOnClick) {
    Card(
        shape = Shapes.large,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .height(120.dp),
        border = BorderStroke(
            1.dp, colorResource(R.color.white)
        )
    ) {
        Box {
            AsyncImage(
                model = match.stadium.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(120.dp)
            )

            Column(modifier = Modifier.padding(6.dp)) {
                Notification(match, onNotificationClick)
                Title(match)
                Teams(match)
            }
        }
    }
}

@Composable
fun Notification(match: MatchDomain, onClick: NotificationOnClick) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        val drawable = if (match.notificationEnabled) R.drawable.ic_notifications_active
        else R.drawable.ic_notifications

        Image(
            painter = painterResource(id = drawable),
            modifier = Modifier.clickable {
                onClick(match)
            },
            contentDescription = null
        )
    }
}

@Composable
fun Title(match: MatchDomain) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${match.date.getDate()} - ${match.name}",
            style = MaterialTheme.typography.h6.copy(color = Color.White)
        )
    }
}

@Composable
fun Teams(match: MatchDomain) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TeamItem(team = match.team1)

        Text(
            text = "X",
            modifier = Modifier.padding(end = 16.dp, start = 16.dp),
            style = MaterialTheme.typography.h6.copy(color = Color.White)
        )

        TeamItem(team = match.team2)
    }
}

@Composable
fun TeamItem(team: TeamDomain) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = team.flag,
            modifier = Modifier.align(Alignment.CenterVertically),
            style = MaterialTheme.typography.h3.copy(color = Color.White)
        )

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = team.displayName,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h6.copy(color = Color.White)
        )
    }
}