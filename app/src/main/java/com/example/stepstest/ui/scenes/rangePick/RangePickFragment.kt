package com.example.stepstest.ui.scenes.rangePick

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.stepstest.R
import com.example.stepstest.ui.common.LoadingView
import com.example.stepstest.ui.utils.makeGone
import com.example.stepstest.ui.utils.makeVisible
import com.example.stepstest.ui.viewObject.Status
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_range_pick.*
import javax.inject.Inject

class RangePickFragment : Fragment(),
    LoadingView {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: RangePickViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_range_pick, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        initUI()
    }

    private fun bindViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(RangePickViewModel::class.java)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.inputScreenDataState.observe(viewLifecycleOwner, Observer { res ->
            res?.let {
                showNotification(it)
            }
        })
        viewModel.comments.observe(viewLifecycleOwner, Observer { res ->
            if (res != null) {
                when (res.status) {
                    Status.LOADING -> {
                        hideKeyboard()
                        showLoading()
                    }
                    Status.SUCCESS -> {
                        res.data?.let {
                            navigateComments(it)
                            viewModel.clearResource()
                        }
                        hideLoading()
                    }
                    Status.ERROR -> {
                        hideLoading()
                    }
                }
            } else {
                hideLoading()
            }
        })
    }

    private fun initUI() {
        btnNext.setOnClickListener {
            updateBounds()
        }
        btnCancel.setOnClickListener {
            viewModel.cancelRequest()
        }
    }

    override fun showLoading() {
        clProgress.makeVisible()
        clContent.makeGone()
    }

    override fun hideLoading() {
        clProgress.makeGone()
        clContent.makeVisible()
    }

    private fun showNotification(res: Int) {
        Toast.makeText(requireContext(), res, Toast.LENGTH_LONG).show()
    }

    private fun updateBounds() {
        viewModel.updateBounds(etLowerBound.getInt(), etUpperBound.getInt())
    }

    private fun navigateComments(commentOffset: CommentOffset) {
        val action =
            RangePickFragmentDirections.actionRangePickFragmentToCommentsFragment(
                commentOffset
            )
        Navigation.findNavController(requireActivity(), R.id.frameContainer)
            .navigate(action)
    }

    private fun hideKeyboard() {
        val imm =
            this.context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.view?.windowToken, 0)
    }

    private fun EditText.getInt(): Int? {
        return try {
            if (this.text.isEmpty()) {
                null
            } else {
                this.text.toString().toInt()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}