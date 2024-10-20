package com.phumlanidev.techhivestore.auth;


import com.phumlanidev.techhivestore.dto.LoginDto;
import com.phumlanidev.techhivestore.dto.UserDto;
import com.phumlanidev.techhivestore.mapper.UserMapper;
import com.phumlanidev.techhivestore.repository.AddressRepository;
import com.phumlanidev.techhivestore.repository.UsersRepository;

import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p> comment </p>.
 */

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {

  private final UsersRepository usersRepository;
  private final AddressRepository addressRepository;
  private final PasswordEncoder passwordEncoder;
  private final Keycloak keycloak;
  private final UserMapper userMapper;

  @Value("${keycloak.auth-server-url}")
  private String keycloakServerUrl;
  @Value("${keycloak.realm}")
  private String keycloakRealm;
  @Value("${keycloak.resource}")
  private String keycloakClientId;
  @Value("${keycloak.credentials.secret}")
  private String keycloakClientSecret;

  private static final String ENABLED_ATTRIBUTE = "enabled";
  private static final String TRUE_VALUE = "true";


  /**
   * <p> comment </p>.
   */
//  public UserDto registerUser(UserDto userDTO) {
//    AddressDto addressDTO = userDTO.getAddress(); // Retrieve the AddressDTO from UserDTO
//
//    if (addressDTO == null) {
//      throw new IllegalArgumentException("AddressDTO cannot be null");
//    }
//
//    Address address = new Address();
//
//    address.setCity(addressDTO.getCity());
//    address.setCountry(addressDTO.getCountry());
//    address.setProvince(addressDTO.getProvince());
//    address.setZipCode(addressDTO.getZipCode());
//    addressRepository.save(address);
//
//    Users user = userMapper.toEntity(userDTO, new Users());
//    user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
////    user.setRoles(Collections.singletonList("ROLE_USER"));
////    user.setAddress(address);
//
//    usersRepository.save(user);
//
//    log.info("User {} saved to database", user.getUsername());
//    registerUserInKeycloak(userDTO);
//    addressRepository.save(address);
//
//
//    return new UserDto(user);
//  }
//
//  /**
//   * <p> comment </p>.
//   */
//  void registerUserInKeycloak(UserDto userDTO) {
//    try {
//      // Get the token for debugging
//      String token = keycloak.tokenManager().getAccessTokenString();
//      log.debug("Using Keycloak token: {}", token);
//
//      RealmResource realmResource = keycloak.realm(keycloakRealm);
//      UsersResource usersResource = realmResource.users();
//
//      UserRepresentation userRepresentation = createUserRepresentation(userDTO);
//
//      try (Response response = usersResource.create(userRepresentation)) {
//        log.debug("Response from Keycloak: {}", response.getStatusInfo());
//
//        if (response.getStatus() == 201) {
//          String userId = extractUserId(response);
//          log.info("User {} created in Keycloak with ID {}", userDTO.getUsername(), userId);
//          setPasswordForUser(usersResource, userId, userDTO.getPassword());
//        } else {
//          log.error("Failed to create user {} in Keycloak: {}", userDTO.getUsername(), response.getStatusInfo().toString());
//          throw new RuntimeException("Failed to create user in Keycloak: " + response.getStatus());
//        }
//      } catch (NotAuthorizedException e) {
//        log.error("Authorization failed during user creation: {}", e.getMessage());
//        // Handle the 401 Unauthorized error here
//      }
//    } catch (Exception e) {
//      log.error("Exception occurred while creating user {} in Keycloak: {}", userDTO.getUsername(), e.getMessage(), e);
//    }
//  }

  private UserRepresentation createUserRepresentation(UserDto userDTO) {
    UserRepresentation userRepresentation = new UserRepresentation();
    userRepresentation.setUsername(userDTO.getUsername());
    userRepresentation.setEmail(userDTO.getEmail());
    userRepresentation.setFirstName(userDTO.getFirstName());
    userRepresentation.setLastName(userDTO.getLastName());
    userRepresentation.singleAttribute(ENABLED_ATTRIBUTE, TRUE_VALUE);
    userRepresentation.setEnabled(true);
    return userRepresentation;
  }

  private String extractUserId(Response response) {
    // Assuming that the response contains the user ID in its location header or similar
    return response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
  }

  private void setPasswordForUser(UsersResource usersResource, String userId, String password) {
    CredentialRepresentation credential = new CredentialRepresentation();
    credential.setTemporary(false);
    credential.setType(CredentialRepresentation.PASSWORD);
    credential.setValue(password);
    usersResource.get(userId).resetPassword(credential);
    log.info("Password set for user ID {} in Keycloak", userId);
  }

  public String login(LoginDto loginDto) {
    try (Keycloak keycloak = KeycloakBuilder.builder()
          .serverUrl(keycloakServerUrl)
          .realm(keycloakRealm)
          .clientId(keycloakClientId)
          .clientSecret(keycloakClientSecret)
          .username(loginDto.getUsername())
          .password(loginDto.getPassword())
          .grantType(OAuth2Constants.PASSWORD)
          .build()){

      AccessTokenResponse accessTokenResponse = keycloak.tokenManager().getAccessToken();
      return accessTokenResponse.getIdToken();
    } catch (Exception e) {
      log.error("Exception occurred while logging in user {}: {}",
          loginDto.getUsername(), e.getMessage(), e);
    }
    throw new RuntimeException("Invalid username or password");
  }
}
