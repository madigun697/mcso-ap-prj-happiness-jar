package java.edu.utexas.cs.happinessjar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.edu.utexas.cs.happinessjar.databinding.FragmentMessageBinding

class MessageFragment : Fragment() {
    companion object {
        fun newInstance(): MessageFragment {
            val frag = MessageFragment()
            return frag
        }
    }

    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }
}