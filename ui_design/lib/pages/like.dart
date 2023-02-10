import 'package:flutter/material.dart';

class Likes extends StatelessWidget {
  const Likes({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const Center(
      child: Padding(
        padding: EdgeInsets.all(8.0),
        child: Text("Likes page",
          style: TextStyle(
              color: Colors.redAccent,fontSize: 50
          ),),
      ),
    );
  }
}
