import 'package:flutter/material.dart';

class MediaQueryDemo extends StatelessWidget {
  const MediaQueryDemo({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Padding(
          padding: const EdgeInsets.all(50.0),
          child: Center(
            child: Container(
              height: 100,
              width: 300,
              child: Text("Orientation:${MediaQuery.of(context).orientation} /nHeight:${MediaQuery.of(context).size.height}" ,style: TextStyle(fontSize: 30),),
            ),
          ),
        ),
        Padding(
          padding: const EdgeInsets.all(50.0),
          child: Center(
            child: Container(
              height: 100,
              width: 300,
              child: Text("Orientation:${MediaQuery.of(context).orientation} Width:${MediaQuery.of(context).size.width}" ,style: const TextStyle(fontSize: 30),),
            ),
          ),
        ),
      ],
    );
  }
}
