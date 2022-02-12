package uz.mobiler.valutekurs.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.tab_item.view.*
import uz.mobiler.valutekurs.R
import uz.mobiler.valutekurs.adapters.HomeRvAdapters
import uz.mobiler.valutekurs.adapters.ViewPagerAdapters
import uz.mobiler.valutekurs.database.AppDatabase
import uz.mobiler.valutekurs.databinding.FragmentHomeBinding
import uz.mobiler.valutekurs.models.Valute

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var appDatabase: AppDatabase
    lateinit var homeRvAdapters: HomeRvAdapters
    lateinit var viewPagerAdapters: ViewPagerAdapters
    lateinit var titleList: ArrayList<String>
    lateinit var list: ArrayList<Valute>
    lateinit var adapterList: ArrayList<Valute>

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        appDatabase = AppDatabase.getInctance(requireContext())
        titleList = ArrayList()
        adapterList = ArrayList()
        adapterList = appDatabase.valuteDao().getAllValute() as ArrayList



        viewPagerAdapters = ViewPagerAdapters()
        binding.viewPager.adapter = viewPagerAdapters

        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab, position -> }.attach()

        TabLayoutMediator(binding.tab, binding.viewPager
        ) { tab, position -> }.attach()

        homeViewModel.getValute().observe(viewLifecycleOwner) {
            binding.progress.visibility=View.INVISIBLE
            binding.tv.visibility=View.VISIBLE

            homeRvAdapters = HomeRvAdapters()

            for (valute in it) {
                if (appDatabase.valuteDao().getAllValuteByDate(valute.date,valute.code).isEmpty()){
                    appDatabase.valuteDao().insertValue(valute)
                }
            }

            it.forEach { valute ->
                titleList.add(valute.code)
            }

            viewPagerAdapters.notifyDataSetChanged()
            viewPagerAdapters.submitList(it)
            setTabs()
            rvAdapter(it[0])
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun rvAdapter(valute: Valute) {
        val data = appDatabase.valuteDao().getAllValuteByDateRv(
            valute.date,
            valute.code
        ).reversed()
        homeRvAdapters.setAdpater(data)
        homeRvAdapters.notifyDataSetChanged()
        binding.homeRv.adapter = homeRvAdapters
    }

    private fun setTabs() {
        for (i in 0 until binding.tabLayout.tabCount) {
            val tabBind =
                LayoutInflater.from(requireContext()).inflate(R.layout.tab_item, null, false)
            val tab = binding.tabLayout.getTabAt(i)
            tab?.customView = tabBind

            tabBind?.title?.text = titleList[i]

            if (i == 0) {
                tabBind?.title?.setBackgroundResource(R.drawable.tab_back_selected)
                tabBind?.title?.setTextColor(resources.getColor(R.color.white))
            } else {
                tabBind?.title?.setBackgroundResource(R.drawable.tab_back_unselected)
                tabBind?.title?.setTextColor(resources.getColor(R.color.tab_color))
            }
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val view = tab?.customView
                view?.title?.setBackgroundResource(R.drawable.tab_back_selected)
                view?.title?.setTextColor(resources.getColor(R.color.white))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val view = tab?.customView
                view?.title?.setBackgroundResource(R.drawable.tab_back_unselected)
                view?.title?.setTextColor(resources.getColor(R.color.tab_color))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }
}