import 'package:flutter/material.dart';
import 'package:meliora_mobile/views/onboarding/authenticate.dart';
import 'package:meliora_mobile/views/onboarding/connection.dart';
import 'package:meliora_mobile/views/onboarding/welcome.dart';

class OnboardingScreen extends StatefulWidget {
  const OnboardingScreen({super.key});

  @override
  State<StatefulWidget> createState() => _OnboardingScreenState();
}

class _OnboardingScreenState extends State<OnboardingScreen> {
  final PageController _pageController = PageController();
  int _currentPage = 0;

  void _completeOnboarding() {
    // Navigator.of(context).pushReplacementNamed('/home');
  }

  @override
  Widget build(BuildContext context) {
    final List<GlobalKey<OnboardingPageState>> keys = [
      GlobalKey<WelcomePageState>(),
      GlobalKey<ConnectionPageState>(),
      GlobalKey<AuthenticationPageState>()
    ];

    final List<OnboardingPage> pages = [
      WelcomePage(key: keys[0]),
      ConnectionPage(key: keys[1]),
      AuthenticationPage(key: keys[2]),
    ];
    
    bool nextButtonLoading = false;
    
    Future<bool> onNextPressed() async {
      setState(() {
        nextButtonLoading = true;
      });
      final currentPageState = keys[_currentPage].currentState;
      if (currentPageState != null && currentPageState.validate != null) {
        final isValid = await currentPageState.validate!();
        if (!isValid) return false;
      }

      if (currentPageState != null && currentPageState.onSubmit != null) {
        await currentPageState.onSubmit!();
      }
      
      setState(() {
        nextButtonLoading = false;
      });
      
      if (_currentPage < pages.length - 1) {
        _pageController.nextPage(
          duration: const Duration(milliseconds: 300),
          curve: Curves.easeInOut,
        );
        return true;
      }

      _completeOnboarding();
      return true;
    }
    
    return Scaffold(
      body: PageView(
        controller: _pageController,
        physics: const NeverScrollableScrollPhysics(), // Disable swipe
        onPageChanged: (index) => setState(() => _currentPage = index),
        children: pages,
      ),
      bottomNavigationBar: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
        child: Row(
          mainAxisAlignment: _currentPage == 0
              ? MainAxisAlignment.end
              : MainAxisAlignment.spaceBetween,
          children: [
            if (_currentPage > 0)
              ElevatedButton(onPressed: () => _pageController.previousPage(
                duration: const Duration(milliseconds: 300),
                curve: Curves.easeInOut,
              ), child: const Text('Back')),
            ElevatedButton(
              onPressed: onNextPressed,
              child: !nextButtonLoading ? Text(_currentPage == pages.length - 1 ? 'Finish' : 'Next') : Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  const SizedBox(
                    width: 16,
                    height: 16,
                    child: CircularProgressIndicator(
                      strokeWidth: 2,
                      color: Colors.white,
                    ),
                  ),
                  const SizedBox(width: 8),
                  Text(_currentPage == pages.length - 1 ? 'Finish' : 'Next'),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}

abstract class OnboardingPage extends StatefulWidget {
  const OnboardingPage({super.key});
}

abstract class OnboardingPageState<T extends OnboardingPage> extends State<T> {
  Future<bool> Function()? get validate => null;
  Future<void> Function()? get onSubmit => null;
}