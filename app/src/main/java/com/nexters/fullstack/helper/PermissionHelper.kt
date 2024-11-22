package com.nexters.fullstack.helper

interface PermissionHelper {
    fun isAcceptPermission(): Boolean

    val requestCode: Int

    val requestPermissions: List<String>
}