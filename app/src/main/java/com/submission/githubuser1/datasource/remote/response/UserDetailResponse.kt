package com.submission.githubuser1.datasource.remote.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_details", indices = [Index(value = ["id"], unique = true)])
class UserDetailResponse(
    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id")
    val id: Int,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String,

    @SerializedName("login")
    @ColumnInfo(name = "login")
    val login: String,

    @SerializedName("email")
    @ColumnInfo(name ="email")
    val email: String?,

    @SerializedName("company")
    @ColumnInfo(name = "company")
    val company: String?,

    @SerializedName("location")
    @ColumnInfo(name = "location")
    val location: String?,

    @SerializedName("avatar_url")
    @ColumnInfo(name ="avatar_url")
    val avatarUrl: String,

    @SerializedName("followers")
    @ColumnInfo(name = "followers")
    val followers: Int,

    @SerializedName("following")
    @ColumnInfo(name = "following")
    val following: Int,

    @SerializedName("public_repos")
    @ColumnInfo(name = "public_repos")
    val publicRepos: Int,

    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    @ColumnInfo(name = "updated_at")
    val updatedAt: String,

    @SerializedName("url")
    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "is_favourite")
    val isFavourite: Int = 0,
)