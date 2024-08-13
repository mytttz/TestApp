package com.example.testapp.mainscreen

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.google.android.material.button.MaterialButton

class MainScreenActivity : AppCompatActivity() {

    private lateinit var cellList: RecyclerView
    private lateinit var createButton: MaterialButton
    private lateinit var viewModel: CellViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        cellList = findViewById(R.id.cell_list)
        createButton = findViewById(R.id.create_button)
        viewModel = CellViewModel(this)
        val adapter = CellAdapter()
        cellList.adapter = adapter
        cellList.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }
        viewModel.cellList.observe(this) { list ->
            Log.i("size1", list?.size.toString())
            adapter.submitList(list) {
                cellList.scrollToPosition(adapter.itemCount - 1)
            }
        }
        createButton.setOnClickListener {
            viewModel.createCell(this)
        }
    }
}