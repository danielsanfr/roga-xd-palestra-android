package br.com.danielsan.rogadx.searchbystreet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.danielsan.rogadx.databinding.ActivitySearchByStreetResultBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class SearchByStreetResultActivity : AppCompatActivity() {
    companion object {
        private const val FALLBACK = "-"
        private const val EXTRA_UF = "uf"
        private const val EXTRA_CITY = "city"
        private const val EXTRA_STREET = "street"

        fun createIntent(
            context: Context,
            uf: String,
            city: String,
            street: String
        ): Intent {
            return Intent(context, SearchByStreetResultActivity::class.java)
                .putExtra(EXTRA_UF, uf)
                .putExtra(EXTRA_CITY, city)
                .putExtra(EXTRA_STREET, street)
        }
    }

    private val binding by lazy {
        ActivitySearchByStreetResultBinding.inflate(layoutInflater)
    }
    private val httpClient by lazy {
        OkHttpClient.Builder().build()
    }
    private val adapter by lazy { SearchByStreetResultAdapter(this::onClickAddress) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.address.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.HORIZONTAL
            )
        )
        binding.address.adapter = adapter

        findAddressByStreet(
            intent.getStringExtra(EXTRA_UF) ?: "",
            intent.getStringExtra(EXTRA_CITY) ?: "",
            intent.getStringExtra(EXTRA_STREET) ?: ""
        )
    }

    private fun findAddressByStreet(
        uf: String,
        city: String,
        street: String
    ) {
        val request = Request.Builder()
            .url("https://viacep.com.br/ws/${uf}/${city}/${street}/json/")
            .build()

        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(
                        this@SearchByStreetResultActivity,
                        "Aconteceu um erro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonArrayResponse = JSONArray(response.body?.string() ?: "[]")
                val list = mutableListOf<JSONObject>()
                for (i in 0 until jsonArrayResponse.length()) {
                    list.add(jsonArrayResponse.optJSONObject(i) ?: JSONObject())
                }
                runOnUiThread { adapter.submitList(list) }
            }
        })
    }

    private fun onClickAddress(jsonResponse: JSONObject) {
        val intent = StreetResultActivity.createIntent(
            this,
            jsonResponse.optString("cep", FALLBACK),
            jsonResponse.optString("uf", FALLBACK),
            jsonResponse.optString("localidade", FALLBACK),
            jsonResponse.optString("bairro", FALLBACK),
            jsonResponse.optString("logradouro", FALLBACK),
            jsonResponse.optString("complemento", FALLBACK)
        )
        startActivity(intent)
    }
}
