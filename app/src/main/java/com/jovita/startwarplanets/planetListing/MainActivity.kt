package com.jovita.startwarplanets.planetListing

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import androidx.compose.ui.draw.clip
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
import com.jovita.startwarplanets.data.RootPlanetItem
import com.jovita.startwarplanets.planetDetail.PlanetDetailActivity
import com.jovita.startwarplanets.ui.theme.StarwarPlanetsTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class MainActivity : ComponentActivity() {

    var planets by mutableStateOf<List<RootPlanetItem>>(emptyList())
    var networkState :Boolean = true

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseContent()
        }
    }

    private fun fetchPlanets(context: Context): List<RootPlanetItem> {


        if (NetworkState().isNetworkAvailable(context)) {
            lifecycleScope.launch(Dispatchers.Main) {
                val planetsList = PlanetListViewModel().getPlanetsData()
                networkState = true
                planets = planetsList!!
            }
        }else{
            networkState = false
            planets = emptyList()
        }
        return planets
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    fun BaseContent() {

        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }

        StarwarPlanetsTheme {
            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackbarHostState, modifier = Modifier.testTag(
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
                        title = {
                            Text(stringResource(R.string.star_wars_planets))
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
                            .fillMaxSize(),
                        scope,
                        snackbarHostState
                    )
                }
            }
        }
    }

    @Composable
    fun StartWars(
        modifier: Modifier = Modifier,
        scope: CoroutineScope,
        snackbarHostState: SnackbarHostState
    ) {

        StarwarPlanetsTheme {

            var context = LocalContext.current

            Column(
                Modifier
                    .absolutePadding(5.dp, 15.dp, 5.dp, 5.dp)
                    .fillMaxSize()
            ) {

                val planetList: List<RootPlanetItem>? = fetchPlanets(context)

                if (!planetList.isNullOrEmpty()) {
                    for (item in planetList) {
                        planetItem(data = item) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    getString(
                                        R.string.launching_details_for,
                                        item.name
                                    )
                                )
                            }
                            val intent = Intent(context, PlanetDetailActivity::class.java)
                            intent.putExtra(getString(R.string.get_extra_planet), item)
                            context.startActivity(intent)
                        }
                    }
                } else {
                    if(networkState){
                        planetItem(null) {
                            scope.launch {
                                snackbarHostState.showSnackbar(getString(R.string.try_again_later))
                            }
                        }
                    }else{
                        LaunchedEffect(key1 = 2000) {
                            scope.launch {
                                snackbarHostState.showSnackbar(getString(R.string.network_not_available))
                            }
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
                .clickable(onClick = onClick)
                .testTag(stringResource(R.string.planetitem)),
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
                        text = getString(R.string.loading_contents),
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
}

