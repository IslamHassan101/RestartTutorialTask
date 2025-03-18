package com.islam.restarttutorialtask.prsentation.navigation

import com.islam.restarttutorialtask.R


sealed class Screen(val route: String, val title: Int, val icon: Int) {


    //Auth routs
    object Login : Screen("login", R.string.home, 0)

    //Main routes
    object Home : Screen("home", R.string.home, R.drawable.home_gray)
    object Connect : Screen("Connect", R.string.connect, R.drawable.unselected_connect_icon)
    object Question : Screen("Question", R.string.questions, R.drawable.unselected_help)
    object Tools : Screen("Tools", R.string.tools, R.drawable.unselected_tools_icon)
    object Profile : Screen("Profile", R.string.profile, R.drawable.unselected_profile)


}