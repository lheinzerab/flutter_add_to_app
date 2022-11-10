package dev.flutter.example.androidView

import android.app.Application
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor.DartEntrypoint


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val flutterEngine = FlutterEngine(this)
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartEntrypoint.createDefault()
        )

        FlutterEngineCache
            .getInstance()
            .put(FLUTTER_ENGINE_ID, flutterEngine)
    }

    companion object {
        const val FLUTTER_ENGINE_ID = "flutter_engine_id"

        fun getFlutterEngine(): FlutterEngine {

            val e: FlutterEngine = requireNotNull(
                FlutterEngineCache
                    .getInstance().get(FLUTTER_ENGINE_ID)
            )

            return e
        }
    }
}