package com.jovita.startwarplanets.planetDetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.jovita.startwarplanets.data.Planet
import com.jovita.startwarplanets.data.PlanetProperties
import com.jovita.startwarplanets.data.RootPlanetItem
import com.jovita.startwarplanets.planetListing.PlanetListViewModel
import com.jovita.startwarplanets.ui.theme.StartwarPlanetsTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlanetDetailActivity : ComponentActivity() {

    lateinit var planetShort: RootPlanetItem
    var planet by mutableStateOf(Planet())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        planetShort = intent.getSerializableExtra("planet") as RootPlanetItem

        setContent {
            UiComponents()
        }
    }


    private fun fetchPlanetDetails(): Planet {
        lifecycleScope.launch(Dispatchers.Main) {
            val planetDetails = PlanetListViewModel().getPlanetDetails()
            planet = planetDetails!!
        }
        return planet
    }


    @Preview(name = "Planet Detail activity")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun UiComponents() {

        StartwarPlanetsTheme {
            val scope = rememberCoroutineScope()
            val snackbarHostState = remember { SnackbarHostState() }
            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                },
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = {
                            Text("Planet - ${planetShort.name}")
                        }
                    )
                },
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    LaunchedEffect(Unit) {
                        snackbarHostState.showSnackbar("Loading contents...")
                    }


                    var planetResponse: Planet = fetchPlanetDetails()

                    if (planetResponse != null) {
                        planetResponse.result?.properties?.let { PlanetDetailGrid(it) }
                    }

                    //Text(text = "Loading...")
                }

            }
        }
    }

    @Composable
    fun PlanetDetailGrid(plantProperties: PlanetProperties) {

        Column {
            Row {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                ) {
                    PropertyItem(value = plantProperties.rotation_period, name = "Rotation Period")
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                ) {
                    PropertyItem(value = plantProperties.orbital_period, name = "Orbital Period")
                }
            }
        }
        Column {

            Row {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                ) {
                    PropertyItem(value = plantProperties.diameter, name = "Diameter")
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                ) {
                    PropertyItem(value = plantProperties.climate, name = "Climate")
                }
            }
        }
        Column {
            Row {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                ) {
                    PropertyItem(value = plantProperties.gravity, name = "Gravity")
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                ) {
                    PropertyItem(value = plantProperties.terrain, name = "Terrain")
                }
            }
        }
        Column {
            Row {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                ) {
                    PropertyItem(value = plantProperties.surface_water, name = "Surface water")
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                ) {
                    PropertyItem(value = plantProperties.population, name = "population")
                }
            }
        }
    }

    @Composable
    fun PropertyItem(value: String, name: String) {

        Row {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(5.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                Column() {
                    Text(
                        text = value,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = Color.Black
                    )

                    Text(
                        text = name,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}