package com.islam.restarttutorialtask.prsentation.navigation

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.islam.restarttutorialtask.prsentation.auth.GoogleSignInClient


@Composable
fun MainNavigate(
    context: Context,
    scope: LifecycleCoroutineScope,
    googleSignInClient: GoogleSignInClient
) {
    val navController = rememberNavController()

    var currentRoute by remember { mutableStateOf<String?>(null) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Login.route) {
                BottomNavigationBar(
                    navController = navController,
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
        }
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

