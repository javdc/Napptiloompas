package com.javdc.napptiloompas.presentation.ui.about

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.navigation.fragment.findNavController
import com.javdc.napptiloompas.common.util.tryOrNull
import com.javdc.napptiloompas.presentation.R
import com.javdc.napptiloompas.presentation.databinding.FragmentAboutBinding
import com.javdc.napptiloompas.presentation.ui.common.BaseFragment
import com.javdc.napptiloompas.presentation.util.safeNavigateUp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutFragment : BaseFragment<FragmentAboutBinding>(FragmentAboutBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        setUpAppVersionText()
    }

    private fun setUpToolbar() {
        binding?.aboutToolbar?.setNavigationOnClickListener { findNavController().safeNavigateUp() }
    }

    private fun setUpAppVersionText() {
        binding?.aboutAppVersionTextView?.apply {
            text = getAppVersion()?.let { getString(R.string.about_app_version_format, it) }
            movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun getAppVersion(): String? = context?.let {
        tryOrNull { it.packageManager.getPackageInfo(it.packageName, 0).versionName }
    }

}