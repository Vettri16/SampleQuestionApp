package com.demo.samplequestionapp.rest

import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response

class RestClientCallback<T> {

    fun enqueueCall(tag: String, call: Single<Response<T>>): Observable<RestClientEntity<T>>{
        RestClient.addToQueue(tag, call)
        return Observable.create {
            call.subscribeOn(Schedulers.io())
            .subscribe(object : SingleObserver<Response<T>> {
                override fun onSubscribe(d: Disposable?) {}
                override fun onSuccess(response: Response<T>) {
                    RestClient.removeRequest(tag)
                    lateinit var restClientEntity: RestClientEntity<T>
                    if (response.body() != null) {
                        restClientEntity = RestClientEntity(response.isSuccessful,200, "", response.body(), null)
                        it.onNext(restClientEntity)
                    } else {
                        val error = RestClientError.parseError(response)
                        var message = error?.message ?: response.message()
                        if (response.code() == 401) {
                            message = "Username / Password is incorrect"
                        }
                        val apiError = APIError(response.code(), message)
                        restClientEntity = RestClientEntity(false, apiError.statusCode, apiError.message, null, apiError)
                        it.onNext(restClientEntity)
                    }
                    it.onComplete()
                }

                override fun onError(e: Throwable?) {
                    RestClient.removeRequest(tag)
                    val restClientEntity: RestClientEntity<T> = RestClientEntity(false, 1, e?.message?:"Error", null, APIError(1, e?.message?:"Error"))
                    it.onNext(restClientEntity)
                    it.onComplete()
                }
            }) }

    }
}