package utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator


class AndroidVibrationHandler(private val context: Context) : VibrationHandler {

    override fun vibrate() {
        val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v?.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            v?.vibrate(500)
        }
    }

}