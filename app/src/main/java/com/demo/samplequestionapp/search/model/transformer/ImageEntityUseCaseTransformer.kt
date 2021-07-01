package com.demo.samplequestionapp.search.model.transformer

import com.demo.samplequestionapp.base.UseCaseTransformer
import com.demo.samplequestionapp.rest.APIError
import com.demo.samplequestionapp.rest.RestClientEntity
import com.demo.samplequestionapp.search.model.entities.ImageUIEntity
import com.demo.samplequestionapp.search.model.entities.SearchResultEntity
import com.demo.samplequestionapp.search.model.entities.SearchUIEntity
import com.demo.samplequestionapp.search.model.usecase.GetImagesForSearchHint

class ImageEntityUseCaseTransformer:
    UseCaseTransformer<RestClientEntity<SearchResultEntity>, GetImagesForSearchHint.GetImageResponseValue> {

    override fun transform(input: RestClientEntity<SearchResultEntity>): GetImagesForSearchHint.GetImageResponseValue {
        input.data?.let { searchResultEntity ->
            val searchDataEntity = searchResultEntity.photos
            val images = ArrayList<ImageUIEntity>()
            searchDataEntity.photo.map {
                images.add(
                    ImageUIEntity(
                        it.id,
                        it.owner,
                        it.secret,
                        it.server,
                        it.farm,
                        it.title,
                        it.isPublic,
                        it.isFriend,
                        it.isFamily
                    )
                )
            }
            val searchUIEntity = SearchUIEntity(
                searchDataEntity.page,
                searchDataEntity.pages,
                searchDataEntity.perPage,
                images
            )
            return GetImagesForSearchHint.GetImageResponseValue(searchUIEntity, input.apiError)
        }?: kotlin.run {
            var error: APIError? = null
            if (input.apiError != null) {
                error = input.apiError
            }
            return GetImagesForSearchHint.GetImageResponseValue(null, error)
        }
    }
}