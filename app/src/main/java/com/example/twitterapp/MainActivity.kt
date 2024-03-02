package com.example.twitterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.twitterapp.ui.theme.TwitterAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                ListItem()
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 300, heightDp = 300)
@Composable
private fun ListItem(){

    val list = mutableListOf("One","Two","Three","Four","One","Two","Three","Four")

    LazyColumn(content =  {

        items(list){ item ->
            Row {
                Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "")
                Column(verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = item)
                    Text(text = "Una")
                }

            }
        }

    })


}
