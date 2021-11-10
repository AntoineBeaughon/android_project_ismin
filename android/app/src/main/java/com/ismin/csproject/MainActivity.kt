package com.ismin.csproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private lateinit var btnCreateFountain: FloatingActionButton
    //val ftnService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCreateFountain = findViewById(R.id.a_main_btn_create_fountain)
        btnCreateFountain.setOnClickListener {
            displayCreateFountain()
        }

        loadAllFountains()
    }

    private fun loadAllFountains() {
        ftnService.getAllFountains().enqueue(object : Callback<List<Fountain>> {
            override fun onResponse(
                call: Call<List<Fountain>>,
                response: Response<List<Fountain>>
            ) {
                val allFountains: List<Fountain>? = response.body()

                allFountains?.forEach {
                    bookshelf.addBook(it)
                }
                displayBookList();
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error when trying to fetch books" + t.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
}