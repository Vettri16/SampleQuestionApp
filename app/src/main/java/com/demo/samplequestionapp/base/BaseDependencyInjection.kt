package com.demo.samplequestionapp.base

import com.demo.samplequestionapp.commonutils.UseCaseHandler

abstract class BaseDependencyInjection {
    fun provideUseCaseHandler() = UseCaseHandler()
}