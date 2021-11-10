package com.ismin.csproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.view.Menu
import android.view.View
import android.widget.Toast
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

const val SERVER_BASE_URL = "https://bookshelf-gme.cleverapps.io/"

class MainActivity : AppCompatActivity() {

    private lateinit var btnCreateFountain: FloatingActionButton
    private val ftnShelf = FountainShelf()
    private var favShelf = arrayListOf<String>()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(SERVER_BASE_URL)
        .build()
    private val ftnService = retrofit.create(FountainService::class.java)
    private val userService = retrofit.create(UserService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCreateFountain = findViewById(R.id.a_main_btn_create_fountain)
        btnCreateFountain.setOnClickListener {
            displayCreateFountain()
        }

        loadAllFountains()
        loadFav()
    }

    private fun loadAllFountains() {
        ftnService.getAllFountains().enqueue(object : Callback<List<Fountain>> {
            override fun onResponse(
                call: Call<List<Fountain>>,
                response: Response<List<Fountain>>
            ) {
                val allFountains: List<Fountain>? = response.body()

                allFountains?.forEach {
                    ftnShelf.addFountain(it)
                }
                displayFountainList();
            }

            override fun onFailure(call: Call<List<Fountain>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error when trying to fetch fountains" + t.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
        )
    }

    private fun loadFav() {
        userService.getUserFavorites(username).enqueue(object : Callback<ArrayList<String>> {
            override fun onResponse(
                call: Call<ArrayList<String>>,
                response: Response<ArrayList<String>>
            ) {
                val allFavorites = response.body()
                allFavorites?.forEach {
                    favShelf.add(it)
                }
            }

            override fun onFailure(call: Call<ArrayList<String>>, t: Throwable) {
                Toast.makeText(application Context, "Error when trying to fetch favorites" + t.localizedMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun displayFountainList() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = FountainListFragment.newInstance(ftnShelf.getAllFountains(), favShelf)
        fragmentTransaction.replace(R.id.a_main_lyt_fragment_container, fragment)
        fragmentTransaction.commit()
    }

    private fun displayCreateFountain() {
        btnCreateFountain.visibility = View.VISIBLE

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun refresh() {
        loadAllFountains()
        loadFav()
    }
}