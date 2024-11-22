package com.nexters.fullstack.util.extension

import android.content.Intent
import android.net.Uri

fun Intent.share(type: String, title: String, value: Uri): Intent {
    action = Intent.ACTION_SEND
    this.type = type
    putExtra(Intent.EXTRA_STREAM, value)
    return Intent.createChooser(this, title)
}
