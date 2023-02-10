import 'package:flutter/material.dart';
class Story extends StatelessWidget {
  const Story({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: Container(
        height: 50,
        width: 50,
        decoration: const BoxDecoration(
          color: Colors.deepOrange,
          shape: BoxShape.circle,
        ),
      ),
    );
  }
}
