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


class MainActivity : FragmentActivity(), FlutterBridge {

    private var flutterFragment: FlutterFragment? = null
    private var flutterChannel: MethodChannel? = null

    private fun sendMessageToFlutter(arg: String) {
        flutterChannel?.invokeMethod("flutterIChooseYou", arg)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager

        flutterFragment = fragmentManager
            .findFragmentByTag(TAG_FLUTTER_FRAGMENT) as FlutterFragment?

        if (flutterFragment == null) {
            flutterFragment = FlutterFragment.createDefault()
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

    override fun setMethodChannel(methodChannel: MethodChannel?) {
        methodChannel?.setMethodCallHandler(null)

        flutterChannel = methodChannel
        methodChannel?.setMethodCallHandler { call, result ->

            Log.i(
                "MethodCallHandler",
                "Flutter calling with method:${call.method} and argument:${call.arguments}"
            )
            sendMessageToFlutter("I heard your message flutter.")
            result.success(true)
        }

        sendMessageToFlutter("testFrom")
    }
}