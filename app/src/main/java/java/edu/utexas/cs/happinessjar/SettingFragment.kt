package java.edu.utexas.cs.happinessjar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.edu.utexas.cs.happinessjar.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    companion object {
        fun newInstance(): SettingFragment {
            val frag = SettingFragment()
            return frag
        }
    }

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)

        val settingItems = mutableListOf("Profile settings", "Letters")
        val settingIcons = mutableListOf(R.drawable.ic_baseline_person_24, R.drawable.ic_baseline_library_books_24)
        val settingAdapter = SettingAdapter(this.requireContext(), settingItems, settingIcons)

        binding.settingList.adapter = settingAdapter
        binding.settingList.setOnItemClickListener { parent, view, position, id ->
            Log.d("Setting", settingItems[position])
        }

        return binding.root
    }
}