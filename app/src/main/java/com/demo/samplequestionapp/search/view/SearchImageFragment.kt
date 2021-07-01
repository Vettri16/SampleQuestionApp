package com.demo.samplequestionapp.search.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.demo.samplequestionapp.BR
import com.demo.samplequestionapp.R
import com.demo.samplequestionapp.base.RecyclerAdapter
import com.demo.samplequestionapp.commonutils.Constants
import com.demo.samplequestionapp.commonutils.OnAdapterClickListener
import com.demo.samplequestionapp.databinding.FragSearchImageBinding
import com.demo.samplequestionapp.search.model.SearchImageViewModel
import com.demo.samplequestionapp.search.model.SearchImageViewModelFactory
import com.demo.samplequestionapp.search.model.entities.ImageUIEntity


class SearchImageFragment: Fragment() {

    private lateinit var fragSearchImageBinding: FragSearchImageBinding
    private val searchImageViewModel: SearchImageViewModel by activityViewModels{
        SearchImageViewModelFactory( requireActivity().application)
    }
    private var adapter: RecyclerAdapter<ImageUIEntity, FragSearchImageBinding>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragSearchImageBinding = DataBindingUtil.inflate(inflater, R.layout.frag_search_image, container, false)
        adapter = null
        return fragSearchImageBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initScrollListener()
        fragSearchImageBinding.viewModel = searchImageViewModel
        if(searchImageViewModel.searchImageList.isEmpty()) {
            searchImageViewModel.getImagesForSearchHint(false)
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        searchImageViewModel.imageResponseLiveData.observe(viewLifecycleOwner){
            initImageAdapter(it)
        }
        searchImageViewModel.shimmerLiveData.observe(viewLifecycleOwner){
            if(it)
            {
                fragSearchImageBinding.shimmerProductListing.startShimmer()
            } else {
                fragSearchImageBinding.shimmerProductListing.stopShimmer()
            }
        }

        searchImageViewModel.keyboardCloseLiveData.observe(viewLifecycleOwner){
            closeKeyboard()
        }
    }

    private fun closeKeyboard()
    {
        val imm: InputMethodManager =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = requireActivity().currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun initScrollListener() {
        val gridLayoutManager = fragSearchImageBinding.rvProductListing.layoutManager as GridLayoutManager?
        fragSearchImageBinding.nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
            if (v?.getChildAt(v.childCount - 1) != null && gridLayoutManager!=null) {
                if (scrollY >= v.getChildAt(v.childCount - 1)
                        .measuredHeight - v.measuredHeight &&
                    scrollY > oldScrollY
                ) {
                    val totalItemCount: Int = gridLayoutManager.itemCount
                    val lastVisibleItem: Int = gridLayoutManager.findLastVisibleItemPosition()
                    val loadMore = lastVisibleItem == totalItemCount - 1
                    if(loadMore) {
                        searchImageViewModel.onPageEndReached()
                    }
                }
            }
        })
    }

    private fun initImageAdapter(images: List<ImageUIEntity>) {
        val recyclerEntities = ArrayList<ImageUIEntity>()
        images.forEach{ recyclerEntities.add(it.copy()) }
        adapter?.setItems(recyclerEntities) ?: run{
            adapter = RecyclerAdapter(requireContext(), fragSearchImageBinding, BR.adapter, R.layout.adapter_image_list_item, BR.entity, recyclerEntities, object: OnAdapterClickListener<ImageUIEntity> {
                override fun onClick(view: View, recyclerEntity: ImageUIEntity) {
                    searchImageViewModel.onImageClicked(recyclerEntity)
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .add(R.id.flFragContainer, ImageDetailFragment.getCurrentInstance(null), Constants.IMAGE_DETAIL_FRAG_TAG)
                        .addToBackStack(Constants.IMAGE_DETAIL_FRAG_TAG)
                        .commit()
                }
            })
        }
    }

    companion object {
        fun getCurrentInstance(bundle: Bundle?): SearchImageFragment {
            val fragment = SearchImageFragment()
            if(bundle!=null)
            fragment.arguments = bundle
            return fragment
        }
    }
}