//
// AUTO-GENERATED FILE, DO NOT MODIFY!
//
// @dart=2.12

// ignore_for_file: unused_element, unused_import
// ignore_for_file: always_put_required_named_parameters_first
// ignore_for_file: constant_identifier_names
// ignore_for_file: lines_longer_than_80_chars

part of openapi.api;

class UpdatePlaylistRequest {
  /// Returns a new [UpdatePlaylistRequest] instance.
  UpdatePlaylistRequest({
    required this.name,
    required this.description,
    this.added,
    this.removed,
  });

  Object? name;

  Object? description;

  Object? added;

  Object? removed;

  @override
  bool operator ==(Object other) => identical(this, other) || other is UpdatePlaylistRequest &&
     other.name == name &&
     other.description == description &&
     other.added == added &&
     other.removed == removed;

  @override
  int get hashCode =>
    // ignore: unnecessary_parenthesis
    (name == null ? 0 : name!.hashCode) +
    (description == null ? 0 : description!.hashCode) +
    (added == null ? 0 : added!.hashCode) +
    (removed == null ? 0 : removed!.hashCode);

  @override
  String toString() => 'UpdatePlaylistRequest[name=$name, description=$description, added=$added, removed=$removed]';

  Map<String, dynamic> toJson() {
    final json = <String, dynamic>{};
    if (this.name != null) {
      json[r'name'] = this.name;
    } else {
      json[r'name'] = null;
    }
    if (this.description != null) {
      json[r'description'] = this.description;
    } else {
      json[r'description'] = null;
    }
    if (this.added != null) {
      json[r'added'] = this.added;
    } else {
      json[r'added'] = null;
    }
    if (this.removed != null) {
      json[r'removed'] = this.removed;
    } else {
      json[r'removed'] = null;
    }
    return json;
  }

  /// Returns a new [UpdatePlaylistRequest] instance and imports its values from
  /// [value] if it's a [Map], null otherwise.
  // ignore: prefer_constructors_over_static_methods
  static UpdatePlaylistRequest? fromJson(dynamic value) {
    if (value is Map) {
      final json = value.cast<String, dynamic>();

      // Ensure that the map contains the required keys.
      // Note 1: the values aren't checked for validity beyond being non-null.
      // Note 2: this code is stripped in release mode!
      assert(() {
        requiredKeys.forEach((key) {
          assert(json.containsKey(key), 'Required key "UpdatePlaylistRequest[$key]" is missing from JSON.');
          assert(json[key] != null, 'Required key "UpdatePlaylistRequest[$key]" has a null value in JSON.');
        });
        return true;
      }());

      return UpdatePlaylistRequest(
        name: mapValueOfType<Object>(json, r'name'),
        description: mapValueOfType<Object>(json, r'description'),
        added: mapValueOfType<Object>(json, r'added'),
        removed: mapValueOfType<Object>(json, r'removed'),
      );
    }
    return null;
  }

  static List<UpdatePlaylistRequest> listFromJson(dynamic json, {bool growable = false,}) {
    final result = <UpdatePlaylistRequest>[];
    if (json is List && json.isNotEmpty) {
      for (final row in json) {
        final value = UpdatePlaylistRequest.fromJson(row);
        if (value != null) {
          result.add(value);
        }
      }
    }
    return result.toList(growable: growable);
  }

  static Map<String, UpdatePlaylistRequest> mapFromJson(dynamic json) {
    final map = <String, UpdatePlaylistRequest>{};
    if (json is Map && json.isNotEmpty) {
      json = json.cast<String, dynamic>(); // ignore: parameter_assignments
      for (final entry in json.entries) {
        final value = UpdatePlaylistRequest.fromJson(entry.value);
        if (value != null) {
          map[entry.key] = value;
        }
      }
    }
    return map;
  }

  // maps a json object with a list of UpdatePlaylistRequest-objects as value to a dart map
  static Map<String, List<UpdatePlaylistRequest>> mapListFromJson(dynamic json, {bool growable = false,}) {
    final map = <String, List<UpdatePlaylistRequest>>{};
    if (json is Map && json.isNotEmpty) {
      // ignore: parameter_assignments
      json = json.cast<String, dynamic>();
      for (final entry in json.entries) {
        map[entry.key] = UpdatePlaylistRequest.listFromJson(entry.value, growable: growable,);
      }
    }
    return map;
  }

  /// The list of required keys that must be present in a JSON.
  static const requiredKeys = <String>{
    'name',
    'description',
  };
}

