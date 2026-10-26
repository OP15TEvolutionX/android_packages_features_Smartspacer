package com.kieronquinn.app.smartspacer.utils.extensions

import com.google.firebase.crashlytics.FirebaseCrashlytics

fun logNonFatal(exception: Exception) {
    try {
        FirebaseCrashlytics.getInstance().recordException(exception)
    }catch (e: IllegalStateException){
        //Local ROM builds may not configure a Firebase project.
    }catch (e: NullPointerException){
        //Crashlytics is disabled
    }
}