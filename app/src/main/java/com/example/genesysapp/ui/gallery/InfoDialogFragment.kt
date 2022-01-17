package com.example.genesysapp.ui.gallery

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint

class InfoDialogFragment : DialogFragment() {

    private val args by navArgs<InfoDialogFragmentArgs>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle("Summary")
            .setMessage(args.usersinfo)
            .setNegativeButton("OK", null)
            .create()
}