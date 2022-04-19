// ignore_for_file: file_names

import 'package:flutter/material.dart';

import '../bottomNavi.dart';

class HomePage extends StatefulWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        decoration: const BoxDecoration(
            image: DecorationImage(
          repeat: ImageRepeat.repeat,
          image: AssetImage('assets/background.jpg'),
        )),
        child: const Center(child: Text("Startseite")),
      ),
    );
  }
}
