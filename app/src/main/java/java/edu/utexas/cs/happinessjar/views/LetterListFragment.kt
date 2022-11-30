package java.edu.utexas.cs.happinessjar.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import java.edu.utexas.cs.happinessjar.R
import java.edu.utexas.cs.happinessjar.databinding.FragmentLetterListBinding
import java.edu.utexas.cs.happinessjar.models.LetterModel
import java.edu.utexas.cs.happinessjar.utils.LetterAdapter

class LetterListFragment : Fragment(R.layout.fragment_letter_list) {
    companion object {
        fun newInstance(): LetterListFragment {
            return LetterListFragment()
        }
    }

    private val viewModel: LetterModel by activityViewModels()
    private var _binding: FragmentLetterListBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLetterListBinding.bind(view)

        val rv = binding.rv
        val letterAdapter = LetterAdapter(viewModel)

        val itemDecor = DividerItemDecoration(rv.context, LinearLayoutManager.VERTICAL)
        rv.addItemDecoration(itemDecor)
        rv.adapter = letterAdapter
        rv.layoutManager = LinearLayoutManager(rv.context)

        viewModel.observeLetters().observe(viewLifecycleOwner) {
            letterAdapter.submitList(it)
            letterAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}