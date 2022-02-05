package kozlov.tinkoff.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kozlov.tinkoff.R
import kozlov.tinkoff.databinding.FragmentSavedBinding


class SavedFragment : Fragment() {

    private var _binding: FragmentSavedBinding? = null
    private val binding: FragmentSavedBinding
        get() = _binding ?: throw RuntimeException("FragmentSavedBinding == null")



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}