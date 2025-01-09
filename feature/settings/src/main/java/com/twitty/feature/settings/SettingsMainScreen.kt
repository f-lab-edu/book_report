package com.twitty.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.twitty.designsystem.icon.BookReportIcons
import com.twitty.feature.settings.navigation.SettingsRoute

@Composable
fun SettingsMainScreen(
    onNavigateToAlarm: () -> Unit,
    onNavigateToTag: () -> Unit,
    onNavigateToBackup: () -> Unit,
    onNavigateToTheme: () -> Unit,
    onNavigateToFeedback: () -> Unit,
) {
    Surface {
        Column {
            UserInfo(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 32.dp)
                    .wrapContentSize()
            )
            SettingsList(
                onNavigateToAlarm,
                onNavigateToTag,
                onNavigateToBackup,
                onNavigateToTheme,
                onNavigateToFeedback,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UserInfo(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        GlideImage(
            model = null,
            contentDescription = null,
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
                .background(Color.Gray)
                .clickable {
                    // TODO: Camera on
                }
        )
        Text(
            text = "User Name",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp)
        )
    }

}


@Composable
fun SettingsList(
    onNavigateToAlarm: () -> Unit,
    onNavigateToTag: () -> Unit,
    onNavigateToBackup: () -> Unit,
    onNavigateToTheme: () -> Unit,
    onNavigateToFeedback: () -> Unit,
    modifier: Modifier = Modifier
) {
    val settings = listOf(
        SettingsRoute.Alarm to stringResource(id = R.string.alarm_setting),
        SettingsRoute.Tag to stringResource(id = R.string.tag_management),
        SettingsRoute.Backup to stringResource(id = R.string.backup_and_sync),
        SettingsRoute.Theme to stringResource(id = R.string.theme),
        SettingsRoute.Feedback to stringResource(id = R.string.user_feedback_and_improvement),
    )

    LazyColumn(modifier = modifier) {
        items(settings) { (route, setting) ->
            ConstraintLayout(
                modifier = modifier
                    .clickable {
                        when (route) {
                            SettingsRoute.Alarm -> onNavigateToAlarm()
                            SettingsRoute.Tag -> onNavigateToTag()
                            SettingsRoute.Backup -> onNavigateToBackup()
                            SettingsRoute.Theme -> onNavigateToTheme()
                            SettingsRoute.Feedback -> onNavigateToFeedback()
                        }
                    }
            ) {
                val (title, icon) = createRefs()
                Text(
                    text = setting,
                    modifier = Modifier
                        .constrainAs(title) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                        }
                        .padding(16.dp)
                )
                Icon(
                    BookReportIcons.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier
                        .constrainAs(icon) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        }
                        .padding(16.dp)
                )
            }
        }

        item {
            Text(
                text = stringResource(id = R.string.reset_settings),
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    }
}
