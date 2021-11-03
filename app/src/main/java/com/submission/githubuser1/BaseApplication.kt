package com.submission.githubuser1

import android.app.Application
import com.submission.githubuser1.datasource.local.AppDatabase
import com.submission.githubuser1.datasource.remote.AppRemoteApi
import com.submission.githubuser1.datasource.remote.IRemoteApi

/****************************************************
 * Created by Indra Muliana
 * On Sunday, 24/10/2021 15.30
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

class BaseApplication : Application() {

    companion object {
        @JvmStatic
        lateinit var remoteApi: IRemoteApi

        @JvmStatic
        lateinit var appDB: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        remoteApi = AppRemoteApi.initApi(IRemoteApi::class.java)
        appDB = AppDatabase.initDatabase(this)
    }

}