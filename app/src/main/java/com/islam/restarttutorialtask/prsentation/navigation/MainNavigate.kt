package com.islam.restarttutorialtask.prsentation.navigation

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.islam.restarttutorialtask.R
import com.islam.restarttutorialtask.prsentation.auth.GoogleSignInClient
import jp.morux2.composeSpotlight.Spotlight

@Composable
fun MainNavigate(
    context: Context,
    scope: LifecycleCoroutineScope,
    googleSignInClient: GoogleSignInClient
) {
    val navController = rememberNavController()
    var targetRect by remember { mutableStateOf<Rect?>(null) }
    var showSpotlight by remember { mutableStateOf(false) }
    var targetPosition by remember { mutableStateOf(Offset.Zero) }
    var targetSize by remember { mutableStateOf(IntSize.Zero) }
    var showTooltip by remember { mutableStateOf(false) }
    var currentRoute by remember { mutableStateOf<String?>(null) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    currentRoute = navBackStackEntry?.destination?.route
    var isFirstTime by remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Login.route) {
                BottomNavigationBar(
                    navController = navController,
                    modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                        targetRect = layoutCoordinates.boundsInRoot()
                        // Calculate the position relative to the root layout
                        targetPosition = layoutCoordinates.localToRoot(Offset.Zero)
                        targetSize = layoutCoordinates.size
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxWidth()) {
            NavHost(
                navController = navController,
                startDestination = "auth_graph",
                modifier = Modifier.padding(innerPadding)
            ) {
                authGraph(navController, context, scope, googleSignInClient)
                mainGraph(navController, context, scope, googleSignInClient)
            }

            //Spotlight code
            if (showSpotlight && targetRect != null && isFirstTime) {
                Spotlight(
                    targetRect = targetRect!!,
                    onTargetClicked = {
                        Toast.makeText(
                            context,
                            "Target Clicked !!",
                            Toast.LENGTH_SHORT
                        ).show()
                        showSpotlight = false
                        showTooltip = false
                    },
                    onDismiss = {
                        showSpotlight = false
                        showTooltip = false
                        isFirstTime = false
                    }
                )
            }

            // Tooltip code
            if (currentRoute != Screen.Login.route && showTooltip && isFirstTime) {
                BottomBarTooltip(
                    targetPosition = targetPosition,
                    targetSize = targetSize,
                    currentRoute = currentRoute,
                )
            }
        }
    }
    //LaunchedEffect for showing spotlight and tooltip
    LaunchedEffect(targetRect, isFirstTime) {
        if (targetRect != null && isFirstTime) {
            showSpotlight = true
            showTooltip = true
        }
    }
    // LaunchedEffect for reset the isFirstTime
    LaunchedEffect(currentRoute) {
        isFirstTime = true
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, modifier: Modifier = Modifier) {
    val screens =
        listOf(
            Screen.Home,
            Screen.Connect,
            Screen.Question,
            Screen.Tools,
            Screen.Profile
        )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar(modifier = modifier) {
        screens.forEach { screen ->
            NavigationBarItem(
                label = { Text(text = stringResource(screen.title)) },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(screen.icon),
                        contentDescription = ""
                    )
                },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        launchSingleTop = true
                        popUpTo(navController.graph.findStartDestination().id)
                    }
                }
            )
        }
    }
}

@Composable
fun BottomBarTooltip(
    targetPosition: Offset,
    targetSize: IntSize,
    currentRoute: String?,
) {
    var tooltipText = ""

    tooltipText = when (currentRoute) {
        Screen.Home.route -> stringResource(R.string.home_description)
        Screen.Connect.route -> stringResource(R.string.connect_description)
        Screen.Question.route -> stringResource(R.string.question_description)
//        Screen.Tools.route -> stringResource(R.string.tools_description)
//        Screen.Profile.route -> stringResource(R.string.profile_description)
        else -> ""
    }
    TooltipBox(
        tooltipPosition = targetPosition,
        targetSize = targetSize,
        isAbove = true,
        isCenter = true,
    ) {
        TooltipContent(text = tooltipText)
    }
}

@Composable
fun TooltipBox(
    tooltipPosition: Offset,
    targetSize: IntSize,
    isAbove: Boolean = false,
    isCenter: Boolean = false,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val tooltipHeight = with(density) { 40.dp.toPx() }
    val tooltipWidth = with(density) { 150.dp.toPx() }
    val tooltipPadding = with(density) { 8.dp.toPx() }

    val xOffset = if (isCenter) {
        tooltipPosition.x - tooltipWidth / 2 + targetSize.width / 2
    } else {
        tooltipPosition.x
    }
    val yOffset = if (isAbove) {
        tooltipPosition.y - tooltipHeight - tooltipPadding
    } else {
        tooltipPosition.y + targetSize.height + tooltipPadding
    }

    Box(
        modifier = Modifier
            .padding(16.dp)
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
    Box(
        modifier = Modifier
            .background(Color.Black, RoundedCornerShape(8.dp))
            .padding(8.dp),
        contentAlignment = Alignment.Center
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

