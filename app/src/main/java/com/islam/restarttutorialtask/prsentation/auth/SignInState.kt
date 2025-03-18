package com.islam.restarttutorialtask.prsentation.auth

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
