package com.demo.samplequestionapp.rest

import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class RestClient {

    companion object {
        private var baseUrl = "https://api.flickr.com"

        var curInstance: RestClient? = null
        get() {
            return if(field==null) {
                RestClient()
            } else {
                field
            }
        }

        @Volatile
        private var httpClient: OkHttpClient.Builder? = null

        private val retrofitQueue: HashMap<String, Single<*>> = HashMap<String, Single<*>>()

        @Synchronized
        fun addToQueue(tag: String?, call: Single<*>?) {
            if (tag != null && tag.isNotEmpty() && call != null) {
                if (!retrofitQueue.containsKey(tag)) {
                    retrofitQueue[tag] = call
                }
            }
        }

        @Synchronized
        fun removeRequest(tag: String?) {
            if (tag != null && tag.isNotEmpty()) {
                retrofitQueue.remove(tag)
            }
        }

        private fun getOkHttpClient(timeout: Long): OkHttpClient.Builder? {
            if (httpClient == null) {
                synchronized(RestClient::class.java) {
                    if (httpClient == null) {
                        httpClient = OkHttpClient.Builder()
                        httpClient!!.addInterceptor{ chain ->
                            val original = chain.request()
                            val request: Request = getHeaderParams(original)
                            val response = chain.proceed(request)
                            response
                        }
                        httpClient!!.connectTimeout(timeout, TimeUnit.SECONDS)
                            .readTimeout(timeout, TimeUnit.SECONDS)
                            .writeTimeout(timeout, TimeUnit.SECONDS).build()
                    }
                }
            }
            return httpClient
        }

        private fun getHeaderParams(original: Request): Request {
            return original.newBuilder()
                .method(original.method(),original.body())
                .build()
        }


    }

    fun getClient(): Retrofit {
        val client = getOkHttpClient(30)?.build()
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return  Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(client)
            .build()
    }
}