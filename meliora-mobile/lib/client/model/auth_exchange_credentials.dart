//
// AUTO-GENERATED FILE, DO NOT MODIFY!
//
// @dart=2.12

// ignore_for_file: unused_element, unused_import
// ignore_for_file: always_put_required_named_parameters_first
// ignore_for_file: constant_identifier_names
// ignore_for_file: lines_longer_than_80_chars

part of openapi.api;

class AuthExchangeCredentials {
  /// Returns a new [AuthExchangeCredentials] instance.
  AuthExchangeCredentials({
    required this.accessToken,
    required this.refreshToken,
  });

  Object? accessToken;

  Object? refreshToken;

  @override
  bool operator ==(Object other) => identical(this, other) || other is AuthExchangeCredentials &&
     other.accessToken == accessToken &&
     other.refreshToken == refreshToken;

  @override
  int get hashCode =>
    // ignore: unnecessary_parenthesis
    (accessToken == null ? 0 : accessToken!.hashCode) +
    (refreshToken == null ? 0 : refreshToken!.hashCode);

  @override
  String toString() => 'AuthExchangeCredentials[accessToken=$accessToken, refreshToken=$refreshToken]';

  Map<String, dynamic> toJson() {
    final json = <String, dynamic>{};
    if (this.accessToken != null) {
      json[r'accessToken'] = this.accessToken;
    } else {
      json[r'accessToken'] = null;
    }
    if (this.refreshToken != null) {
      json[r'refreshToken'] = this.refreshToken;
    } else {
      json[r'refreshToken'] = null;
    }
    return json;
  }

  /// Returns a new [AuthExchangeCredentials] instance and imports its values from
  /// [value] if it's a [Map], null otherwise.
  // ignore: prefer_constructors_over_static_methods
  static AuthExchangeCredentials? fromJson(dynamic value) {
    if (value is Map) {
      final json = value.cast<String, dynamic>();

      // Ensure that the map contains the required keys.
      // Note 1: the values aren't checked for validity beyond being non-null.
      // Note 2: this code is stripped in release mode!
      assert(() {
        requiredKeys.forEach((key) {
          assert(json.containsKey(key), 'Required key "AuthExchangeCredentials[$key]" is missing from JSON.');
          assert(json[key] != null, 'Required key "AuthExchangeCredentials[$key]" has a null value in JSON.');
        });
        return true;
      }());

      return AuthExchangeCredentials(
        accessToken: mapValueOfType<Object>(json, r'accessToken'),
        refreshToken: mapValueOfType<Object>(json, r'refreshToken'),
      );
    }
    return null;
  }

  static List<AuthExchangeCredentials> listFromJson(dynamic json, {bool growable = false,}) {
    final result = <AuthExchangeCredentials>[];
    if (json is List && json.isNotEmpty) {
      for (final row in json) {
        final value = AuthExchangeCredentials.fromJson(row);
        if (value != null) {
          result.add(value);
        }
      }
    }
    return result.toList(growable: growable);
  }

  static Map<String, AuthExchangeCredentials> mapFromJson(dynamic json) {
    final map = <String, AuthExchangeCredentials>{};
    if (json is Map && json.isNotEmpty) {
      json = json.cast<String, dynamic>(); // ignore: parameter_assignments
      for (final entry in json.entries) {
        final value = AuthExchangeCredentials.fromJson(entry.value);
        if (value != null) {
          map[entry.key] = value;
        }
      }
    }
    return map;
  }

  // maps a json object with a list of AuthExchangeCredentials-objects as value to a dart map
  static Map<String, List<AuthExchangeCredentials>> mapListFromJson(dynamic json, {bool growable = false,}) {
    final map = <String, List<AuthExchangeCredentials>>{};
    if (json is Map && json.isNotEmpty) {
      // ignore: parameter_assignments
      json = json.cast<String, dynamic>();
      for (final entry in json.entries) {
        map[entry.key] = AuthExchangeCredentials.listFromJson(entry.value, growable: growable,);
      }
    }
    return map;
  }

  /// The list of required keys that must be present in a JSON.
  static const requiredKeys = <String>{
    'accessToken',
    'refreshToken',
  };
}

