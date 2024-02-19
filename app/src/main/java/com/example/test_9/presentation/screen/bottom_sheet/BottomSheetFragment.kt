package com.example.test_9.presentation.screen.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.test_9.databinding.FragmentBottomSheetBinding
import com.example.test_9.presentation.interfaces.OnChoosePictureButton
import com.example.test_9.presentation.interfaces.OnTakePictureButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetFragment : BottomSheetDialogFragment(){

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)

        binding.btnTakePicture.setOnClickListener {
            (parentFragment as OnTakePictureButton).onTakePictureOptionSelected()
            dismiss()
        }

        binding.btnChoosePicture.setOnClickListener {
            (parentFragment as OnChoosePictureButton).onChoosePictureOptionSelected()
            dismiss()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}