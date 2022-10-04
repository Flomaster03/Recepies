package ru.netology.nerecipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nerecipe.R
import ru.netology.nerecipe.databinding.SingleRecipeBinding
import ru.netology.nerecipe.dto.Recipe


class RecipeAdapter(
    val interactionListener: RecipeInteractionListener
) :
    ListAdapter<Recipe, RecipeAdapter.ViewHolder>(diffCallback) {

    class ViewHolder(
        private val binding: SingleRecipeBinding,
        listener: RecipeInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var recipe: Recipe

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.menuOptions).apply {
                inflate(R.menu.options_menu)
                setOnMenuItemClickListener { MenuItem ->
                    when (MenuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(recipe)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(recipe)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.likes.setOnClickListener { listener.onLikeClicked(post) }
            binding.share.setOnClickListener { listener.onShareClicked(post) }
            binding.optionsMenu.setOnClickListener { popupMenu.show() }

            binding.videoContent.setOnClickListener { listener.onVideoPlayButtonClicked(post) }
            binding.videoPlay.setOnClickListener { listener.onVideoPlayButtonClicked(post) }

            binding.content.setOnClickListener { listener.onSinglePostClicked(post) }
            binding.authorName.setOnClickListener { listener.onSinglePostClicked(post) }
            binding.published.setOnClickListener { listener.onSinglePostClicked(post) }
            binding.avatar.setOnClickListener { listener.onSinglePostClicked(post) }
        }

        fun bind(recipe: Recipe) {
            this.recipe = recipe
            with(binding) {
                title.text = recipe.title
                authorName.text = recipe.authorName
                categoryRecipe.text = recipe.categoryRecipe
                textRecipe.text = recipe.textRecipe
                buttonFavorite.setImageResource(getFavoriteIconResId(recipe.isFavorite))
                buttonFavorite.setOnClickListener {
                    interactionListener.onFavoriteClicked(recipe.id)
                }
            }
        }
    }

    private object diffCallback : DiffUtil.ItemCallback<Recipe>() {

        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SingleRecipeBinding.inflate(
            inflater,
            parent, false
        )
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
    }

}

/*
class RecipeAdapter(private val interactionListener: RecipeInteractionListener) : ListAdapter<Recipe, RecipeAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeBinding.inflate(inflater, parent, false)

        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: RecipeBinding,
        listener: RecipeInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var recipe: Recipe

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.menuOptions).apply {
                inflate(R.menu.option_menu)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(recipe)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(recipe)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        fun bind(recipe: Recipe) {
            this.recipe = recipe
            with(binding) {
                title.text = recipe.title
                authorName.text = recipe.authorName
                categoryRecipe.text = recipe.categoryRecipe
                textRecipe.text = recipe.textRecipe
                buttonFavorite.setImageResource(getFavoriteIconResId(recipe.isFavorite))
                buttonFavorite.setOnClickListener {
                    interactionListener.onFavoriteClicked(recipe.id)
                }
                title.setOnClickListener {
                    interactionListener.onSingleRecipeClicked(recipe)
                }
                textRecipe.setOnClickListener {
                    interactionListener.onSingleRecipeClicked(recipe)
                }
                authorName.setOnClickListener {
                    interactionListener.onSingleRecipeClicked(recipe)
                }
                categoryRecipe.setOnClickListener {
                    interactionListener.onSingleRecipeClicked(recipe)
                }
                menuOptions.setOnClickListener {
                    popupMenu.show()
                }
            }
        }

        @DrawableRes
        private fun getFavoriteIconResId(liked: Boolean) =
            if (liked) R.drawable.ic_is_favourites else R.drawable.ic_is_not_favourites
    }
}

private object DiffCallback : DiffUtil.ItemCallback<Recipe>() {

    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
        oldItem == newItem
}
*/
