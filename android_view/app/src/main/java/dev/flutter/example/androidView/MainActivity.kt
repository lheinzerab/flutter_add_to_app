// Copyright 2019 The Flutter team. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package dev.flutter.example.androidView

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import io.flutter.embedding.android.FlutterFragment
import io.flutter.plugin.common.MethodChannel


class MainActivity : FragmentActivity() {

    private var flutterFragment: FlutterFragment? = null

    lateinit var flutterChannel: MethodChannel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        flutterChannel = MethodChannel(
            MyApplication.getFlutterEngine().dartExecutor.binaryMessenger,
            "flutter.bridge.method.channel"
        )

        flutterChannel.setMethodCallHandler { call, result ->
            Log.d("MethodHandler", "Flutter called method: " + call.method)
            flutterChannel.invokeMethod("methodCalledFromAndroid", "argument")
        }
        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager

        flutterFragment = fragmentManager
            .findFragmentByTag(TAG_FLUTTER_FRAGMENT) as FlutterFragment?

        if (flutterFragment == null) {
            flutterFragment = FlutterFragment.withCachedEngine("flutter_engine_id").build()
            fragmentManager
                .beginTransaction()
                .add(
                    R.id.fragment_container,
                    flutterFragment!!,
                    TAG_FLUTTER_FRAGMENT
                )
                .commit()
        }


    }

    companion object {
        private const val TAG_FLUTTER_FRAGMENT = "flutter_fragment"
    }

}