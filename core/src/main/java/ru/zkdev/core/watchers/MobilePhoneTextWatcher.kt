package ru.zkdev.core.watchers

import android.text.Editable
import android.text.TextWatcher

class MobilePhoneTextWatcher() : TextWatcher {
    val builder = StringBuilder()
    var ignore = false
    private val numPlace = 'X'

    override fun afterTextChanged(editable: Editable?) {
        if (editable.isNullOrEmpty()) return

        if (!ignore) {
            removeFormat(editable.toString());
            applyFormat(builder.toString());
            ignore = true;
            editable!!.replace(0, editable.length, builder.toString());
            ignore = false;
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    private fun isNumberChar(c: Char): Boolean {
        return c >= '0' && c <= '9'
    }

    private fun applyFormat(value: String) {

        val template = getTemplate(value)
        builder.setLength(0)
        var i = 0
        var textIndex = 0
        while (i < template!!.length && textIndex < value.length) {
            if (template[i] == numPlace) {
                builder.append(value[textIndex])
                textIndex++
            } else {
                builder.append(template[i])
            }
            i++
        }
    }

    private fun removeFormat(text: String) {
        builder.setLength(0)
        for (element in text) {
            if (isNumberChar(element)) {
                builder.append(element)
            }
        }
    }

    private fun getTemplate(text: String): String? {
        return if (text.startsWith("7")) {
            "+X (XXX) XXX-XX-XX"
        } else {
            "+XXX (XXX) XX-XX-XX"
        }
    }
}