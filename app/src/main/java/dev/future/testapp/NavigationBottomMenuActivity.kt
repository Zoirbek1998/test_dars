package dev.future.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import dev.future.testapp.databinding.ActivityNavigationBottomMenuBinding
import dev.future.testapp.fragment.HomeFragment

class NavigationBottomMenuActivity : AppCompatActivity() {
    lateinit var binding : ActivityNavigationBottomMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBottomMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        transparentFragment(Fragment1())

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_calendar->{
                    transparentFragment(Fragment1())
                }
                R.id.nav_menu->{
                    transparentFragment(Fragment2())
                }
                R.id.nav_search->{
                    transparentFragment(Fragment3())
                }
            }
            true
        }

    }

    private fun transparentFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_menu_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}