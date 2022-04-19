// ignore_for_file: file_names

import 'package:flutter/material.dart';

import 'screens/chatPage.dart';
import 'screens/homePage.dart';
import 'screens/profilPage.dart';

class BottomNavi extends StatefulWidget {
  const BottomNavi({Key? key}) : super(key: key);

  @override
  _BottomNaviState createState() => _BottomNaviState();
}

class _BottomNaviState extends State<BottomNavi> {
  int currentIndex = 0;
  final screens = [
    const HomePage(),
    const ChatPage(),
    const ProfilPage(),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
/*       body: Container(
        decoration: const BoxDecoration(
            image: DecorationImage(
          repeat: ImageRepeat.repeat,
          image: AssetImage('assets/background.jpg'),
        )),
        child: const Center(child: Text("Startseite")),
      ), */
      body: screens[currentIndex],
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: currentIndex,
        onTap: (index) => setState(() => currentIndex = index),
        selectedItemColor: Colors.red,
        unselectedItemColor: Colors.grey.shade600,
        selectedLabelStyle: const TextStyle(fontWeight: FontWeight.w600),
        unselectedLabelStyle: const TextStyle(fontWeight: FontWeight.w600),
        type: BottomNavigationBarType.fixed,
        items: const [
          BottomNavigationBarItem(
            icon: Icon(Icons.house),
            label: "Start",
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.chat),
            label: "Chats",
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.account_box),
            label: "Profil",
          ),
        ],
      ),
    );
  }
}
