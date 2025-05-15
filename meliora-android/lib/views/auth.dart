import 'package:animations/animations.dart';
import 'package:flutter/material.dart';

class AuthView extends StatefulWidget {
  const AuthView({super.key});

  @override
  State<AuthView> createState() => _AuthViewState();
}

class _AuthViewState extends State<AuthView> {
  final FocusNode _focusNode = FocusNode();
  int pageIndex = 0;

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addPostFrameCallback((_) {
      _focusNode.requestFocus();
    });
  }
  
  @override
  Widget build(BuildContext context) {
    /*return (
      Scaffold(
        body: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 64),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const Text("Welcome to Meliora", style: TextStyle(
                fontSize: 32,
                fontWeight: FontWeight.bold,
              )),
              const SizedBox(height: 128),
              const Text("Please input your valid server URL", style: TextStyle(
                fontSize: 16,
                fontWeight: FontWeight.w500,
              )),
              const SizedBox(height: 12),
              TextField(
                focusNode: _focusNode,
                decoration: const InputDecoration(border: OutlineInputBorder(), labelText: 'Server URL', hintText: "https://meliora.example.com"),
              ),
              const Spacer(),
              Row(
                  mainAxisAlignment: MainAxisAlignment.end,
                  children: [
                    FilledButton.tonal(
                      onPressed: () {}, child: const Text("Continue"),
                    )
                  ],
              )
            ],
          ),
        ),
      )
    );*/
    final List<Widget> pages = [
      const Center(child: Text('Step 1', style: TextStyle(fontSize: 32))),
      const Center(child: Text('Step 2', style: TextStyle(fontSize: 32))),
      const Center(child: Text('Step 3', style: TextStyle(fontSize: 32))),
    ];

    void nextPage() {
      setState(() {
        pageIndex = (pageIndex + 1).clamp(0, pages.length - 1);
      });
    }

    void prevPage() {
      setState(() {
        pageIndex = (pageIndex - 1).clamp(0, pages.length - 1);
      });
    }
    
    return Scaffold(
      body: PageTransitionSwitcher(
        duration: const Duration(milliseconds: 300),
        reverse: false,
        transitionBuilder: (
            Widget child,
            Animation<double> primaryAnimation,
            Animation<double> secondaryAnimation,
            ) =>
            SharedAxisTransition(
              animation: primaryAnimation,
              secondaryAnimation: secondaryAnimation,
              transitionType: SharedAxisTransitionType.horizontal,
              child: child,
            ),
        child: pages[pageIndex],
      ),
      bottomNavigationBar: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
        child: Row(
          mainAxisAlignment: pageIndex == 0 ? MainAxisAlignment.end : MainAxisAlignment.spaceBetween,
          children: [
            if (pageIndex > 0)
              ElevatedButton(onPressed: prevPage, child: const Text('Back')),
            ElevatedButton(
              onPressed: nextPage,
              child: Text(pageIndex == pages.length - 1 ? 'Finish' : 'Next'),
            ),
          ],
        ),
      ),
    );
  }
}