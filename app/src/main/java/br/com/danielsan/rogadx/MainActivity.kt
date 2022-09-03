package br.com.danielsan.rogadx

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.danielsan.rogadx.databinding.ActivityMainBinding
import br.com.danielsan.rogadx.searchbystreet.SearchByStreetActivity

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.searchByZipCode.setOnClickListener {
            startActivity(Intent(this, SearchByZipCodeActivity::class.java))
        }

        binding.searchByStreet.setOnClickListener {
            startActivity(Intent(this, SearchByStreetActivity::class.java))
        }
    }
}
