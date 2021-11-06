@file:Suppress("unused")

package com.submission.githubuser1.helper

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.submission.githubuser1.R
import com.submission.githubuser1.datasource.remote.response.ResponseStatus
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

/****************************************************
 * Created by Indra Muliana (indra.ndra26@gmail.com)
 * On Sunday, 24/10/2021 19.24
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

private const val TAG = "Utils"

fun Context.toast(msg: String, long: Boolean = false) {
    Toast.makeText(this, msg, if (long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.snackBar(
    msg: String,
    long: Boolean = false,
    anchor: View? = null,
    actionName: String = resources.getString(R.string.text_btn_retry),
    action: (() -> Unit)? = null
) {
    Snackbar.make(this, msg, if (long) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT).apply {
        action?.let {
            setAction(actionName) {
                it()
            }
        }

        anchor?.let {
            anchorView = it
        }
    }.show()
}

fun Fragment.handleRequestError(failure: ResponseStatus.Failure, action: (() -> Unit)? = null) {
    val anchor = null

    when (val exception = failure.exception) {
        is HttpException -> {
            when (val errorCode = exception.code()) {
                401 -> requireView().snackBar(getString(R.string.error_bad_request, errorCode), anchor = anchor)
                403 -> requireView().snackBar(getString(R.string.error_not_authorize, errorCode), anchor = anchor)
                404 -> requireView().snackBar(getString(R.string.error_not_found, errorCode), anchor = anchor)
                500 -> requireView().snackBar(getString(R.string.error_internal_server_error, errorCode), anchor = anchor)
                else -> requireView().snackBar(getString(R.string.error_http_failure, errorCode), anchor = anchor)
            }

            Log.e(TAG, "handleRequestError: ${exception.response()?.errorBody()?.string().toString()}")
        }
        is UnknownHostException -> {
            requireView().snackBar(getString(R.string.error_unknown_host), long = true, anchor = anchor, action = action)

            Log.e(TAG,"handleRequestError: ${exception.message}")
        }
        is ConnectException -> {
            requireView().snackBar(getString(R.string.error_connection), long = true, anchor = anchor, action = action)

            Log.e(TAG,"handleRequestError: ${exception.message}")
        }
        else -> {
            requireView().snackBar(getString(R.string.error_unknown), long = true, anchor = anchor, action = action)

            Log.e(TAG,"handleRequestError: ${exception.message} ${exception.stackTrace}")
        }
    }
}

fun showInputKey(view: View, show: Boolean) {
    when (show) {
        true -> {
            view.requestFocus().run {
                val input = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                input.showSoftInput(view, InputMethodManager.SHOW_FORCED)
            }
        }
        false -> {
            view.requestFocus().run {
                val input = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                input.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }
}

fun showInputKey(context: Context, show: Boolean) {
    val input = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    input.toggleSoftInput(if (show) InputMethodManager.SHOW_FORCED else InputMethodManager.RESULT_HIDDEN, 0)
}

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        // .apply(RequestOptions().override(500, 500))
        .placeholder(ContextCompat.getDrawable(this.context, R.drawable.img_placeholder))
        .error(ContextCompat.getDrawable(this.context, R.drawable.img_placeholder))
        .centerCrop()
        .into(this)
}