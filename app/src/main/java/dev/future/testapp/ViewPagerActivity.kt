package dev.future.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import dev.future.testapp.adapter.ViewPagerAdapter.ViewPagerAdapter

class ViewPagerActivity : AppCompatActivity() {

  var  viewPager: ViewPager2? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)

        viewPager = findViewById<ViewPager2>(R.id.view_pager)

        val framents : ArrayList<Fragment> = arrayListOf(
            Fragment1(),
            Fragment2(),
            Fragment3(),
        )

        val adapter = ViewPagerAdapter(framents,this)
        viewPager?.adapter = adapter
    }

    override fun onBackPressed() {
        if(viewPager?.currentItem == 0){
            super.onBackPressed()
        }else{
            viewPager?.currentItem = viewPager!!.currentItem - 1
        }


    }
}