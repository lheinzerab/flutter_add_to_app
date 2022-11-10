import 'package:flutter/services.dart';

class FlutterMethodChannel {
  static const channelName = "flutter.bridge.method.channel";
  late MethodChannel methodChannel;

  static final FlutterMethodChannel instance = FlutterMethodChannel._init();

  FlutterMethodChannel._init();

  void configureChannel() {
    methodChannel = MethodChannel(channelName);
    methodChannel
        .setMethodCallHandler(this.methodHandler); // set method handler
  }

  void sendMessage(String method, String argument) {
    methodChannel.invokeMethod(method, argument);
  }

  Future<void> methodHandler(MethodCall call) async {
    print(
        'Android calling with method:${call.method} and argument ${call.arguments}');
  }
}
