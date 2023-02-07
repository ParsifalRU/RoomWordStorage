package com.example.roomwordstorage.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomwordstorage.R
import com.example.roomwordstorage.model.User
import com.example.roomwordstorage.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        view.updateFirstName.setText(args.currentUser.firstName)
        view.updateLastName.setText(args.currentUser.lastName)
        view.updateAge.setText(args.currentUser.age.toString())

        view.updateAddButton.setOnClickListener {
            updateItem()
        }

        createMenu()

        return view
    }

    private fun updateItem(){
        val firstName = updateFirstName.text.toString()
        val lastName = updateLastName.text.toString()
        val age = Integer.parseInt(updateAge.text.toString())

        if(inputCheck(firstName , lastName, updateAge.text)){
            val updateUser = User(args.currentUser.id, firstName, lastName, age)
            mUserViewModel.updateUser(updateUser)
            findNavController().navigate(R.id.action_updateFragment_to_listFragment2)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(requireContext(), "Please fill out fields.", Toast.LENGTH_SHORT).show()
        }
    }


    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean{
        return !(TextUtils.isEmpty(firstName) or TextUtils.isEmpty(lastName) or age.isEmpty() )
    }

    private fun createMenu(){
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.delete_menu, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_delete -> {
                        deleteUser()
                        true
                    }

                    else -> {false}
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    fun deleteUser(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mUserViewModel.deleteUser(args.currentUser)
            Toast.makeText(
                requireContext(),
                "Successfully removed: ${args.currentUser.firstName}",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment2)
        }
        builder.setNegativeButton("No") { _, _ ->
        }
        builder.setTitle("Delete ${args.currentUser.firstName}?")
        builder.setMessage("Are you sure you want to delete ${args.currentUser.firstName}?")
        builder.create().show()
    }
}