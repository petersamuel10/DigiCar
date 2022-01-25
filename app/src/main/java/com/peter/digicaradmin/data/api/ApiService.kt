package com.peter.digicaradmin.data.api

import com.peter.digicaradmin.data.model.Temp
import retrofit2.http.GET

interface ApiService {
    @GET("weather?q=Cairo&appid=5340f46e23bccfee26b9fddef44b0675")
    suspend fun temp(): Temp

}