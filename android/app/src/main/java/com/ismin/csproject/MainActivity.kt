package com.ismin.csproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

const val SERVER_BASE_URL = "https://app-b3e19c6c-55bd-4e27-bd54-47e255a79c08.cleverapps.io/"

class MainActivity : AppCompatActivity(), FountainListFragment.FountainListListener {

    //private lateinit var btnCreateFountain: FloatingActionButton
    //private lateinit var ftnService: FountainService
    //private lateinit var userService: UserService
    private val ftnShelf = FountainShelf()
    private var favShelf = arrayListOf<String>()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(SERVER_BASE_URL)
        .build()
    private val ftnService = retrofit.create(FountainService::class.java)
    private val userService = retrofit.create(UserService::class.java)
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        username = intent.getStringExtra(Intent.EXTRA_TEXT)!!
        Toast.makeText(this, "Welcome ${username}!", Toast.LENGTH_SHORT).show()

        /*btnCreateFountain = findViewById(R.id.a_main_btn_create_fountain)
        btnCreateFountain.setOnClickListener {
            displayCreateFountain()
        }*/

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
        userService.getFavorites(username).enqueue(object : Callback<ArrayList<String>> {
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
                Toast.makeText(applicationContext, "Error when trying to fetch favorites" + t.localizedMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun displayFountainList() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = FountainListFragment.newInstance(ftnShelf.getAllFountains(), favShelf)
        fragmentTransaction.replace(R.id.a_main_lyt_fragment_container, fragment)
        fragmentTransaction.commit()
    }

    /*private fun displayCreateFountain() {
        btnCreateFountain.visibility = View.VISIBLE

    }*/

    fun goToList(view: View) {
        displayFountainList()
    }

    fun goToInfos(view: View) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val infosFragment = InfosFragment.newInstance()

        fragmentTransaction.replace(R.id.a_main_lyt_fragment_container, infosFragment)
        fragmentTransaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_action_refresh -> {
                Toast.makeText(this, "That's refreshing", Toast.LENGTH_SHORT).show()
                loadAllFountains()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun favFromFragment(id: String) {
        if (favShelf.contains(id)) {
            Toast.makeText(this, "Removing ${id}", Toast.LENGTH_SHORT).show()

            userService.deleteFavorite(username, id).enqueue(object: Callback<ArrayList<String>>{
                override fun onResponse(call : Call<ArrayList<String>>, response: Response<ArrayList<String>>) {
                    favShelf.remove(id)
                }

                override fun onFailure(call : Call<ArrayList<String>>, t: Throwable) {
                    Toast.makeText(applicationContext, "Network error ${t.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            })
        } else {
            Toast.makeText(this, "Adding ${id}", Toast.LENGTH_SHORT).show()

            userService.addFavorites(username,id).enqueue(object: Callback<ArrayList<String>>{
                override fun onResponse(call: Call<ArrayList<String>>, response: Response<ArrayList<String>>) {
                    favShelf.add(id)
                    refresh()
                }

                override fun onFailure(call: Call<ArrayList<String>>, t: Throwable) {
                    Toast.makeText(applicationContext, "Network error ${t.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    override fun refresh() {
        loadAllFountains()
        loadFav()
    }
}