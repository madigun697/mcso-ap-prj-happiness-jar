package java.edu.utexas.cs.happinessjar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import java.edu.utexas.cs.happinessjar.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    companion object {
        fun newInstance(): HomeFragment {
            val frag = HomeFragment()
            return frag
        }
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
}