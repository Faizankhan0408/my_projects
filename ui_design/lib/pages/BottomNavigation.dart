import 'package:flutter/material.dart';
import 'package:ui_design/pages/like.dart';
import 'package:ui_design/pages/settings.dart';

import 'home.dart';

class NavigationBottom extends StatefulWidget {
  const NavigationBottom({Key? key}) : super(key: key);

  @override
  State<NavigationBottom> createState() => _NavigationBottomState();
}

class _NavigationBottomState extends State<NavigationBottom> {
  int _selectIndex = 0;

  final List<Widget> _pages = [
    const Home(),
    const Likes(),
    const Settings(),
  ];

  void _navigationBarIndex(int index) {
    setState(() {
      _selectIndex = index;
    });
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      // appBar: AppBar(
      //   title: const Text("A P P B A R"),
      //   backgroundColor: Colors.red,
      //   leading: IconButton(
      //     onPressed: () {
      //       // do something
      //     },
      //     icon: const Icon(Icons.menu),
      //   ),
      //   actions: [
      //     IconButton(onPressed: () {}, icon: const Icon(Icons.share)),
      //     IconButton(onPressed: () {}, icon: const Icon(Icons.subscriptions)),
      //   ],
      // ),
      // body: const Center(
      //   child: Text(
      //     "hello",
      //     style: TextStyle(color: Colors.redAccent, fontSize: 50),
      //   ),
      // ),
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _selectIndex,
        onTap: _navigationBarIndex,
        // type: BottomNavigationBarType.fixed,
        items: const [
          BottomNavigationBarItem(
            icon: Icon(Icons.home),
            label: "Home",
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.heart_broken_sharp),
            label: "Likes",
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.settings),
            label: "Settings",
          ),

          BottomNavigationBarItem(
            icon: Icon(Icons.settings),
            label: "Settings",
          )
        ],
      ),
    );
  }
}
