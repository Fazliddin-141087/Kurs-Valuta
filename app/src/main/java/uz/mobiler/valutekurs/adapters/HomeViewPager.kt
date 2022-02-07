package uz.mobiler.valutekurs.adapters

import androidx.fragment.app.*
import uz.mobiler.valutekurs.ViewPagerFragment
import uz.mobiler.valutekurs.models.Valute

class HomeViewPager(var valuteList:ArrayList<Valute>,fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)  {

    override fun getCount(): Int {
        return valuteList.size
    }

    override fun getItem(position: Int): Fragment {
         return  ViewPagerFragment.newInstance(valuteList[position])
    }

}