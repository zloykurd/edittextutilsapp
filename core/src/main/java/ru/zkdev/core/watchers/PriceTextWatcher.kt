package ru.zkdev.core.watchers

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.lang.ref.WeakReference
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*


class PriceTextWatcher(view: EditText, private val locale: Locale) : TextWatcher {
    private val editTextWeakReference: WeakReference<EditText> = WeakReference<EditText>(view)

    private val builder = StringBuilder()
    var ignore = false

    override fun afterTextChanged(editable: Editable?) {
        val editText: EditText = editTextWeakReference.get() ?: return
        if (editable.isNullOrEmpty()) return
        if (!ignore) {
            blockOrOpenOperation()

            val cleanString = removeFormat(editable.toString());
            val parsed = parseString(cleanString)
            val formatted = applyFormat(parsed)
            editText.setText(formatted)
            setSelection(editText, parsed)

            blockOrOpenOperation()
        }
    }

    private fun blockOrOpenOperation() {
        ignore = !ignore
    }

    private fun applyFormat(value: BigDecimal): String {
        return NumberFormat.getCurrencyInstance(locale).format(value)
    }

    private fun setSelection(editText: EditText, parsed: BigDecimal) {
        editText.setSelection(
            when {
                parsed.toString().length >= 31 -> {
                    parsed.toString().length + 9
                }
                parsed.toString().length >= 28 -> {
                    parsed.toString().length + 8
                }
                parsed.toString().length >= 25 -> {
                    parsed.toString().length + 7
                }
                parsed.toString().length >= 22 -> {
                    parsed.toString().length + 6
                }
                parsed.toString().length >= 19 -> {
                    parsed.toString().length + 5
                }
                parsed.toString().length >= 16 -> {
                    parsed.toString().length + 4
                }
                parsed.toString().length >= 13 -> {
                    parsed.toString().length + 3
                }
                parsed.toString().length >= 10 -> {
                    parsed.toString().length + 2
                }
                parsed.toString().length >= 7 -> {
                    parsed.toString().length + 1
                }
                else -> {
                    parsed.toString().length
                }
            }
        )
    }

    private fun parseString(cleanString: String): BigDecimal {
        return BigDecimal(cleanString)
            .setScale(2, BigDecimal.ROUND_FLOOR)
            .divide(BigDecimal(100), BigDecimal.ROUND_FLOOR)
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(s: CharSequence, p1: Int, p2: Int, p3: Int) {
    }

    private fun removeFormat(text: String): String {
        builder.setLength(0)
        for (element in text) {
            if (isNumberChar(element)) {
                builder.append(element)
            }
        }

        return builder.toString().replace("[$,.]".toRegex(), "")
    }

    private fun isNumberChar(c: Char): Boolean {
        return c in '0'..'9'
    }
}

