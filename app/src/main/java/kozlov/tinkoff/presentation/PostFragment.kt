package kozlov.tinkoff.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.tabs.TabLayout
import kozlov.tinkoff.R
import kozlov.tinkoff.databinding.FragmentPostBinding
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
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostBinding.inflate(inflater, container, false)

        showButtonState()
        showRandomPost()
        setupTabLayout()
        setupClickListenerNextButton()
        showLoadingState()
        observeTabPosition()
        setupClickListenerReplayButton()

        return binding.root
    }

    private fun showButtonState() {
        with(binding.replayButton){
            visibility = if (viewModel.positionRandomItem == 0) View.INVISIBLE else View.VISIBLE
        }


    }


    private fun observeTabPosition() {
        viewModel.categoryState.observe(viewLifecycleOwner) {
            binding.tabCategories.getTabAt(it)?.select()
        }
    }

    private fun showLoadingState() {
        viewModel.loadingState.observe(viewLifecycleOwner) {
            with(binding.progressBar) {
                visibility = when (it) {
                    true -> View.VISIBLE
                    false -> View.INVISIBLE
                }
            }
        }
    }

    private fun setupClickListenerNextButton() {
        viewModel.categoryState.observe(viewLifecycleOwner) { category ->
            binding.nextButton.setOnClickListener {

                when (category) {
                    0 -> {
                        logicForRandomCategory()
                    }

                }
            }

        }
    }

    private fun setupClickListenerReplayButton() {
        viewModel.categoryState.observe(viewLifecycleOwner) { category ->
            binding.replayButton.setOnClickListener {

                when (category) {
                    0 -> {
                        viewModel.positionRandomItem--
                        Log.d("position", "${viewModel.positionRandomItem}")
                        showRandomPost()
                        if (viewModel.positionRandomItem == 0) binding.replayButton.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }


    private fun setupTabLayout() {

        binding.tabCategories.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    viewModel.setPositionCategory(it.position)

                    when (it.position) {
                        // 0 -> showRandomPost()
//                        1 -> showCategoryPost(viewModel.latestItem)
//                        2 -> showCategoryPost(viewModel.topItem)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    private fun logicForRandomCategory() {
        with(viewModel) {
            if (positionRandomItem == finishPositionRandom) {
                positionRandomItem++
                Log.d("position", "$positionRandomItem")
                getRandomPost()
                finishPositionRandom = positionRandomItem
            } else {
                positionRandomItem++
                Log.d("position", "$positionRandomItem")
                showRandomPost()
            }
            if (positionRandomItem > 0) binding.replayButton.visibility = View.VISIBLE
        }
    }


    private fun showRandomPost() {
        viewModel.randomItemList.observe(viewLifecycleOwner) {
            Glide.with(binding.root)
                .asGif()
                .listener(object : RequestListener<GifDrawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<GifDrawable>?, isFirstResource: Boolean): Boolean {
                        binding.progressBar.visibility = View.INVISIBLE
                        return false
                    }

                    override fun onResourceReady(
                        resource: GifDrawable?, model: Any?, target: Target<GifDrawable>?, dataSource: DataSource?, isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility = View.INVISIBLE
                        return false
                    }
                })
                .load(it[viewModel.positionRandomItem].image)
                .error(R.drawable.ic_broken_image)
                .into(binding.sourceInclude.imagePost)
            binding.sourceInclude.description.text = it[viewModel.positionRandomItem].description

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}