package com.example.twitterapp.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.twitterapp.main.NavigationKeys.Arg.USER_EMAIL_ID
import com.example.twitterapp.screens.ComposeTweet
import com.example.twitterapp.screens.LoginSignupScreen
import com.example.twitterapp.screens.TimeLineScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TwitterApp()
        }
    }
}

@Composable
private fun  TwitterApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    var mEmailId = ""
    NavHost(navController, startDestination = NavigationKeys.Route.LOGIN_SIGNUP_SCREEN) {
        composable(route = NavigationKeys.Route.LOGIN_SIGNUP_SCREEN) {
            LoginSignupScreen(
                context = context
            ) { emailId ->
                mEmailId = emailId
                navController.navigate("${NavigationKeys.Route.TIME_LINE_SCREEN}/$emailId")
            }
        }
        composable(
            route = "${NavigationKeys.Route.TIME_LINE_SCREEN}/{${USER_EMAIL_ID}}",
            arguments = listOf(navArgument(USER_EMAIL_ID) {
                type = NavType.StringType
            })
        ) { navBackStackEntry ->
            val emailID = navBackStackEntry.arguments?.getString(USER_EMAIL_ID)
            TimeLineScreen(emailID, navController)
        }

        composable(route = NavigationKeys.Route.POST_TWEET_SCREEN) {
            ComposeTweet(context = context, email =  mEmailId, navHostController = navController)
        }
    }
}


object NavigationKeys {
    object Arg {
        const val USER_EMAIL_ID = "email_id"
    }

    object Route {

        const val LOGIN_SIGNUP_SCREEN = "login_signup"
        const val TIME_LINE_SCREEN = "time_line"
        const val POST_TWEET_SCREEN = "post_tweet"
    }

}


