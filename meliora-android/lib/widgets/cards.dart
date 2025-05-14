import 'package:flutter/material.dart';

class HomePlaylistCard extends StatelessWidget {
  final String imageUrl;
  final String title;
  final VoidCallback onTap;

  const HomePlaylistCard({
    super.key,
    required this.imageUrl,
    required this.title,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return Card.filled(
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(8.0),
      ),
      child: InkWell(
        borderRadius: BorderRadius.circular(8),
        onTap: onTap,
        child: Row(
          children: [
            ClipRRect(
              borderRadius: const BorderRadius.only(
                topLeft: Radius.circular(8),
                bottomLeft: Radius.circular(8),
              ),
              child: Image.network(
                imageUrl,
                width: 75,
                height: 75,
                fit: BoxFit.cover,
              ),
            ),
            const SizedBox(width: 10),
            Text(
              title,
              style: const TextStyle(
                fontWeight: FontWeight.w600,
              ),
            ),
            const Spacer(),
            IconButton.filledTonal(
              onPressed: onTap,
              icon: const Icon(Icons.play_arrow),
            ),
            const SizedBox(width: 10)
          ],
        ),
      ),
    );
  }
}
