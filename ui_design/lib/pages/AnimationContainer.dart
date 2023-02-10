import 'package:flutter/material.dart';

class CustomAnimationContainer extends StatefulWidget {
  const CustomAnimationContainer({Key? key}) : super(key: key);

  @override
  State<CustomAnimationContainer> createState() => _CustomAnimationContainerState();
}

class _CustomAnimationContainerState extends State<CustomAnimationContainer> {

  double BoxHeight=100;
  double BoxWidth=100;

  void _changeSize(){
    setState(() {
      BoxWidth=300;
      BoxHeight=300;
    });
  }

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: _changeSize,
      child: Center(
        child: AnimatedContainer(
          duration: const Duration(seconds: 2),
          child: Container(
            height: BoxHeight,
            width: BoxWidth,
            color: Colors.deepPurple[200],
          ),
        ),
      ),
    );
  }
}
