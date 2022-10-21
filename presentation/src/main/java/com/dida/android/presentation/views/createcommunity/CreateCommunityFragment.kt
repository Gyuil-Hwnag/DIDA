package com.dida.android.presentation.views.createcommunity

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dida.android.R
import com.dida.android.databinding.FragmentCreateCommunityBinding
import com.dida.android.databinding.FragmentDetailCommunityBinding
import com.dida.android.presentation.adapter.community.CreateCommunityNftPagerAdapter
import com.dida.android.presentation.adapter.detailnft.CommunityAdapter
import com.dida.android.presentation.base.BaseFragment
import com.dida.android.presentation.views.nav.community.CommunityViewModel
import com.dida.domain.model.nav.detailnft.Comments
import com.dida.domain.model.nav.detailnft.Community
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CreateCommunityFragment : BaseFragment<FragmentCreateCommunityBinding, CreateCommunityViewModel>(R.layout.fragment_create_community) {

    private val TAG = "CreateCommunityFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_create_community

    override val viewModel : CreateCommunityViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        initAdapter()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collectLatest {
                when(it) {
                    is CreateCommunityNavigationAction.NavigateToSelectNft ->
                        navigate(CreateCommunityFragmentDirections.actionCreateCommunityFragmentToCommunityCommunityInputFragment(true))
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initAdapter() {
        val tabTitleArray = arrayOf(getString(R.string.create_community_like_nft), getString(R.string.create_community_my_nft))

        val viewPager = binding.viewpagerNft
        val tabLayout = binding.tabNft

        viewPager.adapter = CreateCommunityNftPagerAdapter(this@CreateCommunityFragment, lifecycle)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            this.title = resources.getString(R.string.create_community_title)
            this.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            this.setNavigationIcon(R.drawable.ic_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }
}