package com.example.image

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.image.ui.theme.ImageTheme
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting() {
    var start by remember { mutableStateOf(0) }
    var imageSize by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }
    var selectedValue by remember { mutableStateOf(0) }
    var select by remember { mutableStateOf(0) }
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Random Image", fontSize = 26.sp, modifier = Modifier.padding(20.dp))
        TextField(
            value = value,
            label = { Text(text = "Image size") },
            onValueChange = { newText ->
                value = newText
            }
        )


        Row (
            modifier = Modifier.selectable(
                selected = selectedValue == 0,
                onClick = {
                    selectedValue = 1
                }
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedValue == 1,
                onClick = {
                    selectedValue = 1
                }
            )
            Text("RectangleShape")
        }

        Row (
            modifier = Modifier.selectable(
                selected = selectedValue == 2,
                onClick = {
                    selectedValue = 2
                }
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedValue == 2,
                onClick = {
                    selectedValue = 2
                }
            )
            Text("CircleShape")
        }


        Button(onClick = { imageSize = value
                    select = selectedValue
                    start = CheckStart(select = select)
                })
        {
            Text("Show Image")
        }
        ShowImage(start = start,value = imageSize, selectedValue = select)
    }
}

fun CheckStart(select: Int): Int {
    if (select != 0) {
        return  1
    }
    else{
        return 0
    }
}

fun CheckSelect(selected: Int): Shape {
    if (selected == 2) {
        return CircleShape
    }
    else {
        return RectangleShape
    }
}

@Composable
fun ShowImage(start: Int, value: String, selectedValue: Int) {
    var shape = CheckSelect(selected = selectedValue)
    if (start != 0) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data("https://loremflickr.com/$value")
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.mars_photo),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .clip(shape)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ImageTheme {
        Greeting()
    }
}