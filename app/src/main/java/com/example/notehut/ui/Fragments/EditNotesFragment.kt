package com.example.notehut.ui.Fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notehut.Model.Notes
import com.example.notehut.R
import com.example.notehut.ViewModel.NotesViewModel
import com.example.notehut.databinding.FragmentEditNotesBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_edit_notes.*
import java.util.*

class EditNotesFragment : Fragment() {

    val notes by navArgs<EditNotesFragmentArgs>()
    lateinit var binding:FragmentEditNotesBinding
    var priority:String = "1"
    val viewModel: NotesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentEditNotesBinding.inflate(layoutInflater,container,false)

        // set menu true
        setHasOptionsMenu(true)

        binding.edtTitle.setText(notes.data.title)
        binding.edtSubtitle.setText(notes.data.subTitle)
        binding.edtNotes.setText(notes.data.notes)

        when(notes.data.priority){
            "1" -> {
                priority = "1"
                binding.pGreen.setImageResource(R.drawable.ic_baseline_done_24)
                binding.pYellow.setImageResource(0)
                binding.pRed.setImageResource(0)
            }
            "2" -> {
                priority = "2"
                binding.pYellow.setImageResource(R.drawable.ic_baseline_done_24)
                binding.pGreen.setImageResource(0)
                binding.pRed.setImageResource(0)
            }
            "3" -> {
                priority = "3"
                binding.pRed.setImageResource(R.drawable.ic_baseline_done_24)
                binding.pYellow.setImageResource(0)
                binding.pGreen.setImageResource(0)
            }
        }


        // if anyone changes priority during edit, for that
        binding.pGreen.setOnClickListener {
            priority = "1"
            binding.pGreen.setImageResource(R.drawable.ic_baseline_done_24)
            binding.pYellow.setImageResource(0)
            binding.pRed.setImageResource(0)
        }

        binding.pYellow.setOnClickListener {
            priority = "2"
            binding.pYellow.setImageResource(R.drawable.ic_baseline_done_24)
            binding.pGreen.setImageResource(0)
            binding.pRed.setImageResource(0)
        }

        binding.pRed.setOnClickListener {
            priority = "3"
            binding.pRed.setImageResource(R.drawable.ic_baseline_done_24)
            binding.pYellow.setImageResource(0)
            binding.pGreen.setImageResource(0)
        }

        // edit save floating button listener
        binding.btnEditSaveNotes.setOnClickListener {
            updateNotes(it)
        }

        return binding.root
    }

    private fun updateNotes(it : View?){
        val id = notes.data.id
        val title = binding.edtTitle.text.toString()
        val subTitle = binding.edtSubtitle.text.toString()
        val notes = binding.edtNotes.text.toString()
        val d = Date()
        val notesDate : CharSequence = DateFormat.format("MMMM d, yyyy",d.time)

        val data = Notes(id,title = title,subTitle = subTitle,notes = notes,date = notesDate.toString(),priority)
        viewModel.updateNotes(data) // this updateNotes is of NotesViewModel

        Toast.makeText(requireContext(),"Notes Updated Successfully", Toast.LENGTH_SHORT).show()

        Navigation.findNavController(it!!).navigate(R.id.action_editNotesFragment_to_homeFragment)
    }

    // inbuilt function for creating delete menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    // inbuilt function to work delete menu when clicked
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // item carry all items in the delete_menu xml file
        // so we have to check only delete id

        if(item.itemId == R.id.menu_delete){
            val bottomSheet:BottomSheetDialog = BottomSheetDialog(requireContext(),R.style.BottomSheetStyle)
            bottomSheet.setContentView(R.layout.dialog_delete)
            bottomSheet.show()  // delete dialog appears

            val textViewYes = bottomSheet.findViewById<TextView>(R.id.dialog_yes) // finding id of yes textview through bottom sheet, not finding direct
            val textViewNo = bottomSheet.findViewById<TextView>(R.id.dialog_no)

            textViewYes?.setOnClickListener {
                viewModel.deleteNotes(notes.data.id!!)
                bottomSheet.dismiss()
                findNavController().popBackStack()
            }

            textViewNo?.setOnClickListener {
                bottomSheet.dismiss() //delete dialog disappears
            }
        }

        // when back button is pressed
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}