package tunisStore.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tunisStore.app.databinding.ItemCategorieBinding

class CategorieAdapter(private val items: List<Categorie>) :
    RecyclerView.Adapter<CategorieAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCategorieBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategorieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.categoryName.text = item.nom
        holder.binding.icon.setImageResource(item.iconRes)
    }

    override fun getItemCount() = items.size
}
