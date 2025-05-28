//
// AUTO-GENERATED FILE, DO NOT MODIFY!
//
// @dart=2.12

// ignore_for_file: unused_element, unused_import
// ignore_for_file: always_put_required_named_parameters_first
// ignore_for_file: constant_identifier_names
// ignore_for_file: lines_longer_than_80_chars

part of openapi.api;

class PublicPlaylistData {
  /// Returns a new [PublicPlaylistData] instance.
  PublicPlaylistData({
    this.id,
    this.name,
    this.description,
    this.coverImage,
    this.songs,
    this.owner,
  });

  Object? id;

  Object? name;

  Object? description;

  Object? coverImage;

  Object? songs;

  Object? owner;

  @override
  bool operator ==(Object other) => identical(this, other) || other is PublicPlaylistData &&
     other.id == id &&
     other.name == name &&
     other.description == description &&
     other.coverImage == coverImage &&
     other.songs == songs &&
     other.owner == owner;

  @override
  int get hashCode =>
    // ignore: unnecessary_parenthesis
    (id == null ? 0 : id!.hashCode) +
    (name == null ? 0 : name!.hashCode) +
    (description == null ? 0 : description!.hashCode) +
    (coverImage == null ? 0 : coverImage!.hashCode) +
    (songs == null ? 0 : songs!.hashCode) +
    (owner == null ? 0 : owner!.hashCode);

  @override
  String toString() => 'PublicPlaylistData[id=$id, name=$name, description=$description, coverImage=$coverImage, songs=$songs, owner=$owner]';

  Map<String, dynamic> toJson() {
    final json = <String, dynamic>{};
    if (this.id != null) {
      json[r'id'] = this.id;
    } else {
      json[r'id'] = null;
    }
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
    if (this.coverImage != null) {
      json[r'coverImage'] = this.coverImage;
    } else {
      json[r'coverImage'] = null;
    }
    if (this.songs != null) {
      json[r'songs'] = this.songs;
    } else {
      json[r'songs'] = null;
    }
    if (this.owner != null) {
      json[r'owner'] = this.owner;
    } else {
      json[r'owner'] = null;
    }
    return json;
  }

  /// Returns a new [PublicPlaylistData] instance and imports its values from
  /// [value] if it's a [Map], null otherwise.
  // ignore: prefer_constructors_over_static_methods
  static PublicPlaylistData? fromJson(dynamic value) {
    if (value is Map) {
      final json = value.cast<String, dynamic>();

      // Ensure that the map contains the required keys.
      // Note 1: the values aren't checked for validity beyond being non-null.
      // Note 2: this code is stripped in release mode!
      assert(() {
        requiredKeys.forEach((key) {
          assert(json.containsKey(key), 'Required key "PublicPlaylistData[$key]" is missing from JSON.');
          assert(json[key] != null, 'Required key "PublicPlaylistData[$key]" has a null value in JSON.');
        });
        return true;
      }());

      return PublicPlaylistData(
        id: mapValueOfType<Object>(json, r'id'),
        name: mapValueOfType<Object>(json, r'name'),
        description: mapValueOfType<Object>(json, r'description'),
        coverImage: mapValueOfType<Object>(json, r'coverImage'),
        songs: mapValueOfType<Object>(json, r'songs'),
        owner: mapValueOfType<Object>(json, r'owner'),
      );
    }
    return null;
  }

  static List<PublicPlaylistData> listFromJson(dynamic json, {bool growable = false,}) {
    final result = <PublicPlaylistData>[];
    if (json is List && json.isNotEmpty) {
      for (final row in json) {
        final value = PublicPlaylistData.fromJson(row);
        if (value != null) {
          result.add(value);
        }
      }
    }
    return result.toList(growable: growable);
  }

  static Map<String, PublicPlaylistData> mapFromJson(dynamic json) {
    final map = <String, PublicPlaylistData>{};
    if (json is Map && json.isNotEmpty) {
      json = json.cast<String, dynamic>(); // ignore: parameter_assignments
      for (final entry in json.entries) {
        final value = PublicPlaylistData.fromJson(entry.value);
        if (value != null) {
          map[entry.key] = value;
        }
      }
    }
    return map;
  }

  // maps a json object with a list of PublicPlaylistData-objects as value to a dart map
  static Map<String, List<PublicPlaylistData>> mapListFromJson(dynamic json, {bool growable = false,}) {
    final map = <String, List<PublicPlaylistData>>{};
    if (json is Map && json.isNotEmpty) {
      // ignore: parameter_assignments
      json = json.cast<String, dynamic>();
      for (final entry in json.entries) {
        map[entry.key] = PublicPlaylistData.listFromJson(entry.value, growable: growable,);
      }
    }
    return map;
  }

  /// The list of required keys that must be present in a JSON.
  static const requiredKeys = <String>{
  };
}

