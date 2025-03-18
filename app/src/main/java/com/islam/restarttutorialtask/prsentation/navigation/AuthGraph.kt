package com.islam.restarttutorialtask.prsentation.navigation

import android.app.Activity.RESULT_OK
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.islam.restarttutorialtask.prsentation.auth.GoogleSignInClient
import com.islam.restarttutorialtask.prsentation.auth.SignInScreen
import com.islam.restarttutorialtask.prsentation.auth.SignInViewModel
import kotlinx.coroutines.launch

fun NavGraphBuilder.authGraph(
    navController: NavHostController,
    context: Context,
    scope: LifecycleCoroutineScope,
    googleSignInClient: GoogleSignInClient
) {
    navigation(startDestination = Screen.Login.route, route = "auth_graph") {
        composable(Screen.Login.route) {
            val viewModel = viewModel<SignInViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()


            LaunchedEffect(key1 = Unit) {
                if (googleSignInClient.getSignInUser() != null) {
                    navController.navigate("main_graph")
                }
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == RESULT_OK) {
                        scope.launch {
                            val signInResult = googleSignInClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            viewModel.onSignInResult(signInResult)
                        }
                    }
                })

            LaunchedEffect(state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    Toast.makeText(
                        context,
                        "sing in successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate("main_graph")
                    viewModel.restState()
                }
            }

            SignInScreen(state = state, onSingInClick = {
                scope.launch {
                    val singInIntentSender = googleSignInClient.signIn()
                    launcher.launch(
                        IntentSenderRequest.Builder(
                            singInIntentSender ?: return@launch
                        ).build()
                    )
                }
            })
        }
    }
}
