package com.example.stepstest.data.useCase.impl

import com.example.stepstest.data.StepsApi
import com.example.stepstest.data.formResource
import com.example.stepstest.data.model.Comment
import com.example.stepstest.data.useCase.CommentUseCase
import com.example.stepstest.ui.viewObject.Resource

class CommentNetworkUseCase(private val api: StepsApi) : CommentUseCase {

    override suspend fun loadComments(page: String, list: String): Resource<List<Comment>> {
        return api.comments(page, list).formResource()
    }
}