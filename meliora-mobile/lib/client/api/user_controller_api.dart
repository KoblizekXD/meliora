//
// AUTO-GENERATED FILE, DO NOT MODIFY!
//
// @dart=2.12

// ignore_for_file: unused_element, unused_import
// ignore_for_file: always_put_required_named_parameters_first
// ignore_for_file: constant_identifier_names
// ignore_for_file: lines_longer_than_80_chars

part of openapi.api;


class UserControllerApi {
  UserControllerApi([ApiClient? apiClient]) : apiClient = apiClient ?? defaultApiClient;

  final ApiClient apiClient;

  /// Performs an HTTP 'POST /api/v1/users/@me/playlists' operation and returns the [Response].
  /// Parameters:
  ///
  /// * [CreatePlaylistRequest] createPlaylistRequest (required):
  Future<Response> createPlaylistWithHttpInfo(CreatePlaylistRequest createPlaylistRequest,) async {
    // ignore: prefer_const_declarations
    final path = r'/api/v1/users/@me/playlists';

    // ignore: prefer_final_locals
    Object? postBody = createPlaylistRequest;

    final queryParams = <QueryParam>[];
    final headerParams = <String, String>{};
    final formParams = <String, String>{};

    const contentTypes = <String>['application/json'];


    return apiClient.invokeAPI(
      path,
      'POST',
      queryParams,
      postBody,
      headerParams,
      formParams,
      contentTypes.isEmpty ? null : contentTypes.first,
    );
  }

  /// Parameters:
  ///
  /// * [CreatePlaylistRequest] createPlaylistRequest (required):
  Future<Object?> createPlaylist(CreatePlaylistRequest createPlaylistRequest,) async {
    final response = await createPlaylistWithHttpInfo(createPlaylistRequest,);
    if (response.statusCode >= HttpStatus.badRequest) {
      throw ApiException(response.statusCode, await _decodeBodyBytes(response));
    }
    // When a remote server returns no body with a status of 204, we shall not decode it.
    // At the time of writing this, `dart:convert` will throw an "Unexpected end of input"
    // FormatException when trying to decode an empty string.
    if (response.body.isNotEmpty && response.statusCode != HttpStatus.noContent) {
      return await apiClient.deserializeAsync(await _decodeBodyBytes(response), 'Object',) as Object;
    
    }
    return null;
  }

  /// Performs an HTTP 'GET /api/v1/users/{id}/playlists' operation and returns the [Response].
  /// Parameters:
  ///
  /// * [Object] id (required):
  Future<Response> getPlaylistInformationWithHttpInfo(Object id,) async {
    // ignore: prefer_const_declarations
    final path = r'/api/v1/users/{id}/playlists'
      .replaceAll('{id}', id.toString());

    // ignore: prefer_final_locals
    Object? postBody;

    final queryParams = <QueryParam>[];
    final headerParams = <String, String>{};
    final formParams = <String, String>{};

    const contentTypes = <String>[];


    return apiClient.invokeAPI(
      path,
      'GET',
      queryParams,
      postBody,
      headerParams,
      formParams,
      contentTypes.isEmpty ? null : contentTypes.first,
    );
  }

  /// Parameters:
  ///
  /// * [Object] id (required):
  Future<Object?> getPlaylistInformation(Object id,) async {
    final response = await getPlaylistInformationWithHttpInfo(id,);
    if (response.statusCode >= HttpStatus.badRequest) {
      throw ApiException(response.statusCode, await _decodeBodyBytes(response));
    }
    // When a remote server returns no body with a status of 204, we shall not decode it.
    // At the time of writing this, `dart:convert` will throw an "Unexpected end of input"
    // FormatException when trying to decode an empty string.
    if (response.body.isNotEmpty && response.statusCode != HttpStatus.noContent) {
      return await apiClient.deserializeAsync(await _decodeBodyBytes(response), 'Object',) as Object;
    
    }
    return null;
  }

  /// Performs an HTTP 'GET /api/v1/users/{id}/playlists/{playlist_id}' operation and returns the [Response].
  /// Parameters:
  ///
  /// * [Object] id (required):
  ///
  /// * [Object] playlistId (required):
  Future<Response> getPlaylistInformation1WithHttpInfo(Object id, Object playlistId,) async {
    // ignore: prefer_const_declarations
    final path = r'/api/v1/users/{id}/playlists/{playlist_id}'
      .replaceAll('{id}', id.toString())
      .replaceAll('{playlist_id}', playlistId.toString());

    // ignore: prefer_final_locals
    Object? postBody;

    final queryParams = <QueryParam>[];
    final headerParams = <String, String>{};
    final formParams = <String, String>{};

    const contentTypes = <String>[];


    return apiClient.invokeAPI(
      path,
      'GET',
      queryParams,
      postBody,
      headerParams,
      formParams,
      contentTypes.isEmpty ? null : contentTypes.first,
    );
  }

