package dev.future.testapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dev.future.testapp.databinding.ActivityFragment1Binding
import dev.future.testapp.fragment.HomeFragment
import dev.future.testapp.fragment.SearchFragment

class FragmentActivity_1 : AppCompatActivity() {
    lateinit var binding: ActivityFragment1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragment1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        transparentFragment(HomeFragment())

        binding.fragment1.setOnClickListener {
            transparentFragment(HomeFragment())
        }
        binding.fragment2.setOnClickListener {
            transparentFragment(SearchFragment())
        }

    }

    private fun transparentFragment(fragment: Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.commit()
    }
}