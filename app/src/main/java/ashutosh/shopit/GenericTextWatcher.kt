package ashutosh.shopit

import android.text.Editable
import android.text.TextWatcher
import android.view.View

class GenericTextWatcher(private val view : View, private val previousView : View?, private val nextView : View?) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
        val text = s.toString()
        if(text.length==1 && nextView!=null){
            nextView.requestFocus()
        }
        else if(text.isEmpty() && previousView!=null){
            previousView.requestFocus()
        }
    }
}