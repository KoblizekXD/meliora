import 'package:flutter/material.dart';
import 'package:meliora_mobile/client/api.dart';
import 'package:meliora_mobile/jwt.dart';
import 'package:meliora_mobile/main.dart';
import 'package:meliora_mobile/services/meliora_service.dart';
import 'package:meliora_mobile/util.dart';
import 'package:meliora_mobile/views/onboarding/authenticate.dart';
import 'package:meliora_mobile/views/onboarding/connection.dart';
import 'package:meliora_mobile/views/onboarding/welcome.dart';
import 'package:shared_preferences/shared_preferences.dart';

class OnboardingScreen extends StatefulWidget {
  const OnboardingScreen({super.key});

  @override
  State<StatefulWidget> createState() => _OnboardingScreenState();
}

class _OnboardingScreenState extends State<OnboardingScreen> {
  final PageController _pageController = PageController();
  final sp = getIt.get<SharedPreferences>();
  int _currentPage = 0;

  void _completeOnboarding() {
    // Navigator.of(context).pushReplacementNamed('/home');
  }
  
  Future<void> _setAccordinglyPage() async {
    final backendUrl = sp.getString("meliora_backend_url");
    if (backendUrl == null || !(await checkConnection(Uri.parse(backendUrl)))) {
      if (backendUrl != null) _currentPage = 1;
      if (mounted && backendUrl != null) {
        showSnackBar(context,
            "Failed to connect to Meliora server at $backendUrl. Please check your connection and try again.",
            duration: const Duration(seconds: 5));
      }
      return;
    }
    _currentPage = 1;
    final accessToken = sp.getString("meliora_access_token");
    final refreshToken = sp.getString("meliora_refresh_token");
    if (accessToken != null && !isTokenExpired(accessToken)) {
      _completeOnboarding();
    } else if (refreshToken != null && !isTokenExpired(refreshToken)) {
      final temp = ApiClient(
          basePath: backendUrl,
          authentication: null
      );
      try {
        final authControllerApi = AuthControllerApi(temp);
        final response = await authControllerApi.refresh("Bearer $refreshToken");
        if (response != null) {
          await sp.setString("meliora_access_token", (response as dynamic).accessToken);
          await sp.setString("meliora_refresh_token", (response as dynamic).refreshToken);
          getIt.registerSingleton(ApiClient(basePath: backendUrl, authentication: HttpBearerAuth()..accessToken = response.toString()));
          _completeOnboarding();
        } else {
          _currentPage = 2;
        }
      } catch (e) {
        _currentPage = 2;
      }
    }
    _currentPage = 2;
  }
  
  late Future<void> res;
  
  @override
  void initState() {
    super.initState();
    res = _setAccordinglyPage();
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
    
    return FutureBuilder(
      future: res,
      builder: (context, asyncSnapshot) {
        if (asyncSnapshot.connectionState != ConnectionState.done) {
          return const Center(child: CircularProgressIndicator());
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