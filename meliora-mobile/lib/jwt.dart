import 'dart:convert';

/// Decodes the payload of a JWT (without verifying the signature).
Map<String, dynamic> _decodePayload(String token) {
  final parts = token.split('.');
  if (parts.length != 3) {
    throw FormatException('Invalid JWT token');
  }

  final payload = parts[1];

  // Fix base64 padding if necessary
  String normalized = base64.normalize(payload);
  final payloadMap = json.decode(utf8.decode(base64Url.decode(normalized)));

  if (payloadMap is! Map<String, dynamic>) {
    throw FormatException('Invalid payload');
  }

  return payloadMap;
}

/// Returns true if the JWT token is expired.
bool isTokenExpired(String token) {
  try {
    final payload = _decodePayload(token);
    if (!payload.containsKey('exp')) return false;

    final exp = payload['exp'];
    final expiry = DateTime.fromMillisecondsSinceEpoch(exp * 1000);
    return DateTime.now().isAfter(expiry);
  } catch (_) {
    return true; // Consider invalid token as expired
  }
}

/// Returns the expiry date of the JWT token, or null if invalid or missing 'exp'.
DateTime? getTokenExpiryDate(String token) {
  try {
    final payload = _decodePayload(token);
    final exp = payload['exp'];
    return DateTime.fromMillisecondsSinceEpoch(exp * 1000);
  } catch (_) {
    return null;
  }
}