package com.jovita.startwarplanets.planetListing

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.jovita.startwarplanets.R
import com.jovita.startwarplanets.data.RootPlanetItem
import com.jovita.startwarplanets.planetDetail.PlanetDetailActivity
import com.jovita.startwarplanets.ui.theme.StartwarPlanetsTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    var planets by mutableStateOf<List<RootPlanetItem>>(emptyList())
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseContent()
           /* Scaffold (
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = {
                            Text("Star Wars Planets")
                        }
                    )
                },
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .paint(
                            painterResource(id = R.drawable.background_galaxy),
                            contentScale = ContentScale.FillBounds
                        ),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    StartWars(
                        Modifier
                            .fillMaxSize()
                    )
                }
            }*/
        }
    }

    private fun fetchPlanets(): List<RootPlanetItem> {
        lifecycleScope.launch(Dispatchers.Main) {
            val planetsList = PlanetListViewModel().getPlanetsData()
            planets = planetsList!!
        }
        return planets
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BaseContent(){
        Scaffold (
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text("Star Wars Planets")
                    }
                )
            },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .paint(
                        painterResource(id = R.drawable.background_galaxy),
                        contentScale = ContentScale.FillBounds
                    ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                StartWars(
                    Modifier
                        .fillMaxSize()
                )
            }
        }
    }

    @Preview(showBackground = true, name = "Star Wars")
    @Composable
    fun StartWars(modifier: Modifier = Modifier) {

        StartwarPlanetsTheme {

            var context = LocalContext.current
            val planetList: List<RootPlanetItem>? = fetchPlanets()


            Column(
                Modifier
                    .absolutePadding(5.dp, 15.dp, 5.dp, 5.dp)
                    .fillMaxHeight()
                ) {
                if (!planetList.isNullOrEmpty()) {
                    for (item in planetList) {
                        planetItem(data = item) {
                           // Toast.makeText(context, "Clicked on ${item.name}", Toast.LENGTH_SHORT) .show()
                            val intent = Intent(context, PlanetDetailActivity::class.java)
                            intent.putExtra("planet",item)
                            context.startActivity(intent)
                        }
                    }
                } else {
                    // ShowError("No data!!")
                    planetItem(null) {
                        Toast.makeText(context, "Try again later!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

@Composable
fun planetItem(data: RootPlanetItem?, onClick: () -> Unit) {

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .wrapContentSize()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(10.dp)
        ) {

            if (data == null) {
                Text(
                    text = "Loading...",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color.Black
                )
            } else {
                data?.name?.let {
                    Text(
                        text = it,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

