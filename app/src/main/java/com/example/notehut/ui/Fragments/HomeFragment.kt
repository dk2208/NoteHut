package com.example.notehut.ui.Fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notehut.Model.Notes
import com.example.notehut.R
import com.example.notehut.ViewModel.NotesViewModel
import com.example.notehut.databinding.FragmentHomeBinding
import com.example.notehut.ui.Adapter.NotesAdapter
import android.widget.SearchView

class HomeFragment : Fragment() {

    lateinit var binding:FragmentHomeBinding
    val viewModel: NotesViewModel by viewModels()
    var oldNotes = arrayListOf<Notes>()
    lateinit var adapter:NotesAdapter  // declaring adapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)

        setHasOptionsMenu(true)

        // To get all notes on home fragment using recycler view and Notes Adapter by default
        viewModel.getNotes().observe(viewLifecycleOwner,{notesList ->
            oldNotes = notesList as ArrayList<Notes>
            binding.rcvAllNotes.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
            adapter = NotesAdapter(requireContext(),notesList)
            binding.rcvAllNotes.adapter = adapter
        })

        // To get all high notes on home fragment using recycler view and Notes Adapter
        binding.filterHigh.setOnClickListener {
            viewModel.getHighNotes().observe(viewLifecycleOwner,{notesList ->
                oldNotes = notesList as ArrayList<Notes>
                binding.rcvAllNotes.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
                adapter = NotesAdapter(requireContext(),notesList)
                binding.rcvAllNotes.adapter = adapter
            })
        }

        // To get all medium notes on home fragment using recycler view and Notes Adapter
        binding.filterMedium.setOnClickListener {
            viewModel.getMediumNotes().observe(viewLifecycleOwner,{notesList ->
                oldNotes = notesList as ArrayList<Notes>
                binding.rcvAllNotes.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
                adapter = NotesAdapter(requireContext(),notesList)
                binding.rcvAllNotes.adapter = adapter
            })
        }

        // To get all low notes on home fragment using recycler view and Notes Adapter
        binding.filterLow.setOnClickListener {
            viewModel.getLowNotes().observe(viewLifecycleOwner,{notesList ->
                oldNotes = notesList as ArrayList<Notes>
                binding.rcvAllNotes.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
                adapter = NotesAdapter(requireContext(),notesList)
                binding.rcvAllNotes.adapter = adapter
            })
        }

        // To get all notes on home fragment using recycler view and Notes Adapter
        binding.allNotes.setOnClickListener {
            viewModel.getNotes().observe(viewLifecycleOwner,{notesList ->
                oldNotes = notesList as ArrayList<Notes>
                binding.rcvAllNotes.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
                adapter = NotesAdapter(requireContext(),notesList)
                binding.rcvAllNotes.adapter = adapter
            })
        }

        binding.btnAddNotes.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_createNotesFragment)
        }
        return binding.root
    }

    // inbuilt function for creating delete menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)

        val item = menu.findItem(R.id.menu_search)
        val searchView = item.actionView as SearchView
        searchView.queryHint = "Enter Title or Subtitle..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean { // means when we to search after entering note in the search bar
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean { // means when we want to search simultaneously
                notesFiltering(newText)
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun notesFiltering(newText: String?){
        val newFilteredList = arrayListOf<Notes>()

        for(i in oldNotes){
            if(i.title.contains(newText!!) || i.subTitle.contains(newText!!))
                newFilteredList.add(i)
        }

        // now we have to give this new list to adapter and for this we make adapter global due to which it gets updated automatically

        adapter.filtering(newFilteredList)

    }
}