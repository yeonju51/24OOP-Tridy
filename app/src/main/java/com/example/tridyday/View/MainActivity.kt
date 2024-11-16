
package com.example.tridy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.tridy.databinding.ActivityMainBinding
import com.example.tridyday.R
import com.example.tridyday.databinding.ActivityMainBinding
import com.naver.maps.map.NaverMapSdk

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        var binding = ActivityMainBinding.inflate(layoutInflater)
        val navController = binding.frgNav.getFragment<NavHostFragment>().navController
        binding.botNav.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.homeFragment || destination.id == R.id.registTripFragment) {
                binding.botNav.visibility = View.GONE
            } else {
                binding.botNav.visibility = View.VISIBLE
            }
        }

        setContentView(binding.root)

        //네이버  api
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient("1apclb6cvd")


    }

}