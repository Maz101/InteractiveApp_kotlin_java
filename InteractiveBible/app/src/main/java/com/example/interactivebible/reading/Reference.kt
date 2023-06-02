package com.example.interactivebible.reading

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.interactivebible.databinding.ActivityReferenceBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.example.interactivebible.R


class Reference : AppCompatActivity() {

    private lateinit var binding: ActivityReferenceBinding
    private var tabicon = R.drawable.ic_baseline_summarize_18

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReferenceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewpager = binding.vpPager
        val tablayout: TabLayout = binding.tabs


        viewpager.adapter = ViewPagerAdapter(supportFragmentManager,lifecycle)

//
//        configureTabLayout()

        TabLayoutMediator(tablayout, viewpager) { tab, position ->
            val tabNames = listOf("BOOK","CHAPTER","VERSE","")
            tab.text = tabNames[position]

            if (position == 3) {
                tab.setIcon(tabicon)
            }
        }.attach()

        val button = binding.blackBackArrow
        button.setOnClickListener {
//            val intent = Intent(this,ReadingActivity::class.java)
//            startActivity(intent)
            finish()
        }

    }

//    override fun sendData(message: String) {
//
//    }



}


