package utils

import platform.UIKit.UIImpactFeedbackGenerator

class IosVibrationHandler() : VibrationHandler {
    override fun vibrate() {
        val generator = UIImpactFeedbackGenerator(style = platform.UIKit.UIImpactFeedbackStyle.UIImpactFeedbackStyleHeavy)
        generator.impactOccurred()
    }
}