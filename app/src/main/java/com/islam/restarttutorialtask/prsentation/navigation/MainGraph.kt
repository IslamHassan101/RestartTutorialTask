package com.islam.restarttutorialtask.prsentation.navigation

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.islam.restarttutorialtask.prsentation.auth.GoogleSignInClient
import com.islam.restarttutorialtask.prsentation.connect.ConnectScreen
import com.islam.restarttutorialtask.prsentation.home.HomeScreen
import com.islam.restarttutorialtask.prsentation.profile.ProfileScreen
import com.islam.restarttutorialtask.prsentation.questions.QuestionScreen
import com.islam.restarttutorialtask.prsentation.tools.ToolScreen
import kotlinx.coroutines.launch


fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
    context: Context,
    scope: LifecycleCoroutineScope,
    googleSignInClient: GoogleSignInClient
) {
    navigation(startDestination = Screen.Home.route, route = "main_graph") {
        composable(Screen.Home.route) {
            HomeScreen()
        }
        composable(Screen.Connect.route) {
            ConnectScreen()
        }
        composable(Screen.Question.route) {
            QuestionScreen()
        }
        composable(Screen.Tools.route) {
            ToolScreen()
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
                userData = googleSignInClient.getSignInUser(),
                onSignOut = {
                    scope.launch {
                        googleSignInClient.signOut()
                        Toast.makeText(
                            context,
                            "Singed Out",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.popBackStack()

                    }
                },
            )
        }
    }
}

