package dev.flutter.example.androidView

import io.flutter.plugin.common.MethodChannel

interface FlutterBridge {
    fun setMethodChannel(methodChannel: MethodChannel?)
}