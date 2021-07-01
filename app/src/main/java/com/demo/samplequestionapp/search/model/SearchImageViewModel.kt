package com.demo.samplequestionapp.search.model

import android.app.Application
import android.os.CountDownTimer
import android.view.View
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.demo.samplequestionapp.R
import com.demo.samplequestionapp.commonutils.Constants
import com.demo.samplequestionapp.base.ObservableViewModel
import com.demo.samplequestionapp.rest.APIError
import com.demo.samplequestionapp.search.injection.SearchImageDependencyInjection
import com.demo.samplequestionapp.search.model.entities.ImageRequestEntity
import com.demo.samplequestionapp.search.model.entities.ImageUIEntity
import com.demo.samplequestionapp.search.model.usecase.GetImagesForSearchHint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers


class SearchImageViewModel(application: Application): ObservableViewModel(application) {

    var hint: String = ""
    set(value) {
        field = value
        onQueryTextChanged()
    }
    private var pageOffset: Int = 1
    private var pageLimit: Int = 4
    var isFeedExists = false
    private var tag = "GET_IMAGES"
    var shimmerEnabled: Boolean = true
        @Bindable get() = field
        set(value) {
            field = value
            notifyChange()
            shimmerLiveData.value = value
        }
    var loadMoreEnabled: Boolean = false
        @Bindable get() = field
        set(value) {
            field = value
            notifyChange()
        }
    var errorEnabled: Boolean = false
        @Bindable get() = field
        set(value) {
            field = value
            notifyChange()
            recyclerViewEnabled = false
        }
    var recyclerViewEnabled: Boolean = false
        @Bindable get() = !shimmerEnabled && !errorEnabled
        set(value) {
            field = value
            notifyChange()
        }
    val imageResponseLiveData: MutableLiveData<List<ImageUIEntity>> = MutableLiveData()
    val shimmerLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val imageDetailCloseClicked: MutableLiveData<Boolean> = MutableLiveData()
    val searchImageList = ArrayList<ImageUIEntity>()
    private var countDownTimer: CountDownTimer? = null
    var currentImageEntity: ImageUIEntity? = null
    var errorMessage: String = ""
    var errorOrInfo: Boolean = false
        @Bindable get() = field
        set(value) {
            field = value
            notifyChange()
        }

    private fun onQueryTextChanged() {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
        }
        countDownTimer = object : CountDownTimer(500, 100) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                getImagesForSearchHint(false)
            }
        }
        countDownTimer!!.start()
    }

    fun getImagesForSearchHint(loadMore: Boolean) {
        if(loadMore) {
            loadMoreEnabled = true
        }else{
            pageOffset = 0
            searchImageList.clear()
            shimmerEnabled = true
        }
        errorEnabled = false
        errorOrInfo = false
        val requestValues = GetImagesForSearchHint.GetImageRequestValue(constructImageRequestEntity(), tag)
        SearchImageDependencyInjection.provideUseCaseHandler()
            .execute(SearchImageDependencyInjection.provideGetImageForSearchHint(), requestValues)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: Observer<GetImagesForSearchHint.GetImageResponseValue> {
                override fun onSubscribe(d: Disposable?) {

                }

                override fun onNext(t: GetImagesForSearchHint.GetImageResponseValue?) {
                    if(t?.searchUIEntity != null)
                    {
                        if(t.searchUIEntity.photo.isNotEmpty()) {
                            searchImageList.addAll(t.searchUIEntity.photo)
                            isFeedExists = pageOffset < t.searchUIEntity.pages
                            imageResponseLiveData.value = searchImageList
                        } else {
                            errorMessage = getApplication<Application>().applicationContext?.getString(R.string.no_images)?:"No Images Found"
                            errorOrInfo = false
                            errorEnabled = true
                        }
                    } else {
                        var error = APIError(0, "Null response")
                        if(t?.error != null)
                        {
                            error = t.error!!
                        }
                        errorMessage = error.message
                        errorOrInfo = true
                        errorEnabled = true
                    }
                }

                override fun onError(e: Throwable?) {
                    val error = APIError(0, e?.message?:"Error")
                    errorMessage = error.message
                    errorOrInfo = true
                    errorEnabled = true
                }

                override fun onComplete() {
                    loadMoreEnabled = false
                    shimmerEnabled = false
                }
            })
    }

    fun onPageEndReached()
    {
        if (!shimmerEnabled && isFeedExists) {
            pageOffset += pageLimit
            getImagesForSearchHint(true)
        }
    }

    private fun constructImageRequestEntity(): ImageRequestEntity {
        return ImageRequestEntity(Constants.IMAGE_API_METHOD,
            Constants.IMAGE_API_KEY,
            hint,
            Constants.IMAGE_API_FORMAT,
            Constants.IMAGE_API_JSON_CALLBACK,
            pageOffset,
            pageLimit)
    }

    fun onSearchCloseClicked(v: View) {
        if(hint.isNotEmpty())
            hint = ""
    }

    fun onImageClicked(imageEntity: ImageUIEntity)
    {
        currentImageEntity = imageEntity
    }

    fun onErrorRetryClicked(v: View) {
        getImagesForSearchHint(false)
    }


}