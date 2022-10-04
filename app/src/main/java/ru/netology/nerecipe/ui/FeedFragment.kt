package ru.netology.nerecipe.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nerecipe.R
import ru.netology.nerecipe.adapter.RecipeAdapter
import ru.netology.nerecipe.viewModel.RecipeViewModel

class FeedFragment : Fragment() {

    private val viewModel by activityViewModels<RecipeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.favoriteFragment.observe(this) {
            val direction = FeedRecipeFragmentDirections.favoriteFragment()
            findNavController().navigate(direction)
        }

        viewModel.updateRecipeFragment.observe(this) {
            val updatedRecipe = viewModel.updateRecipe.value
            val directions = FeedRecipeFragmentDirections.updateRecipeFragment(updatedRecipe)
            findNavController().navigate(directions)
        }

        viewModel.createFragment.observe(this) {
            val directions = FeedRecipeFragmentDirections.recipeCreateFragment()
            findNavController().navigate(directions)
        }

        viewModel.singleFragment.observe(this) {
            val viewRecipe = viewModel.singleRecipe.value
            val directions = FeedRecipeFragmentDirections.recipeViewFragment(viewRecipe)
            findNavController().navigate(directions)
        }

        viewModel.filterFragment.observe(this) {
            val directions = FeedRecipeFragmentDirections.recipeFilterFragment()
            findNavController().navigate(directions)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = EmptyFieldBinding.inflate(layoutInflater, container, false).also { binding ->

        val recipeAdapter = RecipeAdapter(viewModel)
        binding.listRecipe.adapter = recipeAdapter

        fun viewRecipe() {
            viewModel.data.observe(viewLifecycleOwner) { recipe ->
                recipeAdapter.submitList(recipe)
            }
        }
        viewRecipe()

        if (viewModel.filterIsActive) {
            binding.buttonClearFilter.isVisible = viewModel.filterIsActive
            binding.buttonClearFilter.setOnClickListener {
                viewModel.clearFilter()
                viewModel.filterIsActive = false
                binding.buttonClearFilter.visibility = View.GONE
                viewRecipe()
                viewModel.toggleCheckEuropean = true
                viewModel.toggleCheckPanasian = true
                viewModel.toggleCheckAmerican = true
                viewModel.toggleCheckEastern = true
                viewModel.toggleCheckMediterranean = true
                viewModel.toggleCheckRussian = true
                viewModel.toggleCheckAsian = true
            }
        } else {
            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {

                    if (newText.isNotBlank()) {
                        viewModel.onSearchClicked(newText)
                        viewRecipe()
                    }
                    if (TextUtils.isEmpty(newText)){
                        viewModel.clearFilter()
                        viewRecipe()
                    }
                    return false
                }
            })
        }

        binding.bottomToolbar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favourites -> {
                    viewModel.favoriteFragment.call()
                    true
                }
                R.id.filter -> {
                    viewModel.filterFragment.call()
                    viewModel.clearFilter()
                    true
                }
                else -> false
            }
        }

        binding.buttonAdd.setOnClickListener {
            viewModel.onCreateClicked()
        }

    }.root
}



