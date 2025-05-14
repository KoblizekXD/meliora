import 'package:flutter/material.dart';
import 'package:meliora_android/presentation/home.dart';
import 'package:meliora_android/widgets/player.dart';

import 'theme.dart';
import 'util.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    final brightness = View.of(context).platformDispatcher.platformBrightness;
    TextTheme textTheme = createTextTheme(context, "Roboto", "Poppins");

    MaterialTheme theme = MaterialTheme(textTheme);
    return MaterialApp(
      title: 'Meliora',
      theme: brightness == Brightness.light ? theme.light() : theme.dark(),
      home: const MainPage(),
    );
  }
}

class MainPage extends StatefulWidget {
  const MainPage({super.key});

  @override
  State<MainPage> createState() => _MainPageState();
}

class _MainPageState extends State<MainPage> {
  int pageIndex = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        actions: [
          IconButton(
            icon: const Icon(Icons.account_circle),
            onPressed: () {},
          ),
        ],
      ),
      body: Stack(
        children: [
          <Widget>[
            const HomePage(),
            const Center(child: Text('Search')),
            const Center(child: Text('My Library')),
          ][pageIndex],
          const Positioned(bottom: 8, left: 8, right: 8, child: MiniPlayer())
        ],
      ),
      bottomNavigationBar: NavigationBar(
          onDestinationSelected: (int index) {
            setState(() {
              pageIndex = index;
            });
          },
          selectedIndex: pageIndex,
          destinations: const <Widget>[
            NavigationDestination(
              icon: Icon(Icons.home),
              label: 'Home',
            ),
            NavigationDestination(
              icon: Icon(Icons.search),
              label: 'Search',
            ),
            NavigationDestination(
              icon: Icon(Icons.library_music_rounded),
              label: 'My Library',
            ),
          ]),
    );
  }
}
