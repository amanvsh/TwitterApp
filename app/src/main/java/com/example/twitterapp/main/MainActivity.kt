package com.example.twitterapp.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.twitterapp.screens.ComposeTweet
import com.example.twitterapp.screens.LoginSignupScreen
import com.example.twitterapp.screens.TimeLineScreen
import com.example.twitterapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var mEmailId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val userViewModel: UserViewModel = hiltViewModel()
            if (userViewModel.isUserLoggedIn().isNullOrEmpty()) {
                TwitterApp(null, NavigationKeys.Route.LOGIN_SIGNUP_SCREEN)
            }else{
                TwitterApp(
                    loginEmailId = userViewModel.isUserLoggedIn(),
                    NavigationKeys.Route.TIME_LINE_SCREEN
                )
            }
        }
    }
}

@Composable
private fun  TwitterApp(loginEmailId: String?, launchScreen: String) {
    var emailId = loginEmailId
    val navController = rememberNavController()
    val context = LocalContext.current
    NavHost(navController, startDestination = launchScreen) {

        composable(route = NavigationKeys.Route.LOGIN_SIGNUP_SCREEN) {
            LoginSignupScreen{
                emailId = it
                navController.navigate(route = NavigationKeys.Route.TIME_LINE_SCREEN){
                    popUpTo(NavigationKeys.Route.LOGIN_SIGNUP_SCREEN){ run { inclusive = true } }
                }
            }
        }
        composable(
            route = NavigationKeys.Route.TIME_LINE_SCREEN
        ) {
            TimeLineScreen(emailID = emailId, navController = navController)
        }

        composable(route = NavigationKeys.Route.POST_TWEET_SCREEN) {
            ComposeTweet(email =  emailId, navHostController = navController)
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


