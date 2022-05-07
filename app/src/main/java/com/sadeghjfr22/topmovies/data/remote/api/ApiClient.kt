package com.sadeghjfr22.topmovies.api

import com.sadeghjfr22.topmovies.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private var retrofit: Retrofit? = null
  //  private var OKHttpClient: OkHttpClient? = null

    private fun getApiClient(): Retrofit? {

     //   if (OKHttpClient == null) OKHttpClient = initOkHttp()

        if (retrofit == null) {

            retrofit = Retrofit.Builder().baseUrl(BASE_URL)
              //  .client(OKHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit
    }

/*
    private fun initOkHttp(): OkHttpClient? {

        val REQUEST_TIMEOUT = 60
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
            val request = requestBuilder.build()
            chain.proceed(request)
        }

        return httpClient.build()
    }
*/

    fun getService(): ApiService  = getApiClient()!!.create(ApiService::class.java)


}