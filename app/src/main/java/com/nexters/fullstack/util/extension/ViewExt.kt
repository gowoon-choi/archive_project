package com.nexters.fullstack.util.extension

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent

fun FragmentManager.replace(@IdRes fragmentId: Int, fragment: Fragment) {
    beginTransaction().replace(fragmentId, fragment).commitAllowingStateLoss()
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun FragmentManager.removeFragment(fragment: Fragment) {
    beginTransaction().remove(fragment)
}

fun FirebaseAnalytics.setSettingEvents(view: View, bundle: Bundle) {
    this.logEvent(view.transitionName) {
        param(FirebaseAnalytics.Param.ITEM_ID, bundle)
    }
}

fun Int.dpToPx(context: Context): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics)
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
