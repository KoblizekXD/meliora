//
// AUTO-GENERATED FILE, DO NOT MODIFY!
//
// @dart=2.12

// ignore_for_file: unused_element, unused_import
// ignore_for_file: always_put_required_named_parameters_first
// ignore_for_file: constant_identifier_names
// ignore_for_file: lines_longer_than_80_chars

part of openapi.api;

class PublicUserResponse {
  /// Returns a new [PublicUserResponse] instance.
  PublicUserResponse({
    this.id,
    this.username,
    this.profilePictureUrl,
    this.favoritesId,
    this.publicPlaylists,
  });

  Object? id;

  Object? username;

  Object? profilePictureUrl;

  Object? favoritesId;

  Object? publicPlaylists;

  @override
  bool operator ==(Object other) => identical(this, other) || other is PublicUserResponse &&
     other.id == id &&
     other.username == username &&
     other.profilePictureUrl == profilePictureUrl &&
     other.favoritesId == favoritesId &&
     other.publicPlaylists == publicPlaylists;

  @override
  int get hashCode =>
    // ignore: unnecessary_parenthesis
    (id == null ? 0 : id!.hashCode) +
    (username == null ? 0 : username!.hashCode) +
    (profilePictureUrl == null ? 0 : profilePictureUrl!.hashCode) +
    (favoritesId == null ? 0 : favoritesId!.hashCode) +
    (publicPlaylists == null ? 0 : publicPlaylists!.hashCode);

  @override
  String toString() => 'PublicUserResponse[id=$id, username=$username, profilePictureUrl=$profilePictureUrl, favoritesId=$favoritesId, publicPlaylists=$publicPlaylists]';

  Map<String, dynamic> toJson() {
    final json = <String, dynamic>{};
    if (this.id != null) {
      json[r'id'] = this.id;
    } else {
      json[r'id'] = null;
    }
    if (this.username != null) {
      json[r'username'] = this.username;
    } else {
      json[r'username'] = null;
    }
    if (this.profilePictureUrl != null) {
      json[r'profilePictureUrl'] = this.profilePictureUrl;
    } else {
      json[r'profilePictureUrl'] = null;
    }
    if (this.favoritesId != null) {
      json[r'favoritesId'] = this.favoritesId;
    } else {
      json[r'favoritesId'] = null;
    }
    if (this.publicPlaylists != null) {
      json[r'publicPlaylists'] = this.publicPlaylists;
    } else {
      json[r'publicPlaylists'] = null;
    }
    return json;
  }

  /// Returns a new [PublicUserResponse] instance and imports its values from
  /// [value] if it's a [Map], null otherwise.
  // ignore: prefer_constructors_over_static_methods
  static PublicUserResponse? fromJson(dynamic value) {
    if (value is Map) {
      final json = value.cast<String, dynamic>();

      // Ensure that the map contains the required keys.
      // Note 1: the values aren't checked for validity beyond being non-null.
      // Note 2: this code is stripped in release mode!
      assert(() {
        requiredKeys.forEach((key) {
          assert(json.containsKey(key), 'Required key "PublicUserResponse[$key]" is missing from JSON.');
          assert(json[key] != null, 'Required key "PublicUserResponse[$key]" has a null value in JSON.');
        });
        return true;
      }());

      return PublicUserResponse(
        id: mapValueOfType<Object>(json, r'id'),
        username: mapValueOfType<Object>(json, r'username'),
        profilePictureUrl: mapValueOfType<Object>(json, r'profilePictureUrl'),
        favoritesId: mapValueOfType<Object>(json, r'favoritesId'),
        publicPlaylists: mapValueOfType<Object>(json, r'publicPlaylists'),
      );
    }
    return null;
  }

  static List<PublicUserResponse> listFromJson(dynamic json, {bool growable = false,}) {
    final result = <PublicUserResponse>[];
    if (json is List && json.isNotEmpty) {
      for (final row in json) {
        final value = PublicUserResponse.fromJson(row);
        if (value != null) {
          result.add(value);
        }
      }
    }
    return result.toList(growable: growable);
  }

  static Map<String, PublicUserResponse> mapFromJson(dynamic json) {
    final map = <String, PublicUserResponse>{};
    if (json is Map && json.isNotEmpty) {
      json = json.cast<String, dynamic>(); // ignore: parameter_assignments
      for (final entry in json.entries) {
        final value = PublicUserResponse.fromJson(entry.value);
        if (value != null) {
          map[entry.key] = value;
        }
      }
    }
    return map;
  }

  // maps a json object with a list of PublicUserResponse-objects as value to a dart map
  static Map<String, List<PublicUserResponse>> mapListFromJson(dynamic json, {bool growable = false,}) {
    final map = <String, List<PublicUserResponse>>{};
    if (json is Map && json.isNotEmpty) {
      // ignore: parameter_assignments
      json = json.cast<String, dynamic>();
      for (final entry in json.entries) {
        map[entry.key] = PublicUserResponse.listFromJson(entry.value, growable: growable,);
      }
    }
    return map;
  }

  /// The list of required keys that must be present in a JSON.
  static const requiredKeys = <String>{
  };
}

