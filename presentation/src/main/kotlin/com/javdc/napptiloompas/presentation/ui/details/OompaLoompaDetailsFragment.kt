package com.javdc.napptiloompas.presentation.ui.details

import android.os.Bundle
import android.view.View
import androidx.core.text.parseAsHtml
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.javdc.napptiloompas.domain.model.OompaLoompaBo
import com.javdc.napptiloompas.domain.util.AsyncResult
import com.javdc.napptiloompas.presentation.R
import com.javdc.napptiloompas.presentation.model.GenderEnum
import com.javdc.napptiloompas.presentation.databinding.FragmentOompaLoompaDetailsBinding
import com.javdc.napptiloompas.presentation.ui.common.BaseFragment
import com.javdc.napptiloompas.presentation.util.safeNavigateUp
import com.javdc.napptiloompas.presentation.util.setUpSharedElementEnterTransitionForContainerTransform
import com.javdc.napptiloompas.presentation.util.showSimpleDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OompaLoompaDetailsFragment : BaseFragment<FragmentOompaLoompaDetailsBinding>(FragmentOompaLoompaDetailsBinding::inflate) {

    private val viewModel by viewModels<OompaLoompaDetailsViewModel>()
    private val args: OompaLoompaDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpSharedElementEnterTransitionForContainerTransform()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        observeOompaLoompaDetails()
        viewModel.fetchOompaLoompaDetails(args.id)
    }

    private fun setUpToolbar() {
        binding?.oompaLoompaDetailsToolbar?.setNavigationOnClickListener { findNavController().safeNavigateUp() }
    }

    private fun observeOompaLoompaDetails() {
        viewModel.oompaLoompaDetailsLiveData.observe(viewLifecycleOwner) { result ->
            binding?.apply {
                when (result) {
                    is AsyncResult.Loading -> {
                        oompaLoompaDetailsCircularProgressIndicator.show()
                        oompaLoompaDetailsErrorView.hide()
                    }
                    is AsyncResult.Error -> {
                        oompaLoompaDetailsCircularProgressIndicator.hide()
                        oompaLoompaDetailsInformationConstraintLayout.isVisible = false
                        oompaLoompaDetailsErrorView.show(result.error) {
                            viewModel.fetchOompaLoompaDetails(args.id)
                        }
                    }
                    is AsyncResult.Success -> {
                        oompaLoompaDetailsCircularProgressIndicator.hide()
                        oompaLoompaDetailsInformationConstraintLayout.isVisible = true
                        showOompaLoompaDetails(result.data)
                        oompaLoompaDetailsErrorView.hide()
                    }
                }
            }
        }
    }

    private fun showOompaLoompaDetails(oompaLoompa: OompaLoompaBo) {
        binding?.apply {
            Glide
                .with(root.context)
                .load(oompaLoompa.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .circleCrop()
                .into(oompaLoompaDetailsPhotoImageView)
            oompaLoompaDetailsNameTextView.text = getString(R.string.details_name_format, oompaLoompa.firstName, oompaLoompa.lastName)
            oompaLoompaDetailsAgeTextView.text = oompaLoompa.age?.toString()
            oompaLoompaDetailsProfessionTextView.text = oompaLoompa.profession
            oompaLoompaDetailsGenderImageView.apply {
                when (GenderEnum.getByCode(oompaLoompa.gender)) {
                    GenderEnum.MALE -> setImageResource(R.drawable.ic_gender_male)
                    GenderEnum.FEMALE -> setImageResource(R.drawable.ic_gender_female)
                    else -> isVisible = false
                }
            }
            oompaLoompaDetailsEmailTextView.text = oompaLoompa.email
            oompaLoompaDetailsDescriptionTextView.text = oompaLoompa.description?.parseAsHtml()?.trim()
            oompaLoompaDetailsFavoriteColorTextView.text = oompaLoompa.favorite?.color
            oompaLoompaDetailsFavoriteFoodTextView.text = oompaLoompa.favorite?.food
            oompaLoompaDetailsFavoriteSongTextView.text = oompaLoompa.favorite?.song
            oompaLoompaDetailsFavoriteSongExpandButton.setOnClickListener {
                oompaLoompaDetailsFavoriteSongTextView.maxLines = Int.MAX_VALUE
                oompaLoompaDetailsFavoriteSongExpandButton.isVisible = false
            }
            oompaLoompaDetailsQuotaTextView.text = oompaLoompa.quota?.substring(0, 120)?.plus(Typography.ellipsis)
            oompaLoompaDetailsShowQuotaButton.setOnClickListener {
                showSimpleDialog(
                    title = getString(R.string.details_quota_title),
                    message = oompaLoompa.quota,
                    positiveText = getString(android.R.string.ok),
                )
            }
        }
    }

}