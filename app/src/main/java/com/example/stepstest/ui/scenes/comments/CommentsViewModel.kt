package com.example.stepstest.ui.scenes.comments

import androidx.lifecycle.MutableLiveData
import com.example.stepstest.R
import com.example.stepstest.data.model.Comment
import com.example.stepstest.data.useCase.CommentUseCase
import com.example.stepstest.ui.common.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val DEFAULT_PAGE_SIZE = 10

class CommentsViewModel @Inject constructor(private val useCase: CommentUseCase) : BaseViewModel() {

    var comments: MutableLiveData<List<Comment>> = MutableLiveData()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    var errorState: MutableLiveData<Int?> = MutableLiveData(null)
    var notFoundState: MutableLiveData<Boolean> = MutableLiveData(false)

    private var end: Int = 0
    private var currentStart: Int = 0

    fun updateInitParams(start: Int, end: Int, comments: List<Comment>?) {
        this.currentStart = start + (comments?.size ?: 0)
        this.end = end
        updateComments(comments)
    }

    fun loadMoreItems() {
        if (isLoading.value != true && currentStart < end) {
            isLoading.value = true
            scope.launch {
                try {
                    val result = useCase.loadComments(currentStart.toString(), limit().toString())
                    if (result.isSuccess()) {
                        handleSuccessResult(result.data)
                    } else {
                        errorState.postValue(R.string.server_error)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    errorState.postValue(R.string.loading_error)
                } finally {
                    isLoading.postValue(false)
                }
            }
        }
    }

    private fun handleSuccessResult(result: List<Comment>?) {
        if (result != null) {
            currentStart += result.size
            comments.postValue(result)
            errorState.postValue(null)
        }
    }

    private fun updateComments(comments: List<Comment>?) {
        if (comments.isNullOrEmpty()) {
            notFoundState.postValue(true)
        } else {
            this.comments.value = comments
        }
    }


    private fun limit(): Int {
        val difference = end - currentStart
        return if (difference < DEFAULT_PAGE_SIZE) {
            difference
        } else {
            DEFAULT_PAGE_SIZE
        }
    }
}