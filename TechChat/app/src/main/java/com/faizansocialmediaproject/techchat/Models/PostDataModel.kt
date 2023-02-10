package com.faizansocialmediaproject.techchat.Models

import java.io.Serializable

data class PostDataModel(
    var userID: String = "",
    var userName: String = "",
    var about: String = "",
    var userProflieUrl: String = "",
    var postId: String = "",
    var postedAt: String = "",
    var postUrl: String = "",
    var postDesc: String = ""
) : Serializable {}
