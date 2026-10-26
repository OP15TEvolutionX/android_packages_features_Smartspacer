package com.kieronquinn.app.smartspacer.repositories

import android.content.Context
import android.app.IActivityTaskManager
import android.os.ServiceManager
import android.util.Log
import com.kieronquinn.app.smartspacer.utils.extensions.getForegroundPackage
import com.kieronquinn.app.smartspacer.utils.extensions.isRomBackgroundManaged
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface AccessibilityRepository {

    val foregroundPackage: StateFlow<String>

    suspend fun setForegroundPackage(foregroundPackage: String)

}

class AccessibilityRepositoryImpl(private val context: Context): AccessibilityRepository {

    override val foregroundPackage = MutableStateFlow("")

    init {
        if (context.isRomBackgroundManaged()) {
            MainScope().launch {
                val manager = IActivityTaskManager.Stub.asInterface(
                    ServiceManager.getService("activity_task")
                )
                manager.getForegroundPackage().catch {
                    Log.e("SmartspacerTasks", "Unable to monitor foreground task", it)
                }.collect { foregroundPackage.emit(it) }
            }
        }
    }

    override suspend fun setForegroundPackage(foregroundPackage: String) {
        this.foregroundPackage.emit(foregroundPackage)
    }

}