  /// Parameters:
  ///
  /// * [Object] id (required):
  ///
  /// * [Object] playlistId (required):
  Future<PublicPlaylistData?> getPlaylistInformation1(Object id, Object playlistId,) async {
    final response = await getPlaylistInformation1WithHttpInfo(id, playlistId,);
    if (response.statusCode >= HttpStatus.badRequest) {
      throw ApiException(response.statusCode, await _decodeBodyBytes(response));
    }
    // When a remote server returns no body with a status of 204, we shall not decode it.
    // At the time of writing this, `dart:convert` will throw an "Unexpected end of input"
    // FormatException when trying to decode an empty string.
    if (response.body.isNotEmpty && response.statusCode != HttpStatus.noContent) {
      return await apiClient.deserializeAsync(await _decodeBodyBytes(response), 'PublicPlaylistData',) as PublicPlaylistData;
    
    }
    return null;
  }

  /// Performs an HTTP 'GET /api/v1/users/@me' operation and returns the [Response].
  Future<Response> getSelfWithHttpInfo() async {
    // ignore: prefer_const_declarations
    final path = r'/api/v1/users/@me';

    // ignore: prefer_final_locals
    Object? postBody;

    final queryParams = <QueryParam>[];
    final headerParams = <String, String>{};
    final formParams = <String, String>{};

    const contentTypes = <String>[];


    return apiClient.invokeAPI(
      path,
      'GET',
      queryParams,
      postBody,
      headerParams,
      formParams,
      contentTypes.isEmpty ? null : contentTypes.first,
    );
  }

  Future<PublicUserResponse?> getSelf() async {
    final response = await getSelfWithHttpInfo();
    if (response.statusCode >= HttpStatus.badRequest) {
      throw ApiException(response.statusCode, await _decodeBodyBytes(response));
    }
    // When a remote server returns no body with a status of 204, we shall not decode it.
    // At the time of writing this, `dart:convert` will throw an "Unexpected end of input"
    // FormatException when trying to decode an empty string.
    if (response.body.isNotEmpty && response.statusCode != HttpStatus.noContent) {
      return await apiClient.deserializeAsync(await _decodeBodyBytes(response), 'PublicUserResponse',) as PublicUserResponse;
    
    }
    return null;
  }

  /// Performs an HTTP 'GET /api/v1/users/@me/playlists/{playlist_id}' operation and returns the [Response].
  /// Parameters:
  ///
  /// * [Object] playlistId (required):
  Future<Response> getSelfPlaylistWithHttpInfo(Object playlistId,) async {
    // ignore: prefer_const_declarations
    final path = r'/api/v1/users/@me/playlists/{playlist_id}'
      .replaceAll('{playlist_id}', playlistId.toString());

    // ignore: prefer_final_locals
    Object? postBody;

    final queryParams = <QueryParam>[];
    final headerParams = <String, String>{};
    final formParams = <String, String>{};

    const contentTypes = <String>[];


    return apiClient.invokeAPI(
      path,
      'GET',
      queryParams,
      postBody,
      headerParams,
      formParams,
      contentTypes.isEmpty ? null : contentTypes.first,
    );
  }

  /// Parameters:
  ///
  /// * [Object] playlistId (required):
  Future<PublicPlaylistData?> getSelfPlaylist(Object playlistId,) async {
    final response = await getSelfPlaylistWithHttpInfo(playlistId,);
    if (response.statusCode >= HttpStatus.badRequest) {
      throw ApiException(response.statusCode, await _decodeBodyBytes(response));
    }
    // When a remote server returns no body with a status of 204, we shall not decode it.
    // At the time of writing this, `dart:convert` will throw an "Unexpected end of input"
    // FormatException when trying to decode an empty string.
    if (response.body.isNotEmpty && response.statusCode != HttpStatus.noContent) {
      return await apiClient.deserializeAsync(await _decodeBodyBytes(response), 'PublicPlaylistData',) as PublicPlaylistData;
    
    }
    return null;
  }

  /// Performs an HTTP 'GET /api/v1/users/@me/playlists' operation and returns the [Response].
  Future<Response> getSelfPlaylistsWithHttpInfo() async {
    // ignore: prefer_const_declarations
    final path = r'/api/v1/users/@me/playlists';

    // ignore: prefer_final_locals
    Object? postBody;

    final queryParams = <QueryParam>[];
    final headerParams = <String, String>{};
    final formParams = <String, String>{};

    const contentTypes = <String>[];


    return apiClient.invokeAPI(
      path,
      'GET',
      queryParams,
      postBody,
      headerParams,
      formParams,
      contentTypes.isEmpty ? null : contentTypes.first,
    );
  }

