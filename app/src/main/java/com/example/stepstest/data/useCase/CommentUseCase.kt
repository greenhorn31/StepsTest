package com.example.stepstest.data.useCase

import com.example.stepstest.data.model.Comment
import com.example.stepstest.ui.viewObject.Resource

interface CommentUseCase {

    suspend fun loadComments(page: String, list: String): Resource<List<Comment>>
}