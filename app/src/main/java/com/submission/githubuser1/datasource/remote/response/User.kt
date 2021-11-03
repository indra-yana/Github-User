package com.submission.githubuser1.datasource.remote.response

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
@Entity(tableName = "users", indices = [Index(value = ["id"], unique = true)])
data class User(
    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id")
    val id: Int,

    @SerializedName("login")
    @ColumnInfo(name = "login")
    val login: String,

    @SerializedName("avatar_url")
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,

    @SerializedName("followers_url")
    @ColumnInfo(name = "followers_url")
    val followersUrl: String,

    @SerializedName("following_url")
    @ColumnInfo(name = "following_url")
    val followingUrl: String,

    @SerializedName("repos_url")
    @ColumnInfo(name = "repos_url")
    val reposUrl: String,

    @SerializedName("score")
    @ColumnInfo(name = "score")
    val score: Double?,

    @SerializedName("type")
    @ColumnInfo(name = "type")
    val type: String,

    @SerializedName("url")
    @ColumnInfo(name = "url")
    val url: String
) : Parcelable