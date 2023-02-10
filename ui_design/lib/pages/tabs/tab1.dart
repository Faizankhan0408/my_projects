import 'package:flutter/material.dart';
class Tab1 extends StatelessWidget {
  const Tab1({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const Scaffold(
      body: Center(
        child: Text("TAB 1",
          style: TextStyle(
              fontSize: 30,
              fontWeight: FontWeight.bold
          ),),
      ),
    );
  }
}
