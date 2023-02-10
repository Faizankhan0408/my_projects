package com.faizansocialmediaproject.techchat.Models

import java.io.Serializable

data class UserStoryModel(
    var userId: String="",
    var userStoryUrl: String="",
    var userName: String="",
    var userProfileUrl: String="",
    var postedAt :Long =0
) : Serializable{

}
