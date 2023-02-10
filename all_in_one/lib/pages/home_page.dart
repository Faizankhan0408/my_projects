import 'package:flutter/material.dart';

import '../ui_util/CustomCard.dart';

class HomePage extends StatefulWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("All in one"),
        centerTitle: true,
      ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Column(
          children: [
            buildCard(),
          ],
        ),
      ),
    );
  }


  Card buildCard() {
    var heading = '\$2300 per month';
    var subheading = '2 bed, 1 bath, 1300 sqft';
    var cardImage = const NetworkImage(
        'https://source.unsplash.com/random/800x600?house');
    var supportingText =
        'Beautiful home to rent, recently refurbished with modern appliances...';
    return Card(
        elevation: 4.0,
        child: Column(
          children: [
            ListTile(
              title: Text(heading),
              subtitle: Text(subheading),
              trailing: const Icon(Icons.favorite_outline),
            ),
            Container(
              height: 200.0,
              child: Ink.image(
                image: cardImage,
                fit: BoxFit.cover,
              ),
            ),
            Container(
              padding: const EdgeInsets.all(16.0),
              alignment: Alignment.centerLeft,
              child: Text(supportingText),
            ),
            ButtonBar(
              children: [
                TextButton(
                  child: const Text('CONTACT AGENT'),
                  onPressed: () {/* ... */},
                ),
                TextButton(
                  child: const Text('LEARN MORE'),
                  onPressed: () {/* ... */},
                )
              ],
            )
          ],
        ));
  }

}
