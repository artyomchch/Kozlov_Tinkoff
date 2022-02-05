package kozlov.tinkoff.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kozlov.tinkoff.R
import kozlov.tinkoff.data.repository.PostRepositoryImpl
import kozlov.tinkoff.databinding.FragmentPostBinding
import kozlov.tinkoff.databinding.FragmentSavedBinding
import kozlov.tinkoff.domain.repository.PostRepository
import kozlov.tinkoff.utils.App
import javax.inject.Inject


class PostFragment : Fragment() {

    private var _binding: FragmentPostBinding? = null
    private val binding: FragmentPostBinding
        get() = _binding ?: throw RuntimeException("FragmentPostBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[(PostFragmentViewModel::class.java)]
    }

    private val component by lazy {
        (requireActivity().application as App).component
    }

    override fun onAttach(context: Context) {
        component
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostBinding.inflate(inflater, container, false)
        val a = PostRepositoryImpl()
        CoroutineScope(Dispatchers.IO).launch {
          //  a.getRandomPost()
          //  a.getLatestPost(1)
            a.getTopPost(1)
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}