import 'dart:convert';
import 'dart:developer';

import 'package:http/http.dart' as http;
import 'package:meliora_mobile/main.dart';
import 'package:shared_preferences/shared_preferences.dart';

class MelioraService {
  final String baseUrl;
  String? accessToken;
  String? refreshToken;
  
  MelioraService(this.baseUrl, this.accessToken, this.refreshToken);
  
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
    return response.statusCode == 200 && json.decode(response.body)["enable_registrations"] == true;
  }
  
  MelioraService? fromSharedPreferences() {
    final prefs = getIt.get<SharedPreferences>();
    String? baseUrl = prefs.getString("meliora_backend_url");
    String? accessToken = prefs.getString("meliora_access_token");
    String? refreshToken = prefs.getString("meliora_refresh_token");
    if (baseUrl != null && accessToken != null && refreshToken != null) {
      return MelioraService(baseUrl, accessToken, refreshToken);
    }
    return null;
  }
}

/// Check if the given URL is reachable.
/// Returns true if the server's checking endpoint returns a 200 status code.
Future<bool> checkConnection(Uri url) async {
  try {
    final uri = url.resolve("/api/v1/health/check");
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