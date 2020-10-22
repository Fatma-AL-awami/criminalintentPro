package com.example.cri

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*

     class MainActivity : AppCompatActivity() ,CrimeListFragment.Callbacks {

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_main)
                val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
                if (currentFragment == null) {
                    val fragment = CrimeFragment()

                    supportFragmentManager.beginTransaction()
                        .add(R.id.fragment_container, CrimeListFragment.newInstance())
                        .commit()
                }
            }

         override fun onCrimeSelected(crimeId: UUID) {

            // Log.d("click", "MainActivity.onCrimeSelected: $crimeId")
             val fragment = CrimeFragment.newInstance(crimeId)
             supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
                 .addToBackStack(null).commit()
         }

        }

