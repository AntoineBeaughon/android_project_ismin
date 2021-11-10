package com.ismin.csproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
//import com.squareup.picasso.Picasso

class DetailsActivity: AppCompatActivity() {
    private var fav: Boolean = false
    private lateinit var ftn: Fountain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        ftn = intent.getSerializableExtra("fountain") as Fountain

        fav = intent.getBooleanExtra("fav", false)

        /*var imageView = findViewById<ImageView>(R.id.imageView)
        Picasso.get().load(fountain.image).into(imageView)*/

        var nameTextView = findViewById<TextView>(R.id.id)
        nameTextView.text = "Name: ${ftn.id.toString()}"


        findViewById<TextView>(R.id.tObject).text = "Type de fontaine: ${ftn.tObject.toString()}"
        findViewById<TextView>(R.id.modele).text = "Modèle de la fontaine: ${ftn.modele.toString()}"
        findViewById<TextView>(R.id.numVoie).text = "Numéro: ${ftn.numVoie.toString()}"
        findViewById<TextView>(R.id.voie).text = "Rue: ${ftn.voie.toString()}"
        findViewById<TextView>(R.id.commune).text = "Commune: ${ftn.commune.toString()}"
        findViewById<TextView>(R.id.disponibility).text = "Disponible: ${ftn.disponibility.toString()}"

        if (fav) {
            findViewById<ImageButton>(R.id.imageButton).setImageResource(R.drawable.ic_baseline_star_24)
        }

    }

    fun favButton(view: View) {
        val imgBttn = findViewById<ImageButton>(R.id.imageButton)
        if (fav) {
            imgBttn.setImageResource(R.drawable.ic_baseline_star_border_24)
            fav = !fav
        } else {
            imgBttn.setImageResource(R.drawable.ic_baseline_star_24)
            fav = !fav
        }
    }

    override fun onBackPressed() {
        val returnIntent = Intent()
        returnIntent.putExtra("fav", fav)
        returnIntent.putExtra("fountainname", ftn.id)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
        super.onBackPressed()
    }
}