package com.citibox.mybeerpartner.view.permission

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

private const val PERMISSIONS_REQUEST_CODE: Int = 0

object PermissionManager
{
    fun checkPermissions(activity: AppCompatActivity,
                         permissions: Array<out String>): Boolean
    {
        val needed = mutableListOf<String>()
        permissions.forEach { permission ->
            when
            {
                (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) -> {}
                activity.shouldShowRequestPermissionRationale(permission) ->
                {
                    /* TODO Mostrar diálogo explicando los permisos que faltan. Debería añadirse otro
                        array para aquellos que tienen que explicarse */
                    needed.add(permission)
                }
                else -> needed.add(permission)
            }
        }
        if (needed.isNotEmpty())
        {
            activity.requestPermissions(needed.toTypedArray(), PERMISSIONS_REQUEST_CODE)
            return false
        }
        return true
    }

    fun onPermissionsResult(requestCode: Int,
                            permissions: Array<out String>,
                            grantResults: IntArray,
                            granted: (() -> Unit)? = null,
                            notGranted: ((String) -> Unit)? = null)
    {
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE ->
            {
                for (i in grantResults.indices) {
                    val result = grantResults[i]
                    if ((grantResults.isNotEmpty() && result != PackageManager.PERMISSION_GRANTED)) {
                        notGranted?.invoke(permissions[i])
                        return
                    }
                }
                granted?.invoke()
            }
        }
    }
}