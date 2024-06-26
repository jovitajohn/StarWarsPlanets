package com.jovita.startwarplanets.planetDetail

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.jovita.startwarplanets.R
import com.jovita.startwarplanets.data.NetworkState
import com.jovita.startwarplanets.data.Planet
import com.jovita.startwarplanets.data.PlanetProperties
import com.jovita.startwarplanets.data.PlanetResult
import com.jovita.startwarplanets.data.RootPlanetItem
import com.jovita.startwarplanets.planetListing.PlanetListViewModel
import com.jovita.startwarplanets.ui.theme.StarwarPlanetsTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlanetDetailActivity : ComponentActivity() {

    private lateinit var planetShort: RootPlanetItem
    private var planet by mutableStateOf(Planet())
    private var networkState: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        planetShort =
            intent.getSerializableExtra(getString(R.string.get_extra_planet)) as RootPlanetItem

        setContent {
            UiComponents()
        }
    }


    private fun fetchPlanetDetails(context: Context): Planet {
        if (NetworkState().isNetworkAvailable(context)) {
            lifecycleScope.launch(Dispatchers.Main) {
                val planetDetails = PlanetListViewModel().getPlanetDetails(planetShort.uid)
                planet = planetDetails!!
                networkState = true
            }
        } else {
            networkState = false
            planet = Planet("", null)
        }
        return planet
    }


    @Preview(name = "Planet Detail activity")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun UiComponents() {

        StarwarPlanetsTheme {
            val scope = rememberCoroutineScope()
            val snackbarHostState = remember { SnackbarHostState() }
            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackbarHostState, Modifier.testTag(
                            stringResource(R.string.snackbar)
                        )
                    )
                },
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        modifier = Modifier.testTag(stringResource(R.string.top_bar)),
                        title = {
                            Text(stringResource(R.string.top_bar_planet, planetShort.name))
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                onBackPressedDispatcher.onBackPressed()
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    )
                },
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .paint(
                            painterResource(id = R.drawable.background_galaxy),
                            contentScale = ContentScale.FillBounds
                        ),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    LaunchedEffect(500) {
                        snackbarHostState.showSnackbar(getString(R.string.loading_contents))
                    }

                    val context = LocalContext.current
                    val planetResponse: Planet = fetchPlanetDetails(context)

                    if (planetResponse.result != null) {
                        PlanetDetailGrid(planetResponse.result)
                    } else {

                        val msg =
                            if (networkState) getString(R.string.try_again_later) else getString(R.string.network_not_available)

                        LaunchedEffect(Unit) {
                            snackbarHostState.showSnackbar(msg)
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun PlanetDetailGrid(plantResult: PlanetResult) {

    val plantProperties: PlanetProperties = plantResult.properties

    Column(
        Modifier
            .padding(20.dp)
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            shape = RoundedCornerShape(5.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )
        ) {
            Column(modifier = Modifier.padding(10.dp)) {

                Text(
                    text = stringResource(R.string.description),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = plantResult.description,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color.Black
                )

            }
        }

        Column {

            Row {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                ) {
                    PropertyItem(
                        value = plantProperties.rotation_period,
                        name = stringResource(R.string.rotation_period)
                    )
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                ) {
                    PropertyItem(
                        value = plantProperties.orbital_period,
                        name = stringResource(R.string.orbital_period)
                    )
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
                    PropertyItem(
                        value = plantProperties.diameter,
                        name = stringResource(R.string.diameter)
                    )
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                ) {
                    PropertyItem(
                        value = plantProperties.climate,
                        name = stringResource(R.string.climate)
                    )
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
                    PropertyItem(
                        value = plantProperties.gravity,
                        name = stringResource(R.string.gravity)
                    )
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                ) {
                    PropertyItem(
                        value = plantProperties.population,
                        name = stringResource(R.string.population)
                    )
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
                    PropertyItem(
                        value = plantProperties.surface_water,
                        name = stringResource(R.string.surface_water)
                    )
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                ) {
                    PropertyItem(
                        value = plantProperties.terrain,
                        name = stringResource(R.string.terrain)
                    )
                }
            }
        }
    }
}

@Composable
fun PropertyItem(value: String, name: String) {

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Text(
                text = value,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.Black
            )

            Text(
                text = name,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
        }
    }
}
