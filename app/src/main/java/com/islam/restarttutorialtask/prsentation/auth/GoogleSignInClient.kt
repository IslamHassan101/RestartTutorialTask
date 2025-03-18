package com.islam.restarttutorialtask.prsentation.auth

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.islam.restarttutorialtask.R
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class GoogleSignInClient(
    private val context: Context, private val onTapClient: SignInClient
) {

    private val auth = Firebase.auth

    suspend fun signIn(): IntentSender? {
        val result = try {
            onTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    suspend fun signInWithIntent(intent: Intent): SignInResult {
        val credential = onTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)

        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = user.uid,
                        userName = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    )
                }, errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(
                data = null, errorMessage = e.message
            )
        }
    }

    suspend fun signOut() {
        try {
            onTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    fun getSignInUser(): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid, userName = displayName, profilePictureUrl = photoUrl.toString()
        )
    }

    //TODO fix the deprecation soon using Credential Manager and passKey
    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder().setGoogleIdTokenRequestOptions(
            GoogleIdTokenRequestOptions.Builder().setSupported(true)
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(context.getString(R.string.web_client_id)).build()
        ).setAutoSelectEnabled(true).build()
    }
}