package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.util.INotesRVAdapter
import com.example.notesapp.util.Note
import com.example.notesapp.util.NoteViewModel
import com.example.notesapp.util.NotesRVAdapter

class MainActivity : AppCompatActivity(), INotesRVAdapter {

    lateinit var viewModel : NoteViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var insertButton: Button
    lateinit var editText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        insertButton = findViewById(R.id.inputBtn)
        editText = findViewById(R.id.inputEditText)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NotesRVAdapter(this,this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this, Observer {list->
            list?.let {
                adapter.updateList(it)
            }
        })

        insertButton.setOnClickListener {
            insertFunc()
        }

    }

    private fun insertFunc() {
        var noteText = editText.text.toString()
        if (noteText.isNotEmpty()) {
            viewModel.insertNode(Note(noteText))
            Toast.makeText(this,"Inserted $noteText",Toast.LENGTH_SHORT).show()
        }

    }

    override fun onItemClicked(note: Note) {
        viewModel.deleteNode(note)
        Toast.makeText(this,"Inserted ${note.text}",Toast.LENGTH_SHORT).show()
    }
}