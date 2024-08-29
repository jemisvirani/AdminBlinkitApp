package com.eclatsol.adminblinkitclone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.eclatsol.adminblinkitclone.R
import com.eclatsol.adminblinkitclone.databinding.ItemViewProductCategoryBinding
import com.eclatsol.adminblinkitclone.models.Categories

class AdapterCategory(
    private val context: Context,
    private val categoryList: ArrayList<Categories>,
    val onCategoryClicked : (Categories) -> Unit
) : RecyclerView.Adapter<AdapterCategory.CategoryViewHolder>() {

    class CategoryViewHolder(val binding: ItemViewProductCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(ItemViewProductCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        holder.binding.apply {
            ivCategoryImage.setImageResource(category.icon)
            tvCategoryTitle.text = category.category

            if (position == 0){
                ivCategoryImage.imageTintList = ContextCompat.getColorStateList(context, R.color.white)
            }
        }

        holder.itemView.setOnClickListener{
            onCategoryClicked(category)
        }

    }
}