  Future<Object?> getSelfPlaylists() async {
    final response = await getSelfPlaylistsWithHttpInfo();
    if (response.statusCode >= HttpStatus.badRequest) {
      throw ApiException(response.statusCode, await _decodeBodyBytes(response));
    }
    // When a remote server returns no body with a status of 204, we shall not decode it.
    // At the time of writing this, `dart:convert` will throw an "Unexpected end of input"
    // FormatException when trying to decode an empty string.
    if (response.body.isNotEmpty && response.statusCode != HttpStatus.noContent) {
      return await apiClient.deserializeAsync(await _decodeBodyBytes(response), 'Object',) as Object;
    
    }
    return null;
  }

  /// Performs an HTTP 'GET /api/v1/users/{id}' operation and returns the [Response].
  /// Parameters:
  ///
  /// * [Object] id (required):
  Future<Response> getUserWithHttpInfo(Object id,) async {
    // ignore: prefer_const_declarations
    final path = r'/api/v1/users/{id}'
      .replaceAll('{id}', id.toString());

    // ignore: prefer_final_locals
    Object? postBody;

    final queryParams = <QueryParam>[];
    final headerParams = <String, String>{};
    final formParams = <String, String>{};

    const contentTypes = <String>[];


    return apiClient.invokeAPI(
      path,
      'GET',
      queryParams,
      postBody,
      headerParams,
      formParams,
      contentTypes.isEmpty ? null : contentTypes.first,
    );
  }

  /// Parameters:
  ///
  /// * [Object] id (required):
  Future<PublicUserResponse?> getUser(Object id,) async {
    final response = await getUserWithHttpInfo(id,);
    if (response.statusCode >= HttpStatus.badRequest) {
      throw ApiException(response.statusCode, await _decodeBodyBytes(response));
    }
    // When a remote server returns no body with a status of 204, we shall not decode it.
    // At the time of writing this, `dart:convert` will throw an "Unexpected end of input"
    // FormatException when trying to decode an empty string.
    if (response.body.isNotEmpty && response.statusCode != HttpStatus.noContent) {
      return await apiClient.deserializeAsync(await _decodeBodyBytes(response), 'PublicUserResponse',) as PublicUserResponse;
    
    }
    return null;
  }

  /// Performs an HTTP 'PUT /api/v1/users/@me/playlists/{playlist_id}' operation and returns the [Response].
  /// Parameters:
  ///
  /// * [Object] playlistId (required):
  ///
  /// * [UpdatePlaylistRequest] updatePlaylistRequest (required):
  Future<Response> updatePlaylistWithHttpInfo(Object playlistId, UpdatePlaylistRequest updatePlaylistRequest,) async {
    // ignore: prefer_const_declarations
    final path = r'/api/v1/users/@me/playlists/{playlist_id}'
      .replaceAll('{playlist_id}', playlistId.toString());

    // ignore: prefer_final_locals
    Object? postBody = updatePlaylistRequest;

    final queryParams = <QueryParam>[];
    final headerParams = <String, String>{};
    final formParams = <String, String>{};

    const contentTypes = <String>['application/json'];


    return apiClient.invokeAPI(
      path,
      'PUT',
      queryParams,
      postBody,
      headerParams,
      formParams,
      contentTypes.isEmpty ? null : contentTypes.first,
    );
  }

  /// Parameters:
  ///
  /// * [Object] playlistId (required):
  ///
  /// * [UpdatePlaylistRequest] updatePlaylistRequest (required):
  Future<PublicPlaylistData?> updatePlaylist(Object playlistId, UpdatePlaylistRequest updatePlaylistRequest,) async {
    final response = await updatePlaylistWithHttpInfo(playlistId, updatePlaylistRequest,);
    if (response.statusCode >= HttpStatus.badRequest) {
      throw ApiException(response.statusCode, await _decodeBodyBytes(response));
    }
    // When a remote server returns no body with a status of 204, we shall not decode it.
    // At the time of writing this, `dart:convert` will throw an "Unexpected end of input"
    // FormatException when trying to decode an empty string.
    if (response.body.isNotEmpty && response.statusCode != HttpStatus.noContent) {
      return await apiClient.deserializeAsync(await _decodeBodyBytes(response), 'PublicPlaylistData',) as PublicPlaylistData;
    
    }
    return null;
  }
}
