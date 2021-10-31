package com.submission.githubuser1.view.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.submission.githubuser1.R
import com.submission.githubuser1.databinding.FragmentSplashBinding
import com.submission.githubuser1.helper.Constant

class SplashFragment : Fragment() {

    private lateinit var splashBinding: FragmentSplashBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        splashBinding = FragmentSplashBinding.inflate(inflater, container, false)

        return splashBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }, Constant.DELAY_SPLASH)
    }
}