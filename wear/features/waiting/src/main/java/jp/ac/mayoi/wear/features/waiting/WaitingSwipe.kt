package jp.ac.mayoi.wear.features.waiting

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

// 画面を横スクロールした際の処理
class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0    -> WaitingFragment()
            1    -> SettingFragment()
            else -> WaitingFragment()
        }
    }
}