package com.example.stepstest.ui.scenes.rangePick

import androidx.lifecycle.MutableLiveData
import com.example.stepstest.R
import com.example.stepstest.data.useCase.CommentUseCase
import com.example.stepstest.ui.common.BaseViewModel
import com.example.stepstest.ui.viewObject.Resource
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject

private const val LOADING_ERROR = "Loading error"
private const val DELAY = 3000L
private const val LIMIT = 10

class RangePickViewModel @Inject constructor(
    private val useCase: CommentUseCase
) : BaseViewModel() {

    var inputScreenDataState: MutableLiveData<Int?> = MutableLiveData()
    var comments: MutableLiveData<Resource<CommentOffset>?> = MutableLiveData()
    private var request: Job? = null

    fun updateBounds(lower: Int?, upper: Int?) {
        if (lower != null && upper != null) {
            if (lower >= upper) {
                inputScreenDataState.postValue(R.string.comparison_exc)
            } else {
                loadComments(lower, upper)
            }
        } else {
            inputScreenDataState.postValue(R.string.fill_up)
        }
    }

    fun cancelRequest() {
        try {
            request?.cancel()
            comments.value = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun clearResource() {
        comments.value = null
    }

    private fun loadComments(start: Int, end: Int) {
        comments.postValue(Resource.loading())
        try {
            request?.cancel()
            request = scope.launch {
                delay(DELAY)
                val limit = limit(start, end)
                comments.postValue(
                    useCase.loadComments(start.toString(), limit.toString()).mapData {
                        CommentOffset(it, start, end)
                    })
            }
        } catch (e: Exception) {
            e.printStackTrace()
            comments.postValue(Resource.error(LOADING_ERROR))
        }
    }

    private fun limit(lower: Int, upper: Int): Int {
        return if ((upper - lower) < LIMIT) {
            upper - lower
        } else {
            LIMIT
        }
    }
}