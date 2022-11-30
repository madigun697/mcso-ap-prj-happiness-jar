package java.edu.utexas.cs.happinessjar.utils

import android.app.AlertDialog
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.edu.utexas.cs.happinessjar.R
import java.edu.utexas.cs.happinessjar.databinding.LetterModalBinding
import java.edu.utexas.cs.happinessjar.databinding.RowBinding
import java.edu.utexas.cs.happinessjar.models.Letter
import java.edu.utexas.cs.happinessjar.models.LetterModel

class LetterAdapter(private val viewModel: LetterModel)
    : ListAdapter<Letter, LetterAdapter.VH>(Diff()) {
    class Diff: DiffUtil.ItemCallback<Letter>() {
        override fun areItemsTheSame(oldItem: Letter, newItem: Letter): Boolean {
            return oldItem.firestoreID == newItem.firestoreID
        }

        override fun areContentsTheSame(oldItem: Letter, newItem: Letter): Boolean {
            return oldItem.firestoreID == newItem.firestoreID
                    && oldItem.title == newItem.title
                    && oldItem.body == newItem.body
                    && oldItem.timeStamp == newItem.timeStamp
        }
    }

    inner class VH(private val rowBinding: RowBinding, private val parent: ViewGroup) :
            RecyclerView.ViewHolder(rowBinding.root) {
                fun bind(holder: VH, position: Int) {
                    val letter = viewModel.getLetter(position)
                    holder.rowBinding.title.text = letter.title
                    holder.rowBinding.timestamp.text = DateFormat.format("yyyy-MM-dd", letter.timeStamp?.toDate())
                    holder.rowBinding.title.setOnClickListener {
                        modal(parent, letter)
                    }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val rowBinding = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(rowBinding, parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(holder, position)
    }

    fun modal(parent: ViewGroup, letter: Letter) {
        val builder = AlertDialog.Builder(parent.context, R.style.CustomAlertDialog).create()
        val view = LayoutInflater.from(parent.context).inflate(R.layout.letter_modal,null)
        val modalBinding = LetterModalBinding.bind(view)
        modalBinding.letterTitle.text = letter.title
        modalBinding.letterBody.text = letter.body
        val button = view.findViewById<Button>(R.id.dialogDismiss_button)
        button.setOnClickListener { builder.dismiss() }
        with (builder) {
            setView(view)
            setCanceledOnTouchOutside(false)
            show()
        }
    }

}