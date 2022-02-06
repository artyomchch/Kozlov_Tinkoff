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
        with(binding.replayButton) {
            visibility = if (viewModel.positionRandomItem == 0) View.INVISIBLE else View.VISIBLE
        }
    }

    private fun showCategoryButtonState(category: Int) {
        with(binding.replayButton) {
            when (category) {
                0 -> visibility = if (viewModel.positionRandomItem == 0) View.INVISIBLE else View.VISIBLE
                1 -> visibility = if (viewModel.positionLatestItem == 0) View.INVISIBLE else View.VISIBLE
                2 -> visibility = if (viewModel.positionTopItem == 0) View.INVISIBLE else View.VISIBLE
            }
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
                    0 -> logicForRandomCategory()
                    1 -> logicForLatestCategory()
                    2 -> logicForTopCategory()
                }
            }
        }
    }

    private fun setupClickListenerReplayButton() {
        viewModel.categoryState.observe(viewLifecycleOwner) { category ->
            binding.replayButton.setOnClickListener {

                when (category) {
                    0 -> backLogicForRandomCategory()
                    1 -> backLogicForLatestCategory()
                    2 -> backLogicForTopCategory()
                }

            }
        }
    }

    private fun setupTabLayout() {

        binding.tabCategories.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    viewModel.setPositionCategory(it.position)
                    when (tab.position) {
                        0 -> showRandomPost()
                        1 -> showLatestPost()
                        2 -> showTopPost()
                    }
                    showCategoryButtonState(tab.position)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun logicForRandomCategory() {
        with(viewModel) {
            if (positionRandomItem == finishPositionRandom) {
                positionRandomItem++
                Log.d("position random post", "$positionRandomItem")
                getRandomPost()
                finishPositionRandom = positionRandomItem
            } else {
                positionRandomItem++
                Log.d("position random post", "$positionRandomItem")
                showRandomPost()
            }
            if (positionRandomItem > 0) binding.replayButton.visibility = View.VISIBLE
        }
    }

    private fun logicForLatestCategory() {
        with(viewModel) {
            if (positionLatestItem == finishPositionLatest) {
                positionLatestItem++
                Log.d("position latest post", "$positionLatestItem")
                getLatestPosts(pageLatest)
                pageLatest++
                finishPositionLatest += 20
            } else {
                positionLatestItem++
                Log.d("position latest post", "$positionLatestItem")
                showLatestPost()
            }
            if (positionLatestItem > 0) binding.replayButton.visibility = View.VISIBLE
        }
    }

    private fun logicForTopCategory() {
        with(viewModel) {
            if (positionTopItem == finishPositionTop) {
                positionTopItem++
                Log.d("position top post", "$positionTopItem")
                getTopPosts(pageTop)
                pageTop++
                finishPositionTop += 20
            } else {
                positionTopItem++
                Log.d("position top post", "$positionTopItem")
                showTopPost()
            }
            if (positionTopItem > 0) binding.replayButton.visibility = View.VISIBLE
        }
    }

    private fun backLogicForTopCategory() {
        with(viewModel) {
            positionTopItem--
            Log.d("position", "$positionTopItem")
            showTopPost()
            if (positionTopItem == 0) binding.replayButton.visibility = View.INVISIBLE
        }
    }

    private fun backLogicForLatestCategory() {
        with(viewModel) {
            positionLatestItem--
            Log.d("position", "$positionLatestItem")
            showLatestPost()
            if (positionLatestItem == 0) binding.replayButton.visibility = View.INVISIBLE
        }
    }

    private fun backLogicForRandomCategory() {
        with(viewModel) {
            positionRandomItem--
            Log.d("position", "$positionRandomItem")
            showRandomPost()
            if (positionRandomItem == 0) binding.replayButton.visibility = View.INVISIBLE
        }
    }

    private fun showLatestPost() {
        viewModel.latestItem.observe(viewLifecycleOwner) {
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
                .load(it[viewModel.positionLatestItem].image)
                .error(R.drawable.ic_broken_image)
                .into(binding.sourceInclude.imagePost)
            binding.sourceInclude.description.text = it[viewModel.positionLatestItem].description

        }
    }

    private fun showTopPost() {
        viewModel.topItem.observe(viewLifecycleOwner) {
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
                .load(it[viewModel.positionTopItem].image)
                .error(R.drawable.ic_broken_image)
                .into(binding.sourceInclude.imagePost)
            binding.sourceInclude.description.text = it[viewModel.positionTopItem].description
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