package com.nexters.fullstack.helper

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_DENIED
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

internal class PermissionHelperImpl(private val context: Context) : PermissionHelper {
    override fun isAcceptPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PERMISSION_DENIED
        ) {
            return false
        }
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PERMISSION_DENIED
        ) {
            return false
        }

        return true
    }

    override val requestCode: Int = 2000

    override val requestPermissions: List<String> = listOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
}