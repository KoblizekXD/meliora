import 'package:flutter/material.dart';
import 'package:meliora_mobile/views/onboarding.dart';

class AuthenticationPage extends OnboardingPage {
  const AuthenticationPage({super.key});

  @override
  State<StatefulWidget> createState() => AuthenticationPageState();
}

class AuthenticationPageState extends OnboardingPageState<AuthenticationPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Sign-in", style: TextStyle(fontSize: 24)),
      ),
      body: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 12.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text("Please sign in to your account to continue."),
            const SizedBox(height: 20),
            const TextField(
              decoration: InputDecoration(
                labelText: "Email",
                border: OutlineInputBorder(),
              ),
            ),
            const SizedBox(height: 10),
            const TextField(
              obscureText: true,
              decoration: InputDecoration(
                labelText: "Password",
                border: OutlineInputBorder(),
              ),
            ),
            const SizedBox(height: 10),
            Row(
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                TextButton(
                  style: ButtonStyle(
                    backgroundColor: WidgetStateProperty.resolveWith<Color>(
                          (Set<WidgetState> states) {
                        if (states.contains(WidgetState.pressed)) {
                          return Colors.transparent;
                        }
                        return Colors.transparent;
                      },
                    ),
                    overlayColor: WidgetStateProperty.all(Colors.transparent),
                  ),
                  onPressed: () {},
                  child: const Text("Create Account instead"),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}
