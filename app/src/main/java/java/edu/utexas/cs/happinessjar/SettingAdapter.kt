package java.edu.utexas.cs.happinessjar

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import java.edu.utexas.cs.happinessjar.databinding.SettingItemBinding

class SettingAdapter(items: MutableList<String>, icons: MutableList<Int>) : BaseAdapter() {
    private val items = mutableListOf<String>().apply { addAll(items) }
    private val icons = mutableListOf<Int>().apply { addAll(icons) }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): String {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private fun bindView(rowBinding: SettingItemBinding, item: String, icon: Int) {
        rowBinding.itemText.text = item
        rowBinding.itemIcon.setImageResource(icon)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowBinding = SettingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        bindView(rowBinding, getItem(position), icons[position])

        return rowBinding.root
    }

}