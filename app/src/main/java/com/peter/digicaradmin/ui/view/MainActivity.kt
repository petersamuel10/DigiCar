package com.peter.digicaradmin.ui.view

import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.peter.digicaradmin.MainViewModel
import com.peter.digicaradmin.R
import com.peter.digicaradmin.ui.intent.MainIntent
import com.peter.digicaradmin.ui.viewState.MainViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_new_user.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        setSupportActionBar(toolBar)
        setupSmoothBottomMenu()

        lifecycleScope.launch {
            mainViewModel.mainIntent.send(
                MainIntent.Temp
            )
        }
        observeViewModel()
    }

    private fun observeViewModel() {

        lifecycleScope.launch {
            mainViewModel.state.collect {
                when (it) {
                    is MainViewState.Idle -> {
                    }
                    is MainViewState.Loading -> {
                    }
                    is MainViewState.Temp -> {
                        name.text = it.temp.name
                        temp.text = it.temp.main.temp.toString() + "C"
                    }
                    is MainViewState.Error -> {
                        Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }


    private fun setupSmoothBottomMenu() {
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.navigation_menu)
        val menu = popupMenu.menu
        bottomBar.setupWithNavController(menu, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    // region variable
    private val mainViewModel: MainViewModel by viewModels()
    //endregion
}