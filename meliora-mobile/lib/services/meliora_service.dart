import 'dart:convert';
import 'dart:developer';

import 'package:http/http.dart' as http;

class MelioraService {
  final String baseUrl;
  
  MelioraService(this.baseUrl);
  
  Future<bool> checkConnection() async {
    try {
      final uri = Uri.parse(baseUrl).resolve("/api/v1/health/check");
      final response = await http.get(uri);
      return response.statusCode == 200;
    } catch (e) {
      return false;
    }
  }
  
  Future<http.Response> get(String endpoint) async {
    final uri = Uri.parse(baseUrl).resolve(endpoint);
    return await http.get(uri);
  }
  
  Future<bool> registrationsEnabled() async {
    final response = await get("/api/v1/health/check");
    return response.statusCode == 200 && json.decode(response.body)["enable_registrations"];
  }
}

/// Check if the given URL is reachable.
/// Returns true if the server's checking endpoint returns a 200 status code.
Future<bool> checkConnection(Uri url) async {
  try {
    final uri = url.resolve("/api/v1/check");
    log("Checking connection to $uri");
    final response = await http.get(uri);
    log("Server response: ${response.statusCode} - ${response.body}");
    return response.statusCode == 200;
  } catch (e) {
    log("Error checking connection: $e");
    return false;
  }
}

class MelioraServiceBuilder {
  late String baseUrl;
  late String accessToken;
  late String refreshToken;
  
  MelioraServiceBuilder setBaseUrl(String url) {
    baseUrl = url;
    return this;
  }
  
  MelioraServiceBuilder setCredentials(String email, String password) {
    if (email.isEmpty || password.isEmpty) {
      throw ArgumentError("Email and password cannot be empty");
    }
    
    return this;
  }
}