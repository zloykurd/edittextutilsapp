package ru.zkdev.edittextapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.zkdev.core.watchers.MobilePhoneTextWatcher
import ru.zkdev.core.watchers.PriceTextWatcher
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etPhone.addTextChangedListener(MobilePhoneTextWatcher())
        etPrice.addTextChangedListener(PriceTextWatcher(etPrice, Locale("ky", "KG")))

    }
}