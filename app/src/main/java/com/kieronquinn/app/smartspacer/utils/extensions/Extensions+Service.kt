package com.kieronquinn.app.smartspacer.utils.extensions

import android.app.Notification
import android.app.Service
import android.content.pm.ServiceInfo
import android.content.Context
import android.content.pm.ApplicationInfo
import android.app.NotificationManager
import android.os.Build
import com.kieronquinn.app.smartspacer.components.notifications.NotificationId

fun Context.isRomBackgroundManaged(): Boolean {
    return applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
}

fun Service.startForeground(notificationId: NotificationId, notification: () -> Notification) {
    // ROM system builds are allowlisted from power save and bound by SmartspaceManagerService; they do not need FGS.
    if (isRomBackgroundManaged()) {
        getSystemService(NotificationManager::class.java).cancel(notificationId.ordinal)
        return
    }
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            startForeground(
                notificationId.ordinal,
                notification(),
                ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
            )
        }else{
            startForeground(notificationId.ordinal, notification())
        }
    }catch (e: Exception) {
        //Caches ForegroundServiceStartNotAllowedException on S+ when unable to startForeground
    }
}