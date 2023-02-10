import 'package:flutter/material.dart';
import 'package:ui_design/pages/like.dart';
import 'package:ui_design/pages/settings.dart';

class SideDrawer extends StatefulWidget {
  const SideDrawer({Key? key}) : super(key: key);

  @override
  State<SideDrawer> createState() => _SideDrawerState();
}

class _SideDrawerState extends State<SideDrawer> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.cyan,
        elevation: 0,
        title: const Padding(
          padding: EdgeInsets.all(8.0),
          child: Text(
            "A P P B A R",
            style: TextStyle(fontWeight: FontWeight.bold, color: Colors.white),
          ),
        ),
      ),
      drawer: Drawer(
        child: Container(
          color: Colors.cyan[600],
          child: ListView(
            children: [
              const DrawerHeader(
                  child: Center(
                      child: Text(
                "My Drawer",
                style: TextStyle(fontWeight: FontWeight.bold, fontSize: 40,color: Colors.white),
              ))),
              ListTile(
                contentPadding: const EdgeInsets.all(10),
                title: const Text("Home",style: TextStyle(color: Colors.white),),
               leading: const Icon(Icons.home,size: 30,color: Colors.white,),
                onTap: (){
                  Navigator.of(context).push(MaterialPageRoute(builder: (context)=>const Settings()));
                },
              )
            ],
          ),
        ),
      ),
    );
  }
}
