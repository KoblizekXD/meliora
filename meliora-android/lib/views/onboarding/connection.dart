import 'package:flutter/material.dart';
import 'package:meliora_android/main.dart';
import 'package:meliora_android/services/meliora_service.dart';
import 'package:meliora_android/util.dart';
import 'package:meliora_android/views/onboarding.dart';
import 'package:shared_preferences/shared_preferences.dart';

class ConnectionPage extends OnboardingPage {
  const ConnectionPage({super.key});

  @override
  State<ConnectionPage> createState() => ConnectionPageState();
}

class ConnectionPageState extends OnboardingPageState<ConnectionPage> {
  final TextEditingController _urlController = TextEditingController();
  final FocusNode _focusNode = FocusNode();

  @override
  void dispose() {
    _urlController.dispose();
    _focusNode.dispose();
    super.dispose();
  }
  
  @override
  Future<bool> Function()? get validate {
    return () async {
      final url = _urlController.text;
      if (url.isEmpty) {
        showSnackBar(context, "Please enter a valid URL");
        return false;
      }
      var result = await checkConnection(Uri.parse(url));
      if (!result && mounted) {
        showSnackBar(context, "Could not connect to $url");
      }
      return result;
    };
  }

  @override
  Future<void> Function()? get onSubmit {
    
    return () async {
      getIt.get<SharedPreferences>().setString("meliora_backend_url", _urlController.text);
    };
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Connect to server",
            style: TextStyle(fontSize: 24)),
      ),
      body: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 12.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text("Enter the URL of your Meliora backend server. This may be a public URL or a local IP address.",
                style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.w500,
                )),
            const SizedBox(height: 8),
            const Text("Server URL",
                style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.w500,
                )),
            const SizedBox(height: 12),
            TextField(
              controller: _urlController,
              focusNode: _focusNode,
              decoration: const InputDecoration(
                border: OutlineInputBorder(),
                labelText: 'https://meliora.example.com',
              ),
            ),
          ],
        ),
      ),
    );
  }
}