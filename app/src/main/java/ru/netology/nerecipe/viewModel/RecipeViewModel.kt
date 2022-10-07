package ru.netology.nerecipe.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nerecipe.adapter.RecipeInteractionListener
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.repository.RecipeRepositoryImpl
import ru.netology.nerecipe.db.AppDb
import ru.netology.nerecipe.repository.RecipeRepository
import ru.netology.nmedia.util.SingleLiveEvent

class RecipeViewModel(application: Application) : AndroidViewModel(application),
    RecipeInteractionListener {

    private val repository: RecipeRepository =
        RecipeRepositoryImpl(dao = AppDb.getInstance(context = application).recipeDao)

    val data by repository::data
    var filterIsActive = false
    val toFavoriteFragment = SingleLiveEvent<String>()
    val toCreateFragment = SingleLiveEvent<Unit>()
    val toUpdateFragment = SingleLiveEvent<String?>()
    val toSingleFragment = SingleLiveEvent<Long>()
    val toFilterFragment = SingleLiveEvent<Unit>()
    val updateRecipe = MutableLiveData<Recipe>(null)
    val singleRecipe = MutableLiveData<Recipe?>(null)
    val feedFragment = data
    private val currentRecipe = MutableLiveData<Recipe?>(null)


    fun clearFilter() {
        repository.getData()
    }

    override fun updateContent(
        id: Long,
        title: String,
        authorName: String,
        categoryRecipe: String,
        textRecipe: String
    ) {
        val recipe = Recipe(
            id = id,
            title = title,
            authorName = authorName,
            categoryRecipe = categoryRecipe,
            textRecipe = textRecipe
        )
        repository.save(recipe)
    }

    override fun onRemoveClicked(recipe: Recipe) {
        repository.delete(recipe)
    }

    override fun onEditClicked(recipe: Recipe) {
        updateRecipe.value = recipe
        toUpdateFragment.call()
    }

    override fun onFavouriteClicked(recipeId: Long) {
        repository.favourite(recipeId)
    }

    override fun onSearchClicked(text: String) {
        repository.searchText(text)
    }

    override fun onCreateClicked() {
        toCreateFragment.call()
    }

    override fun onSaveClicked(title: String, authorName: String, categoryRecipe: String, textRecipe: String) {

        val recipe = Recipe(
            id = RecipeRepository.NEW_ID,
            title = title,
            authorName = authorName,
            categoryRecipe = categoryRecipe,
            textRecipe = textRecipe
        )
        repository.save(recipe)
        currentRecipe.value = null
    }

    override fun onSingleRecipeClicked(recipe: Recipe) {
        singleRecipe.value = recipe
        toSingleFragment.call()
    }

    var toggleCheckEuropean = true
    var toggleCheckAsian = true
    var toggleCheckPanasian = true
    var toggleCheckEastern = true
    var toggleCheckAmerican = true
    var toggleCheckRussian = true
    var toggleCheckMediterranean = true

    fun showEuropean(categoryRecipe: String) {
        repository.showEuropean(categoryRecipe)
        filterIsActive = true
        toggleCheckEuropean = false
    }

    fun showAsian(categoryRecipe: String) {
        repository.showAsian(categoryRecipe)
        filterIsActive = true
        toggleCheckAsian = false
    }

    fun showPanasian(categoryRecipe: String) {
        repository.showPanasian(categoryRecipe)
        filterIsActive = true
        toggleCheckPanasian = false
    }

    fun showEastern(categoryRecipe: String) {
        repository.showEastern(categoryRecipe)
        filterIsActive = true
        toggleCheckEastern = false
    }

    fun showAmerican(categoryRecipe: String) {
        repository.showAmerican(categoryRecipe)
        filterIsActive = true
        toggleCheckAmerican = false
    }

    fun showRussian(categoryRecipe: String) {
        repository.showRussian(categoryRecipe)
        filterIsActive = true
        toggleCheckRussian = false
    }

    fun showMediterranean(categoryRecipe: String) {
        repository.showMediterranean(categoryRecipe)
        filterIsActive = true
        toggleCheckMediterranean = false
    }


}