import 'package:flutter/material.dart';
import 'package:liquid_pull_to_refresh/liquid_pull_to_refresh.dart';

class CustomLoadingInList extends StatefulWidget {
  const CustomLoadingInList({Key? key}) : super(key: key);

  @override
  State<CustomLoadingInList> createState() => _CustomLoadingInListState();
}

class _CustomLoadingInListState extends State<CustomLoadingInList> {

  Future<void> _handleRefresh()async{

  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: LiquidPullToRefresh(
        backgroundColor: Colors.yellow[900],
        height: 120,
        borderWidth: 4,
        color: Colors.yellow,
        showChildOpacityTransition: false,
        animSpeedFactor: 1,
        onRefresh: _handleRefresh,
        child: ListView(
          children: [
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: ClipRRect(
                borderRadius: BorderRadius.circular(20),
                child: Container(
                  color: Colors.deepPurple[200],
                  height: 200,
                ),
              ),
            ),

            Padding(
              padding: const EdgeInsets.all(8.0),
              child: ClipRRect(
                borderRadius: BorderRadius.circular(20),
                child: Container(
                  color: Colors.deepPurple[200],
                  height: 200,
                ),
              ),
            ),

            Padding(
              padding: const EdgeInsets.all(8.0),
              child: ClipRRect(
                borderRadius: BorderRadius.circular(20),
                child: Container(
                  color: Colors.deepPurple[200],
                  height: 200,
                ),
              ),
            ),

            Padding(
              padding: const EdgeInsets.all(8.0),
              child: ClipRRect(
                borderRadius: BorderRadius.circular(20),
                child: Container(
                  color: Colors.deepPurple[200],
                  height: 200,
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
