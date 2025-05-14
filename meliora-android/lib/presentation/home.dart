import 'package:flutter/material.dart';
import 'package:meliora_android/widgets/cards.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Padding(
            padding: EdgeInsets.all(16.0),
            child: Text(
              'Good morning, User',
              style: TextStyle(
                fontSize: 24,
                fontWeight: FontWeight.w500,
              ),
            ),
          ),
          HomePlaylistCard(imageUrl: "https://flutter.github.io/assets-for-api-docs/assets/widgets/owl.jpg", title: "y", onTap: () {})
        ],
      ),
    );
  }
}
