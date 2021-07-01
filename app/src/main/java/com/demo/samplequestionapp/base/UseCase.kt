package com.demo.samplequestionapp.base

import com.demo.samplequestionapp.rest.APIError
import io.reactivex.rxjava3.core.Observable

abstract class UseCase<P: UseCase.RequestValue, Q: UseCase.ResponseValue> {

    fun run(requestValues: P): Observable<Q> {
        return executeUseCase(requestValues)
    }

    abstract fun executeUseCase(requestValue: P): Observable<Q>

    fun<R> transform(observable: Observable<R>, transformer: UseCaseTransformer<R, Q>?): Observable<Q> {
        return observable.flatMap { input ->
            Observable.create {
                transformer?.apply {
                    it.onNext(transform(input))
                    it.onComplete()
                }?: kotlin.run {
                    it.onNext(null)
                    it.onComplete()
                }
            }
        }
    }

    interface RequestValue
    interface ResponseValue {
        var error: APIError?
    }

}