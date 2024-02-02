package com.javdc.napptiloompas.presentation.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.javdc.napptiloompas.presentation.model.GenderEnum
import com.javdc.napptiloompas.domain.util.AsyncResult
import com.javdc.napptiloompas.presentation.R
import com.javdc.napptiloompas.presentation.model.ProfessionEnum
import com.javdc.napptiloompas.presentation.databinding.FragmentOompaLoompasBinding
import com.javdc.napptiloompas.presentation.ui.common.BaseFragment
import com.javdc.napptiloompas.presentation.ui.common.PaginationScrollListener
import com.javdc.napptiloompas.presentation.util.safeNavigate
import com.javdc.napptiloompas.presentation.util.setUpExitAndReenterTransitionsForContainerTransform
import com.javdc.napptiloompas.presentation.util.setUpPostponedEnterTransition
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OompaLoompasFragment : BaseFragment<FragmentOompaLoompasBinding>(FragmentOompaLoompasBinding::inflate) {

    private val viewModel by viewModels<OompaLoompasViewModel>()

    private var oompaLoompasAdapter: OompaLoompaAdapter? = null
    private var paginationScrollListener: PaginationScrollListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPostponedEnterTransition(view)
        setUpToolbar()
        setUpFilterChips()
        setUpRecyclerView()
        observeOompaLoompas()
        observeFilters()
    }

    private fun setUpToolbar() {
        binding?.apply {
            oompaLoompasToolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.home_about -> {
                        findNavController().safeNavigate(OompaLoompasFragmentDirections.actionOompaLoompasFragmentToAboutFragment())
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }
    }

    private fun setUpFilterChips() {
        binding?.apply {
            oompaLoompasProfessionsFilterChip.apply {
                setOnClickListener {
                    val selectedProfessions = viewModel.filtersLiveData.value?.professionsFilter.orEmpty().toMutableList()
                    MaterialAlertDialogBuilder(root.context)
                        .setTitle(R.string.filter_profession_dialog_title)
                        .setPositiveButton(android.R.string.ok, null)
                        .setMultiChoiceItems(
                            R.array.professions,
                            ProfessionEnum.entries.map { selectedProfessions.contains(it) }.toBooleanArray()
                        ) { _, which, isChecked ->
                            val selection = ProfessionEnum.entries.getOrNull(which) ?: return@setMultiChoiceItems
                            if (isChecked) selectedProfessions.add(selection) else selectedProfessions.remove(selection)
                            viewModel.setProfessionsFilter(selectedProfessions)
                        }
                        .show()
                }
                setOnCloseIconClickListener {
                    viewModel.setProfessionsFilter(emptyList())
                }
            }
            oompaLoompasGendersFilterChip.apply {
                setOnClickListener {
                    val selectedGenders = viewModel.filtersLiveData.value?.gendersFilter.orEmpty().toMutableList()
                    MaterialAlertDialogBuilder(root.context)
                        .setTitle(R.string.filter_gender_dialog_title)
                        .setPositiveButton(android.R.string.ok, null)
                        .setMultiChoiceItems(
                            R.array.genders,
                            GenderEnum.entries.map { selectedGenders.contains(it) }.toBooleanArray()
                        ) { _, which, isChecked ->
                            val selection = GenderEnum.entries.getOrNull(which) ?: return@setMultiChoiceItems
                            if (isChecked) selectedGenders.add(selection) else selectedGenders.remove(selection)
                            viewModel.setGendersFilter(selectedGenders)
                        }
                        .show()
                }
                setOnCloseIconClickListener {
                    viewModel.setGendersFilter(emptyList())
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        binding?.oompaLoompasRecyclerView?.apply {
            oompaLoompasAdapter = OompaLoompaAdapter { view, oompaLoompa ->
                setUpExitAndReenterTransitionsForContainerTransform()
                findNavController().safeNavigate(
                    OompaLoompasFragmentDirections.actionOompaLoompasFragmentToOompaLoompaDetailsFragment(oompaLoompa.id),
                    FragmentNavigatorExtras(view to getString(R.string.oompa_loompa_details_transition_name))
                )
            }
            adapter = oompaLoompasAdapter
            paginationScrollListener = PaginationScrollListener(
                layoutManager = layoutManager as? LinearLayoutManager ?: return@apply,
                loadMore = { viewModel.fetchOompaLoompas() }
            ).also {
                addOnScrollListener(it)
            }
        }
    }

    private fun observeOompaLoompas() {
        viewModel.oompasLiveData.observe(viewLifecycleOwner) { result ->
            binding?.apply {
                when (result) {
                    is AsyncResult.Loading -> {
                        oompaLoompasCircularProgressIndicator.show()
                        oompaLoompasErrorView.hide()
                        paginationScrollListener?.isLoading = true
                    }

                    is AsyncResult.Error -> {
                        oompaLoompasCircularProgressIndicator.hide()
                        oompaLoompasErrorView.show(result.error) { viewModel.retry() }
                        oompaLoompasAdapter?.submitList(emptyList())
                        paginationScrollListener?.isLoading = false
                    }

                    is AsyncResult.Success -> {
                        oompaLoompasCircularProgressIndicator.hide()
                        oompaLoompasErrorView.hide()
                        oompaLoompasAdapter?.submitList(result.data)
                        paginationScrollListener?.isLoading = false
                    }
                }
            }
        }
    }

    private fun observeFilters() {
        viewModel.filtersLiveData.observe(viewLifecycleOwner) {
            binding?.apply {
                val professionsFilterCount = it.professionsFilter.size
                val gendersFilterCount = it.gendersFilter.size
                oompaLoompasProfessionsFilterChip.apply {
                    if (professionsFilterCount == 0) {
                        text = getString(R.string.filter_profession)
                        isCloseIconVisible = false
                    } else {
                        text = resources.getQuantityString(R.plurals.filter_selected_professions, professionsFilterCount, professionsFilterCount)
                        isCloseIconVisible = true
                    }
                }
                oompaLoompasGendersFilterChip.apply {
                    if (gendersFilterCount == 0) {
                        text = getString(R.string.filter_gender)
                        isCloseIconVisible = false
                    } else {
                        text = resources.getQuantityString(R.plurals.filter_selected_genders, gendersFilterCount, gendersFilterCount)
                        isCloseIconVisible = true
                    }
                }
            }
        }
    }

}