package com.noobexon.xposedfakelocation.manager.ui.drawer

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.noobexon.xposedfakelocation.BuildConfig
import com.noobexon.xposedfakelocation.R
import com.noobexon.xposedfakelocation.manager.ui.navigation.Screen
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.Discord
import compose.icons.lineawesomeicons.Github
import compose.icons.lineawesomeicons.HeartSolid
import compose.icons.lineawesomeicons.InfoCircleSolid
import compose.icons.lineawesomeicons.MapMarkerAltSolid
import compose.icons.lineawesomeicons.MapSolid
import compose.icons.lineawesomeicons.MobileAltSolid
import compose.icons.lineawesomeicons.Telegram

private object DrawerDimensions {
    val SECTION_SPACING = 24.dp
    val ITEM_SPACING = 4.dp
    val ICON_SIZE = 24.dp
    val SECTION_PADDING = 8.dp
    val HEADER_PADDING = 16.dp
    val DRAWER_PADDING = 16.dp
    val ITEM_PADDING = 12.dp
    val ITEM_CORNER_RADIUS = 12.dp
}

@Composable
fun DrawerContent(
    navController: NavController,
    onCloseDrawer: () -> Unit = {}
) {
    val context = LocalContext.current

    ModalDrawerSheet(
        drawerContainerColor = MaterialTheme.colorScheme.surface,
        drawerContentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(DrawerDimensions.DRAWER_PADDING)
        ) {
            DrawerHeader()
            Spacer(modifier = Modifier.height(DrawerDimensions.SECTION_SPACING))
            DrawerSectionHeader(stringResource(R.string.drawer_navigation))

            DrawerItem(
                icon = LineAwesomeIcons.MapSolid,
                label = stringResource(R.string.drawer_map),
                onClick = {
                    navController.navigate(Screen.Map.route)
                    onCloseDrawer()
                },
                isSelected = navController.currentDestination?.route == Screen.Map.route
            )

            DrawerItem(
                icon = LineAwesomeIcons.HeartSolid,
                label = stringResource(R.string.screen_favorites),
                onClick = {
                    navController.navigate(Screen.Favorites.route)
                    onCloseDrawer()
                },
                isSelected = navController.currentDestination?.route == Screen.Favorites.route
            )

            DrawerItem(
                icon = LineAwesomeIcons.MobileAltSolid,
                label = stringResource(R.string.screen_target_apps),
                onClick = {
                    navController.navigate(Screen.TargetApps.route)
                    onCloseDrawer()
                },
                isSelected = navController.currentDestination?.route == Screen.TargetApps.route
            )

            DrawerItem(
                icon = LineAwesomeIcons.MapMarkerAltSolid,
                label = stringResource(R.string.screen_templates),
                onClick = {
                    navController.navigate(Screen.Templates.route)
                    onCloseDrawer()
                },
                isSelected = navController.currentDestination?.route == Screen.Templates.route
            )

            DrawerItem(
                icon = Icons.Default.Settings,
                label = stringResource(R.string.screen_settings),
                onClick = {
                    navController.navigate(Screen.Settings.route)
                    onCloseDrawer()
                },
                isSelected = navController.currentDestination?.route == Screen.Settings.route
            )

            Spacer(modifier = Modifier.height(DrawerDimensions.SECTION_SPACING))
            DrawerSectionHeader(stringResource(R.string.drawer_community))

            DrawerItem(
                icon = LineAwesomeIcons.Telegram,
                label = "Telegram",
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/XposedFakeLocationChat"))
                    context.startActivity(intent)
                    onCloseDrawer()
                }
            )

            DrawerItem(
                icon = LineAwesomeIcons.Discord,
                label = "Discord",
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://discord.gg/8eCRU3KzVS"))
                    context.startActivity(intent)
                    onCloseDrawer()
                }
            )

            DrawerItem(
                icon = LineAwesomeIcons.Github,
                label = "GitHub",
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/noobexon1/XposedFakeLocation"))
                    context.startActivity(intent)
                    onCloseDrawer()
                }
            )

            Spacer(modifier = Modifier.height(DrawerDimensions.SECTION_SPACING))
            DrawerSectionHeader(stringResource(R.string.drawer_app_info))

            DrawerItem(
                icon = LineAwesomeIcons.InfoCircleSolid,
                label = stringResource(R.string.screen_about),
                onClick = {
                    navController.navigate(Screen.About.route)
                    onCloseDrawer()
                },
                isSelected = navController.currentDestination?.route == Screen.About.route
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = stringResource(R.string.drawer_version, BuildConfig.VERSION_NAME),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                modifier = Modifier
                    .padding(DrawerDimensions.SECTION_PADDING)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun DrawerHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(DrawerDimensions.HEADER_PADDING)
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = stringResource(R.string.drawer_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun DrawerSectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(
            start = DrawerDimensions.SECTION_PADDING,
            bottom = DrawerDimensions.SECTION_PADDING
        )
    )
}

@Composable
fun DrawerItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    isSelected: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        Color.Transparent
    }

    val contentColor = if (isSelected) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = DrawerDimensions.ITEM_SPACING)
            .clip(RoundedCornerShape(DrawerDimensions.ITEM_CORNER_RADIUS))
            .clickable(onClick = onClick),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(DrawerDimensions.ITEM_PADDING),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(DrawerDimensions.ICON_SIZE),
                tint = contentColor
            )

            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = contentColor,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            trailingIcon?.invoke()
        }
    }
}
