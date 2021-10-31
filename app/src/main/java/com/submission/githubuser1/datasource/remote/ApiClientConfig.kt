package com.submission.githubuser1.datasource.remote

import com.submission.githubuser1.BuildConfig
import com.submission.githubuser1.helper.Constant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/****************************************************
 * Created by Indra Muliana
 * On Sunday, 24/10/2021 14.30
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

object ApiClientConfig {

    private val retrofit: Retrofit

    init {
        // Add additional query param like API_KEY or Header
        val requestInterceptor = Interceptor { chain ->
            // URL Builder Example
            val urlBuilder = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("api_key", Constant.API_KEY)
                .build()

            // Request Header Example
            val requestHeaders = chain.request()
                .newBuilder()
                .url(urlBuilder)
                .addHeader("Accept", "application/json")
                .apply {
                    if (Constant.ACCESS_TOKEN.isNotEmpty()) {
                        addHeader("Authorization", "token ${Constant.ACCESS_TOKEN}")
                    }
                }
                .build()

            return@Interceptor chain.proceed(requestHeaders)
        }

        // Add logging interceptor if needed
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        // OkHttpClient
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .apply {
                if(BuildConfig.DEBUG) {
                    addInterceptor(loggingInterceptor)
                }
            }
            .connectTimeout(Constant.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <Api> initApi(api: Class<Api>): Api = retrofit.create(api)

}