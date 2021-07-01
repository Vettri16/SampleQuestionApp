package com.demo.samplequestionapp.search.model.usecase

import com.demo.samplequestionapp.base.UseCase
import com.demo.samplequestionapp.rest.APIError
import com.demo.samplequestionapp.search.model.entities.ImageRequestEntity
import com.demo.samplequestionapp.search.model.entities.SearchUIEntity
import com.demo.samplequestionapp.search.model.transformer.ImageEntityUseCaseTransformer
import com.demo.samplequestionapp.search.service.SearchRepository
import io.reactivex.rxjava3.core.Observable

class GetImagesForSearchHint(private val searchRepository: SearchRepository): UseCase<GetImagesForSearchHint.GetImageRequestValue, GetImagesForSearchHint.GetImageResponseValue>() {

    override fun executeUseCase(requestValue: GetImageRequestValue): Observable<GetImageResponseValue> {
        val observable = searchRepository.getImagesForGivenSearchText(requestValue.imageRequestEntity, requestValue.tag)
        return transform(observable, ImageEntityUseCaseTransformer())
    }

    data class GetImageRequestValue(val imageRequestEntity: ImageRequestEntity, val tag: String): RequestValue
    data class GetImageResponseValue(val searchUIEntity: SearchUIEntity?,
                                     override var error: APIError?): ResponseValue

}