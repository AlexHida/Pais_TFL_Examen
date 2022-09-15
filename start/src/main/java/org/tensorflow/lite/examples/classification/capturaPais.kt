package org.tensorflow.lite.examples.classification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide

class capturaPais : AppCompatActivity() {

    private val masInfoCode by lazy {findViewById<TextView>(R.id.codeCoun)}
    private val pais by lazy {findViewById<TextView>(R.id.paisName)}
    private val capital by lazy {findViewById<TextView>(R.id.capitalCoun)}
    private val imagen by lazy {findViewById<ImageView>(R.id.imageCountry)}
    private val tel by lazy {findViewById<TextView>(R.id.codNum)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capturapais)
        val aux = intent.getStringExtra("codigo")
        masInfoCode.text = aux
        datosAp(aux.toString())
    }

    fun datosAp(id:String) {
        val vollCola = Volley.newRequestQueue(this)
        val url = "https://restcountries.com/v2/alpha/$id"
        val mostrarTexto = findViewById<TextView>(R.id.codeCoun)

        val jsonRQ = JsonObjectRequest(
            Request.Method.GET,url,null,
            {response -> Log.d("API", response.toString())
                pais.text = response.getString("name")
                capital.text = response.getString("capital")
                var numeros = response.getJSONArray("callingCodes")
                tel.text = numeros.getInt(0).toString()
                val urlimg = response.getString("flag")
                var urlImgPng = response.getJSONObject("flags").getString("png")
                Log.d("IMG", urlimg)
                Log.d("IMG", urlImgPng)
                Glide.with(this).load(urlImgPng).centerCrop().into(imagen)
            },
            { error -> mostrarTexto.text ="Error"
                error.printStackTrace()
            }
        )
        vollCola.add(jsonRQ)
    }
}