package com.demo.samplequestionapp.base

interface UseCaseTransformer<A,B: UseCase.ResponseValue> {

    fun transform(input: A): B
}