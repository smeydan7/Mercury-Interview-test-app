package mercury.interview.core

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import javax.inject.Singleton


@[Module InstallIn(SingletonComponent::class)]
object NetworkModule {

    @get:Provides @Singleton
    val providesMoshi = Moshi.Builder().build()

    @get:Provides @Singleton
    val providesGson: Gson = GsonBuilder().create()

    @Provides @Singleton
    fun providesNetworkService(gson: Gson, moshi: Moshi): BootstrapNetworkService {
        return Retrofit.Builder()

            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .validateEagerly(true)
            .baseUrl("https://gist.githubusercontent.com/") // added base url here
            .build()
            .create()
    }
}


interface BootstrapNetworkService {
    // gets the endpoint from the base url
    @GET("tadfisher/0cd67d2015dfdfc9d091b9cfe8efff73/raw/9f68987b1ac017d06770ce2b8c4b901f52a4f0d7/mobile-interview.json")
    suspend fun getData(): Response
}

@JsonClass(generateAdapter = true)
data class Example(
    @param:Json val foo: Boolean
)