import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:meliora_android/views/onboarding.dart';

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
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text("Invalid URL"),
            duration: Duration(seconds: 2),
            behavior: SnackBarBehavior.floating,
          ),
        );
        return false;
      }
      var result = false;
      try {
        final response = await http.get(Uri.parse(url).resolve("/api/v1/check"));
        result = response.statusCode == 200;
      } catch (e) {
        result = false;
      }
      if (!result) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text("Invalid URL"),
            duration: Duration(seconds: 2),
            behavior: SnackBarBehavior.floating,
          ),
        );
      }
      return result;
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
            const SizedBox(height: 64),
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