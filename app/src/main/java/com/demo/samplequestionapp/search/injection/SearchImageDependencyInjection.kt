package com.demo.samplequestionapp.search.injection

import com.demo.samplequestionapp.base.BaseDependencyInjection
import com.demo.samplequestionapp.search.model.usecase.GetImagesForSearchHint
import com.demo.samplequestionapp.search.service.SearchRepository
import com.demo.samplequestionapp.search.service.local.SearchLocalRepository
import com.demo.samplequestionapp.search.service.remote.SearchRemoteRepository

object SearchImageDependencyInjection: BaseDependencyInjection() {

    fun provideGetImageForSearchHint() = GetImagesForSearchHint(provideSearchRepository())

    private fun provideSearchRepository() = SearchRepository(provideSearchRemoteRepository(), provideSearchLocalRepository())

    private fun provideSearchRemoteRepository() = SearchRemoteRepository()

    private fun provideSearchLocalRepository() = SearchLocalRepository()

}