package com.example.myapplication.tab

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.myapplication.EntryFragment
import com.example.myapplication.MainActivity
import com.example.myapplication.MainFragment
import java.util.*

@Suppress("DEPRECATION")
class TabPagerAdapter(
    fm: FragmentManager,
private val context: Context):
    FragmentPagerAdapter(fm){

    //各タブごとのFragmentインスタンス作成
    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> { return MainFragment() }
            else -> { return EntryFragment() }

        }
    }

    //各タブごとのタイトル付与
    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> { return "ホーム" }
            else -> { return "登録" }

        }
    }

    //タブの最大値をset
    override fun getCount(): Int {
        return 2
    }
}
