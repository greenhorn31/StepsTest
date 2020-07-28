package com.example.stepstest.ui.scenes.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stepstest.R
import com.example.stepstest.ui.common.LoadingView
import com.example.stepstest.ui.utils.makeGone
import com.example.stepstest.ui.utils.makeVisible
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_comments.*
import javax.inject.Inject


class CommentsFragment : Fragment(),
    LoadingView {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: CommentsViewModel
    private var adapter: CommentsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_comments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        initUI()
        observeViewModel()
    }

    private fun bindViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CommentsViewModel::class.java)
        val commentOffset = CommentsFragmentArgs.fromBundle(requireArguments()).comments
        viewModel.updateInitParams(
            commentOffset.lowerBound,
            commentOffset.upperBound,
            commentOffset.comments
        )
    }

    private fun observeViewModel() {
        viewModel.comments.observe(viewLifecycleOwner, Observer {
            adapter?.add(it)
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { show ->
            if (show) {
                showLoading()
            } else {
                hideLoading()
            }
        })
        viewModel.errorState.observe(viewLifecycleOwner, Observer { res ->
            res?.let {
                showToast(it)
            }
        })
        viewModel.notFoundState.observe(viewLifecycleOwner, Observer { dataNotFound ->
            if (dataNotFound) {
                showToast(R.string.data_not_found)
            }
        })
    }

    private fun initUI() {
        val layoutManager = LinearLayoutManager(requireContext())
        rvCommentList.layoutManager = layoutManager
        adapter = CommentsAdapter(arrayListOf())
        rvCommentList.adapter = adapter
        rvCommentList.itemAnimator = null
        rvCommentList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount: Int = layoutManager.childCount
                val totalItemCount: Int = layoutManager.itemCount
                val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= 10) {
                    viewModel.loadMoreItems()
                }
            }
        })
    }

    private fun showToast(res: Int) {
        Toast.makeText(requireContext(), res, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        pbLinearProgress.makeVisible()
    }

    override fun hideLoading() {
        pbLinearProgress.makeGone()
    }
}