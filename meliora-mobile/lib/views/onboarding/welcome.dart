import 'package:flutter/material.dart';
import 'package:meliora_mobile/views/onboarding.dart';

class WelcomePage extends OnboardingPage {
  const WelcomePage({super.key});

  @override
  State<StatefulWidget> createState() => WelcomePageState();
}

class WelcomePageState extends OnboardingPageState<WelcomePage> {
  @override
  Widget build(BuildContext context) {
    return const Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        Image(image: AssetImage("assets/logo.png")),
        SizedBox(height: 64),
        Text("Welcome to Meliora",
            style: TextStyle(
              fontSize: 32,
              fontWeight: FontWeight.bold,
            )),
        Text(
          "This quick tutorial will guide you through setting up connection to your remote server.",
          style: TextStyle(
            fontSize: 16,
            fontWeight: FontWeight.w500,
          ),
          textAlign: TextAlign.center,
        ),
      ],
    );
  }
}