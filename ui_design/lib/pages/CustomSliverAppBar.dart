import 'package:flutter/material.dart';
class CustomSliverAppBar extends StatefulWidget {
  const CustomSliverAppBar({Key? key}) : super(key: key);

  @override
  State<CustomSliverAppBar> createState() => _CustomSliverAppBarState();
}

class _CustomSliverAppBarState extends State<CustomSliverAppBar> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.deepPurple[200],
      body: CustomScrollView(
        slivers: [
           SliverAppBar(
            leading: const Icon(Icons.menu),
            title: const Text("S L I V E R A P P B A R"),
            expandedHeight: 100,
            floating: true,
            flexibleSpace: FlexibleSpaceBar(
              background: Container(color: Colors.yellow,),
            ),
          ),
          
          // sliver items
          SliverToBoxAdapter(
            child: Container(
              height: 400,
              decoration: BoxDecoration(
                color: Colors.deepPurple,
                borderRadius: BorderRadius.circular(10)
              ),
            ),
          ),

          // sliver items
          SliverToBoxAdapter(
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: Container(
                height: 300,
                decoration: BoxDecoration(
                    color: Colors.green,
                    borderRadius: BorderRadius.circular(10)
                ),
              ),
            ),
          ),

          // sliver items
          SliverToBoxAdapter(
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: Container(
                height: 100,
                decoration: BoxDecoration(
                    color: Colors.deepPurple,
                    borderRadius: BorderRadius.circular(10)
                ),
              ),
            ),
          )
        ],
      ),
    );
  }
}

