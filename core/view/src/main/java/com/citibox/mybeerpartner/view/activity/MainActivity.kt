package com.citibox.mybeerpartner.view.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import com.citibox.mybeerpartner.view.R
import com.citibox.mybeerpartner.view.databinding.ActivityMainBinding
import com.citibox.mybeerpartner.view.fragment.CharactersListFragment
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val permissionsRequestId = 1000

    private lateinit var binding: ActivityMainBinding
    private var charactersListFragment: CharactersListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.filterButton.setOnClickListener { charactersListFragment?.showFilterDialog() }

        if (setupPermissions())
            initApp()
        else
            binding.filterButton.isVisible = false
    }

    /**
     * Receptor de los resultados de la petición de permisos
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        if (requestCode == permissionsRequestId)
        {
            if (grantResults.isNotEmpty())
            {
                for (a in grantResults)
                    if (a != PackageManager.PERMISSION_GRANTED)
                        return

                initApp()
            }
            else
            {
                // Alert dialog
                this@MainActivity.finish()
            }
        }
        else
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setupPermissions() : Boolean
    {
        val permissions : ArrayList<String> = arrayListOf()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
            permissions.add(Manifest.permission.INTERNET)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)

        if (permissions.isNotEmpty())
        {
            ActivityCompat.requestPermissions(this, permissions.toArray(arrayOfNulls<String>(permissions.size)), permissionsRequestId)
            return false
        }
        return true
    }

    private fun initApp() {
        charactersListFragment = CharactersListFragment().apply {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, this, "CharactersListFragment")
                .commitNow()
            binding.filterButton.isVisible = true
        }
    }
}