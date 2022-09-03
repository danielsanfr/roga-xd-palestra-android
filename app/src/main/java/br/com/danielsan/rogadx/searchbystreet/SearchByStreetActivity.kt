package br.com.danielsan.rogadx.searchbystreet

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import br.com.danielsan.rogadx.R
import br.com.danielsan.rogadx.databinding.ActivitySearchByStreetBinding

class SearchByStreetActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivitySearchByStreetBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ufSearch.adapter =
            ArrayAdapter.createFromResource(this, R.array.ufs, android.R.layout.simple_spinner_item)
                .apply {
                    setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                }

        binding.search.setOnClickListener {
            val intent = SearchByStreetResultActivity.createIntent(
                this,
                binding.ufSearch.selectedItem.toString(),
                binding.citySearch.text.toString(),
                binding.streetSearch.text.toString()
            )

            binding.citySearch.requestFocus()
            binding.citySearch.setText("")
            binding.streetSearch.setText("")

            startActivity(intent)
        }
    }
}
