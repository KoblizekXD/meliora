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
      body: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 8),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Good morning, aa55h',
              style: TextStyle(
                fontSize: 24,
                fontWeight: FontWeight.w500,
              ),
            ),
            Row(
              children: [
                FilledButton.tonalIcon(onPressed: () {}, label: const Text("Upload"),
                    icon: const Icon(Icons.cloud_outlined))
              ],
            ),
            const Text(
              'Your playlists',
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.w500,
              ),
            ),
            HomePlaylistCard(
                imageUrl:
                    "https://flutter.github.io/assets-for-api-docs/assets/widgets/owl.jpg",
                title: "Favorites",
                onTap: () {}),
          ],
        ),
      ),
    );
  }
}
