package com.example.cri

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import java.io.File
private val FILE_ARG = "image File"
class image_dialoge : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val view = activity?.layoutInflater?.inflate(R.layout.image_dialoge , null)
        val imgeView = view?.findViewById(R.id.image_dialog) as ImageView
        val imgeFile = arguments?.getSerializable(FILE_ARG) as File
        if (imgeFile.exists()) {
            var pictureUtils = PictureUtils()
            val bitmap = pictureUtils.getScaledBitmap(
                imgeFile.path ,
                requireActivity()
            )
            imgeView.setImageBitmap(bitmap)
        } else
            imgeView.setImageDrawable(null)



        return AlertDialog.Builder(requireContext() , R.style.ThemeOverlay_AppCompat_Dialog_Alert)
            .setView(view)
            .setTitle("Image")
            .setNegativeButton("back") { dialog , _ ->
                dialog.cancel()

            }.create()
    }

    companion object {
        fun newInstance(photoFile: File): image_dialoge {
            val args = Bundle().apply {
                putSerializable(FILE_ARG , photoFile)
            }
            return image_dialoge().apply {
                arguments = args
            }

        }
    }
}
