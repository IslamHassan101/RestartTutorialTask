package com.islam.restarttutorialtask.prsentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.identity.Identity
import com.islam.restarttutorialtask.prsentation.auth.GoogleSignInClient
import com.islam.restarttutorialtask.prsentation.navigation.MainNavigate
import com.islam.restarttutorialtask.prsentation.ui.theme.RestartTutorialTaskTheme


class MainActivity : ComponentActivity() {

    private val googleSignInClient by lazy {
        GoogleSignInClient(
            context = applicationContext, onTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RestartTutorialTaskTheme {

                val context = LocalContext.current
                val scope = lifecycleScope

                MainNavigate(context, scope, googleSignInClient)

            }
        }
    }
}
