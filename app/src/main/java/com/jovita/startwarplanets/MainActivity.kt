package com.jovita.startwarplanets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jovita.startwarplanets.data.Planet
import com.jovita.startwarplanets.data.PlanetData
import com.jovita.startwarplanets.ui.theme.StartwarPlanetsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StartwarPlanetsTheme {
                // A surface container using the 'background' color from the theme
                startWars(Modifier.fillMaxSize().paint(
                    painterResource(id = R.drawable.background_galaxy),
                    contentScale = ContentScale.FillBounds
                ))
            }
        }
    }
}


@Composable
fun startWars(modifier: Modifier = Modifier){
var nameList: List<String> = listOf("Android","Ethan","Kannus","Jo")
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Image(painterResource(id = R.drawable.background_galaxy), contentDescription = null,
            modifier = Modifier.size(160.dp).background(
                color = Color.Black
            )
        )
        Column(modifier.padding(20.dp)) {
            for(name in nameList) {

                Greeting(name)
            }
        }

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
   Surface(color = MaterialTheme.colorScheme.primary) {
       Text(
           text = "Hello $name!",
           modifier = modifier
       )
   }
}

@Preview(showBackground = true,  name = "Star Wars")
@Composable
fun GreetingPreview() {
    StartwarPlanetsTheme {
       startWars()
    }

    @Composable
    fun planetItem(data: Planet, OnClick :()-> Unit){

    }
}