package com.demo.samplequestionapp.commonutils

import com.demo.samplequestionapp.base.UseCase
import io.reactivex.rxjava3.core.Observable

class UseCaseHandler {

    fun<P: UseCase.RequestValue, Q: UseCase.ResponseValue> execute(useCase: UseCase<P, Q>, requestValues: P): Observable<Q> {
        return useCase.run(requestValues)
    }
}