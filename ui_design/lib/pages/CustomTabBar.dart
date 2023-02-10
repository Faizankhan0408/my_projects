import 'package:flutter/material.dart';
import 'package:ui_design/pages/BottomNavigation.dart';
import 'package:ui_design/pages/tabs/tab1.dart';
import 'package:ui_design/pages/tabs/tab2.dart';

class CustomTabBar extends StatefulWidget {
  const CustomTabBar({Key? key}) : super(key: key);

  @override
  State<CustomTabBar> createState() => _CustomTabBarState();
}

class _CustomTabBarState extends State<CustomTabBar> {
  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 3,
      child: Scaffold(
        appBar: AppBar(
          title: const Text("T A B B A R"),
        ),
        body: Column(children: const [
          TabBar(
            indicatorColor: Colors.cyan,
            labelColor: Colors.deepPurple,
            automaticIndicatorColorAdjustment: true,
            tabs: [
              Tab(
                icon: Icon(Icons.home),
              ),
              Tab(
                icon: Icon(Icons.message),
              ),
              Tab(
                icon: Icon(Icons.settings),
              ),
            ],
          ),
          Expanded(
            child: TabBarView(children: [
              Tab1(),
              Tab2(),
              Tab1(),
            ]),
          ),
        ]),
      ),
    );
  }
}
