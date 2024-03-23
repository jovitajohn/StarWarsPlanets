package com.jovita.startwarplanets.planetListing

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jovita.startwarplanets.data.ApiService
import com.jovita.startwarplanets.data.Planet
import com.jovita.startwarplanets.data.RootPlanetItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class PlanetListViewModel : ViewModel() {


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://swapi.tech/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService  = retrofit.create(ApiService::class.java)

    suspend fun getPlanetsData() : List<RootPlanetItem>? {
        return try{
            val response = apiService.getPlanetList()
            Log.i("api response",response.toString())
            if(response.isSuccessful){
                response.body()?.results
            }else{
                null
            }
        }catch (e: Exception){
            null
        }
    }

    suspend fun getPlanetDetails(planetId : String) : Planet? {
        return try{
            val response = apiService.getPlanetDetails(planetId)
            Log.i("api response",response.toString())
            if(response.isSuccessful){
                response.body()
            }else{
                null
            }
        }catch (e: Exception){
            null
        }
    }

}