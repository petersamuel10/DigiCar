package com.peter.digicaradmin.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.peter.digicaradmin.MainViewModel
import com.peter.digicaradmin.R
import com.peter.digicaradmin.ui.intent.MainIntent
import com.peter.digicaradmin.ui.viewState.MainViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_new_user.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class AddNewUserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createAccountBtn.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.mainIntent.send(
                    MainIntent.CreateAccount(
                        nameInEd.text.toString(),
                        passwordInEd.text.toString(),
                        phoneInEd.text.toString()
                    )
                )
            }
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
                        //progressDialog.show()
                    }

                    is MainViewState.CreateAccount -> {
                        // progressDialog.dismiss()
                        Toast.makeText(
                            requireContext(),
                            "Create Account Successful",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is MainViewState.Error -> {
                        // progressDialog.dismiss()
                        Toast.makeText(requireContext(), it.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }


    // region variable
    private val mainViewModel: MainViewModel by viewModels()
    //endregion

}