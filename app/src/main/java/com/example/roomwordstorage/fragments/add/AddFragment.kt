package com.example.roomwordstorage.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomwordstorage.R
import com.example.roomwordstorage.model.User
import com.example.roomwordstorage.viewmodel.UserViewModel


class AddFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        view.findViewById<Button>(R.id.add_button).setOnClickListener{
            insertDataToDatabase(view)
        }

        return view
    }
    private fun insertDataToDatabase(view: View) {
        val firstName = view.findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
        val lastName = view.findViewById<EditText>(R.id.editTextTextPersonName3).text.toString()
        val age = view.findViewById<EditText>(R.id.editTextTextPersonName2).text

        if (inputCheck(firstName, lastName, age)){
            val user = User(0, firstName, lastName ,Integer.parseInt(age.toString()))
            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment2_to_listFragment2)
        }else{
            Toast.makeText(requireContext(), "Please fill all fields!", Toast.LENGTH_LONG).show()
        }
    }
    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean{
        return !(TextUtils.isEmpty(firstName) or TextUtils.isEmpty(lastName) or age.isEmpty() )
    }
}