package br.com.danielsan.rogadx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.danielsan.rogadx.databinding.ActivitySearchByZipCodeBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class SearchByZipCodeActivity : AppCompatActivity() {
    companion object {
        const val FALLBACK = "-"
    }

    private val binding by lazy {
        ActivitySearchByZipCodeBinding.inflate(layoutInflater)
    }
    private val httpClient by lazy {
        OkHttpClient.Builder().build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.search.setOnClickListener {
            findAddressByZipCode(binding.zipCodeSearch.text.toString())
        }
    }

    private fun findAddressByZipCode(zipCode: String) {
        val request = Request.Builder()
            .url("https://viacep.com.br/ws/${zipCode}/json/")
            .build()

        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@SearchByZipCodeActivity, "Aconteceu um erro", Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonResponse = JSONObject(response.body?.string() ?: "{}")
                runOnUiThread {
                    binding.zipCode.text = jsonResponse.optString("cep", FALLBACK)
                    binding.state.text = jsonResponse.optString("uf", FALLBACK)
                    binding.city.text = jsonResponse.optString("localidade", FALLBACK)
                    binding.neighborhood.text = jsonResponse.optString("bairro", FALLBACK)
                    binding.street.text = jsonResponse.optString("logradouro", FALLBACK)
                    binding.complement.text = jsonResponse.optString("complemento", FALLBACK)
                }
            }
        })
    }
}
