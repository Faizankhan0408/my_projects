import 'package:flutter/material.dart';
class Tab2 extends StatelessWidget {
  const Tab2({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const Scaffold(
      body: Center(
        child: Text("TAB 2",
        style: TextStyle(
          fontSize: 30,
          fontWeight: FontWeight.bold
        ),),
      ),
    );
  }
}
