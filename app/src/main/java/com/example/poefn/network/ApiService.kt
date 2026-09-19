package com.example.poefn.network

import com.example.poefn.model.AuthResponse
import com.example.poefn.model.JournalEntry
import com.example.poefn.model.LoginRequest
import com.example.poefn.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @GET("entries")
    suspend fun getEntries(): Response<List<JournalEntry>>

    @GET("entries/search")
    suspend fun searchEntries(@Query("q") query: String): Response<List<JournalEntry>>

    @GET("entries/{id}")
    suspend fun getEntry(@Path("id") entryId: Int): Response<JournalEntry>

    @POST("entries")
    suspend fun createEntry(@Body entry: JournalEntry): Response<JournalEntry>

    @PUT("entries/{id}")
    suspend fun updateEntry(
        @Path("id") entryId: Int,
        @Body entry: JournalEntry
    ): Response<JournalEntry>

    @DELETE("entries/{id}")
    suspend fun deleteEntry(@Path("id") entryId: Int): Response<Unit>
}
