package com.example.twitterapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.twitterapp.main.NavigationKeys
import com.example.twitterapp.model.Tweet
import com.example.twitterapp.viewmodel.UserViewModel
import com.example.twitterapp.viewmodel.TweetViewModel


@Composable
fun TimeLineScreen(emailID: String?, navController: NavController) {

    val tweetsList = remember { mutableStateOf(emptyList<Tweet>()) }
    val tweetsViewModel: TweetViewModel = hiltViewModel()
    val userViewModel: UserViewModel = hiltViewModel()
    val loading = tweetsViewModel.isLoading.value
    LaunchedEffect(key1 = Unit) {
        if (emailID != null) {
            tweetsViewModel.getAllUserTweets()
            tweetsViewModel.userTweetList.collect {
                tweetsList.value = it
            }
        }
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                PostTweet(navController = navController)
                AppLogout(navController = navController, userViewModel)
            }
            TweetsTab(emailID = emailID, tweetsList = tweetsList)
        }
        if (loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

}

@Composable
fun TweetsTab(emailID: String?, tweetsList: MutableState<List<Tweet>>){
    val tabs = listOf("All Tweets", "My Tweets")
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    TabRow(selectedTabIndex = selectedTabIndex) {
        tabs.forEachIndexed { index, title ->
            Tab(
                text = { Text(title) },
                selected = selectedTabIndex == index,
                onClick = { selectedTabIndex = index }
            )
        }
    }

    when (selectedTabIndex) {
        0 -> {
            LazyColumn {
                items(tweetsList.value) { item ->
                    TweetCard(item)
                }
            }
        }
        1 -> {
            val filteredMyTweets: List<Tweet> = tweetsList.value.filter { it.email == emailID }
            LazyColumn {
                items(filteredMyTweets) { item ->
                    TweetCard(item)
                }
            }
        }
    }

}

@Composable
fun TweetCard(item: Tweet) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
                vertical = 8.dp
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        top = 16.dp,
                        bottom = 16.dp
                    )
            ) {
                Text(
                    text = item.email,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(start = 10.dp, bottom = 4.dp)
                )
                Text(
                    text = item.postText,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
            AsyncImage(
                model = item.imgDownloadUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun PostTweet(navController: NavController){
    FloatingActionButton(
        onClick = { navController.navigate(NavigationKeys.Route.POST_TWEET_SCREEN) },
        modifier = Modifier
            .fillMaxWidth(0.4f)
            .padding(10.dp)
    ) {
        Text("Post Tweet",
            Modifier.padding(5.dp),
        )
    }
}

@Composable
fun AppLogout(navController: NavController, userViewModel: UserViewModel){
    FloatingActionButton(
        onClick = {
            userViewModel.logOutUser()
            //clear all previous compose screens and redirect to login-signup page
            navController.popBackStack(
                route = NavigationKeys.Route.TIME_LINE_SCREEN,
                inclusive = true
            )
            navController.navigate(NavigationKeys.Route.LOGIN_SIGNUP_SCREEN)
                  },
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(10.dp)
    ) {
        Text(" Logout ",
            Modifier.padding(5.dp),

            )
    }
}



