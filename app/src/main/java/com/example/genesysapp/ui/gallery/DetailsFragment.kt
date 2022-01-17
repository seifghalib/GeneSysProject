package com.example.genesysapp.ui.gallery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.genesysapp.R
import com.example.genesysapp.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment(R.layout.fragment_details){

    private val args by navArgs<DetailsFragmentArgs>()

    private var _binding : FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDetailsBinding.bind(view)

        binding.apply {
            val image = args.userimage

            Glide.with(this@DetailsFragment)
                .load(image.picture.large)
                .error(R.drawable.ic_error)
                .into(imageView)

            val userFullName = "NAME : ${image.name.title} ${image.name.first} ${image.name.last}"
            textViewName.text = userFullName

            val nat = "NATIONALITY : ${image.nat}"
            textViewNationality.text = nat
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}