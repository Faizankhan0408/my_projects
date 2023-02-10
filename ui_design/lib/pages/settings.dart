import 'package:flutter/material.dart';

class Settings extends StatelessWidget {
  const Settings({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const Center(
      child: Padding(
        padding: EdgeInsets.all(8.0),
        child: Text("Settings page",
          style: TextStyle(
              color: Colors.redAccent,fontSize: 50
          ),),
      ),
    );
  }
}
