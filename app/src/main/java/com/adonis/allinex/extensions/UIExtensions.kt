package com.adonis.allinex.extensions

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


fun <T : ViewBinding> Fragment.viewBinding(inflate: (LayoutInflater, ViewGroup?, Boolean) -> T): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        inflate.invoke(layoutInflater, null, false)
    }
}

fun Context.showShortToast(message : String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(message : String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
