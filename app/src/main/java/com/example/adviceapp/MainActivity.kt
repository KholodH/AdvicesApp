package com.example.adviceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var btnGet:Button
    private lateinit var tvAdvice:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnGet=findViewById(R.id.bGet)
        tvAdvice=findViewById(R.id.tvAdvice)
        btnGet.setOnClickListener {  requestAPI()}


    }
    fun requestAPI(){
        // we use Coroutines to fetch the data, then update the Recycler View if the data is valid
        CoroutineScope(IO).launch {
            // we fetch the prices
            val data = async { fetchPrices() }.await()
            // once the data comes back, we populate our Recycler View
            if(data.isNotEmpty()){
                populateRV(data)
            }else{
                Log.d("MAIN", "Unable to get data")
            }
        }
    }


    fun fetchPrices(): String{

        var response = ""
        try{
            response = URL("https://api.adviceslip.com/advice").readText()
        }catch(e: Exception){
            Log.d("MAIN", "ISSUE: $e")
        }
        // our response is saved as a string
        return response
    }

    suspend fun populateRV(result: String){
        withContext(Main){
            // we create a JSON object from the data
            val jsonObj = JSONObject(result)

            // to go deeper, we can use the getString method (here we get the value of USD)
            val Advice = jsonObj.getJSONObject("slip").getString("advice")
            println(Advice)
            tvAdvice.setText(Advice)

        }


    }}


