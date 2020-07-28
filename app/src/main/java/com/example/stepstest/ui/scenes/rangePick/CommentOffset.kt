package com.example.stepstest.ui.scenes.rangePick

import com.example.stepstest.data.model.Comment
import java.io.Serializable

class CommentOffset(
    var comments: List<Comment>?,
    var lowerBound: Int,
    var upperBound: Int
) : Serializable