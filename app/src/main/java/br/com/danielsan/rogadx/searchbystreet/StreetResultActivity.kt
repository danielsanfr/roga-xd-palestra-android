package br.com.danielsan.rogadx.searchbystreet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.danielsan.rogadx.databinding.ActivityStreetResultBinding

class StreetResultActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_UF = "uf"
        private const val EXTRA_CITY = "city"
        private const val EXTRA_STREET = "street"
        private const val EXTRA_ZIP_CODE = "zip_code"
        private const val EXTRA_COMPLEMENT = "complement"
        private const val EXTRA_NEIGHBORHOOD = "neighborhood"

        fun createIntent(
            context: Context,
            zipCode: String,
            uf: String,
            city: String,
            neighborhood: String,
            street: String,
            complement: String
        ): Intent {
            return Intent(context, StreetResultActivity::class.java)
                .putExtra(EXTRA_ZIP_CODE, zipCode)
                .putExtra(EXTRA_UF, uf)
                .putExtra(EXTRA_CITY, city)
                .putExtra(EXTRA_NEIGHBORHOOD, neighborhood)
                .putExtra(EXTRA_STREET, street)
                .putExtra(EXTRA_COMPLEMENT, complement)
        }
    }

    private val binding by lazy {
        ActivityStreetResultBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.zipCode.text = intent.getStringExtra(EXTRA_ZIP_CODE)
        binding.state.text = intent.getStringExtra(EXTRA_UF)
        binding.city.text = intent.getStringExtra(EXTRA_CITY)
        binding.neighborhood.text = intent.getStringExtra(EXTRA_NEIGHBORHOOD)
        binding.street.text = intent.getStringExtra(EXTRA_STREET)
        binding.complement.text = intent.getStringExtra(EXTRA_COMPLEMENT)
    }
}
