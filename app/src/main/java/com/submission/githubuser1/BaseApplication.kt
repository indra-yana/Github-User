package com.submission.githubuser1

import android.app.Application
import com.submission.githubuser1.datasource.remote.ApiClientConfig
import com.submission.githubuser1.datasource.remote.IApiEndPoint

/****************************************************
 * Created by Indra Muliana
 * On Sunday, 24/10/2021 15.30
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

class BaseApplication : Application() {

    companion object {
        @JvmStatic
        lateinit var remoteApi: IApiEndPoint
    }

    override fun onCreate() {
        super.onCreate()

        remoteApi = ApiClientConfig.initApi(IApiEndPoint::class.java)
    }

}