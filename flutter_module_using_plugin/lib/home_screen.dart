import 'package:flutter/material.dart';
import 'package:flutter_module_using_plugin/flutter_method_channel.dart';

class MyHomePage extends StatelessWidget {
  final String title;

  MyHomePage({required this.title}) : super();

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        title: new Text(title),
      ),
      body: new Center(
        child: new Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            new TextButton(
              child: new Text('Call to Native World!'),
              onPressed: _callNativeWorld,
            ),
          ],
        ),
      ),
    );
  }

  Future<Null> _callNativeWorld() async {
    FlutterMethodChannel.instance
        .sendMessage("callFromFlutter", "argumentFromFlutter");
  }
}
