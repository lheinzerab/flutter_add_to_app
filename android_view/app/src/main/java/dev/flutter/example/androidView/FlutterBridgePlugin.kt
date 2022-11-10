package dev.flutter.example.androidView

import android.util.Log
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler

/**
 * Plugin implementation that uses the new `io.flutter.embedding` package.
 *
 *
 * Instantiate this in an add to app scenario to gracefully handle activity and context changes.
 */
class FlutterBridgePlugin : FlutterPlugin, ActivityAware {

    private var methodChannel: MethodChannel? = null
    private val incomingCallHandler: MethodCallHandler = MethodCallHandler { call, result ->
        Log.i(TAG, "call from flutter:${call.method}, ${call.arguments}")
        result.success(true)
    }

    override fun onAttachedToEngine(binding: FlutterPluginBinding) {
        methodChannel =
            MethodChannel(
                MyApplication.getFlutterEngine().dartExecutor.binaryMessenger,
                "flutter.bridge.method.channel"
            )
                .also {
                    it.setMethodCallHandler(incomingCallHandler)
                }
    }

    override fun onDetachedFromEngine(binding: FlutterPluginBinding) {
        if (methodChannel == null) {
            Log.wtf(TAG, "Already detached from the engine.")
            return
        }
        methodChannel?.setMethodCallHandler(null)
        methodChannel = null
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        if (methodChannel == null) {
            Log.wtf(TAG, "No Channel????!")
            return
        }
        if (binding.activity is FlutterBridge) {
            (binding.activity as FlutterBridge).setMethodChannel(methodChannel!!)
        }
    }

    override fun onDetachedFromActivity() {
    }

    override fun onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity()
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        onAttachedToActivity(binding)
    }

    companion object {
        private const val TAG = "FlutterBridgePlugin"

        /**
         * Registers a plugin implementation that uses the stable `io.flutter.plugin.common`
         * package.
         *
         * Calling this automatically initializes the plugin. However plugins initialized this way
         * won't react to changes in activity or context, unlike [UrlLauncherPlugin].
         */
        /*fun registerWith(registrar: Registrar) {
            val handler =
                MethodCallHandlerImpl(UrlLauncher(registrar.context(), registrar.activity()))
            handler.startListening(registrar.messenger())
        }*/
    }
}