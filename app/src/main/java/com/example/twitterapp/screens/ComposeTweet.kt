package com.example.twitterapp.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.twitterapp.model.Tweet
import com.example.twitterapp.util.showToast
import com.example.twitterapp.viewmodel.TweetViewModel

@Composable
fun ComposeTweet(email: String?, navHostController: NavHostController) {

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }

    val viewModel: TweetViewModel = hiltViewModel()
    val loading = viewModel.isLoading.value

    LaunchedEffect(key1 = viewModel.postTweet) {
        viewModel.postTweet.collect {
            if (it.isNotEmpty()) {
                showToast(it)
                viewModel.getAllUserTweets()
                navHostController.popBackStack()
            }

        }
    }

    Box {
        SetupUIComponents(context, navHostController, imageUri, email, focusManager, viewModel)
        if (loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
private fun SetupUIComponents(
    context: Context,
    navHostController: NavHostController,
    imageUri: MutableState<Uri?>,
    email: String?,
    focusManager: FocusManager,
    viewModel: TweetViewModel
) {

    var postText by remember { mutableStateOf(TextFieldValue()) }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri.value = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Compose Tweet",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                modifier = Modifier.size(55.dp), // Adjust size as needed
                onClick = { navHostController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Close, // Replace with your icon resource
                    contentDescription = "Close"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        DisplaySelectedImage(imageUri, context, viewModel)

        Spacer(modifier = Modifier.height(16.dp))
        SelectImageButton(launcher)

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Tweet Content",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = postText,
            onValueChange = { postText = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        PostTweetButton(email, postText, imageUri, focusManager, viewModel)
    }
}

@Composable
private fun DisplaySelectedImage(
    imageUri: MutableState<Uri?>,
    context: Context,
    viewModel: TweetViewModel
) {

    val imageBitmap = remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(key1 = imageUri.value) {
        if (imageUri.value != null) {
            imageBitmap.value = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri.value)
            } else {
                val source = ImageDecoder.createSource(
                    context.contentResolver,
                    imageUri.value!!
                )
                ImageDecoder.decodeBitmap(source)
            }
        }
    }

    val compressedBitmap = remember { mutableStateOf<Bitmap?>(null) }
    LaunchedEffect(imageBitmap.value) {
        val compressed = imageBitmap.value?.let { viewModel.compressBitmap(it) }
        compressedBitmap.value = compressed
    }

    compressedBitmap.value?.let { bitmap ->
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "Selected Image",
            modifier = Modifier.size(200.dp)
        )
    }
}

@Composable
private fun SelectImageButton(launcher: ActivityResultLauncher<String>) {
    Button(
        onClick = { launcher.launch("image/*") }
    ) {
        Text(text = "Select Image")
    }
}

@Composable
private fun PostTweetButton(
    email: String?,
    postText: TextFieldValue,
    imageUri: MutableState<Uri?>,
    focusManager: FocusManager,
    viewModel: TweetViewModel
) {
    Button(
        onClick = {
            if (!email.isNullOrEmpty()) {
                viewModel.postTweet(
                    Tweet(
                        email = email,
                        postText = postText.text,
                        imgUploadUri = imageUri.value
                    )
                )
                focusManager.clearFocus()
            }
        }
    ) {
        Text(text = "Tweet")
    }
}

