//
// AUTO-GENERATED FILE, DO NOT MODIFY!
//
// @dart=2.12

// ignore_for_file: unused_element, unused_import
// ignore_for_file: always_put_required_named_parameters_first
// ignore_for_file: constant_identifier_names
// ignore_for_file: lines_longer_than_80_chars

part of openapi.api;

class FeatureResponse {
  /// Returns a new [FeatureResponse] instance.
  FeatureResponse({
    this.enableRegistration,
  });

  Object? enableRegistration;

  @override
  bool operator ==(Object other) => identical(this, other) || other is FeatureResponse &&
     other.enableRegistration == enableRegistration;

  @override
  int get hashCode =>
    // ignore: unnecessary_parenthesis
    (enableRegistration == null ? 0 : enableRegistration!.hashCode);

  @override
  String toString() => 'FeatureResponse[enableRegistration=$enableRegistration]';

  Map<String, dynamic> toJson() {
    final json = <String, dynamic>{};
    if (this.enableRegistration != null) {
      json[r'enableRegistration'] = this.enableRegistration;
    } else {
      json[r'enableRegistration'] = null;
    }
    return json;
  }

  /// Returns a new [FeatureResponse] instance and imports its values from
  /// [value] if it's a [Map], null otherwise.
  // ignore: prefer_constructors_over_static_methods
  static FeatureResponse? fromJson(dynamic value) {
    if (value is Map) {
      final json = value.cast<String, dynamic>();

      // Ensure that the map contains the required keys.
      // Note 1: the values aren't checked for validity beyond being non-null.
      // Note 2: this code is stripped in release mode!
      assert(() {
        requiredKeys.forEach((key) {
          assert(json.containsKey(key), 'Required key "FeatureResponse[$key]" is missing from JSON.');
          assert(json[key] != null, 'Required key "FeatureResponse[$key]" has a null value in JSON.');
        });
        return true;
      }());

      return FeatureResponse(
        enableRegistration: mapValueOfType<Object>(json, r'enableRegistration'),
      );
    }
    return null;
  }

  static List<FeatureResponse> listFromJson(dynamic json, {bool growable = false,}) {
    final result = <FeatureResponse>[];
    if (json is List && json.isNotEmpty) {
      for (final row in json) {
        final value = FeatureResponse.fromJson(row);
        if (value != null) {
          result.add(value);
        }
      }
    }
    return result.toList(growable: growable);
  }

  static Map<String, FeatureResponse> mapFromJson(dynamic json) {
    final map = <String, FeatureResponse>{};
    if (json is Map && json.isNotEmpty) {
      json = json.cast<String, dynamic>(); // ignore: parameter_assignments
      for (final entry in json.entries) {
        final value = FeatureResponse.fromJson(entry.value);
        if (value != null) {
          map[entry.key] = value;
        }
      }
    }
    return map;
  }

  // maps a json object with a list of FeatureResponse-objects as value to a dart map
  static Map<String, List<FeatureResponse>> mapListFromJson(dynamic json, {bool growable = false,}) {
    final map = <String, List<FeatureResponse>>{};
    if (json is Map && json.isNotEmpty) {
      // ignore: parameter_assignments
      json = json.cast<String, dynamic>();
      for (final entry in json.entries) {
        map[entry.key] = FeatureResponse.listFromJson(entry.value, growable: growable,);
      }
    }
    return map;
  }

  /// The list of required keys that must be present in a JSON.
  static const requiredKeys = <String>{
  };
}

