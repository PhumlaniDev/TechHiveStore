package com.phumlanidev.techhivestore.service;


import com.phumlanidev.techhivestore.dto.LoginDto;
import com.phumlanidev.techhivestore.dto.UserDTO;
import com.phumlanidev.techhivestore.model.Users;
import com.phumlanidev.techhivestore.repository.UserRepository;
import java.util.Collections;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
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
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final Keycloak keycloak;

  @Value("${keycloak.auth-server-url}")
  private String keycloakServerUrl;
  @Value("${keycloak.realm}")
  private String keycloakRealm;
  @Value("${keycloak.resource}")
  private String keycloakClientId;
  @Value("${keycloak.credentials.secret}")
  private String keycloakClientSecret;

  public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, Keycloak keycloak) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.keycloak = keycloak;
  }


  /**
   * <p> comment </p>.
   */
  public UserDTO registerUser(UserDTO userDTO) {
    Users user = new Users();
    user.setUsername(userDTO.getUsername());
    user.setEmail(userDTO.getEmail());
    user.setFirstName(userDTO.getFirstName());
    user.setLastName(userDTO.getLastName());
    user.setPhoneNumber(userDTO.getPhoneNumber());
    user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    user.setRoles(Collections.singletonList("ROLE_USER"));
    userRepository.save(user);
    log.info("User {} saved to database", user.getUsername());
    registerUserInKeycloak(userDTO);
    return new UserDTO(user);
  }

  /**
   * <p> comment </p>.
   */
  private void registerUserInKeycloak(UserDTO userDTO) {
    try {

      RealmResource realmResource = keycloak.realm(keycloakRealm);
      UsersResource usersResource = realmResource.users();

      UserRepresentation userRepresentation = new UserRepresentation();
      userRepresentation.setUsername(userDTO.getUsername());
      userRepresentation.setEmail(userDTO.getEmail());
      userRepresentation.setFirstName(userDTO.getFirstName());
      userRepresentation.setLastName(userDTO.getLastName());
      userRepresentation.singleAttribute("enabled", "true");
      userRepresentation.setEnabled(true);

      Response response = usersResource.create(userRepresentation);
      log.debug("Response from Keycloak: {}", response.getStatusInfo());

      if (response.getStatus() == 201) {
        String userId = usersResource.create(userRepresentation).toString();
        log.info("User {} created in Keycloak with ID {}", userDTO.getUsername(), userId);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(userDTO.getPassword());

        usersResource.get(userId).resetPassword(credential);
        log.info("Password set for user {} in Keycloak", userDTO.getUsername());
      } else {
        // Log the error response
        log.error("Failed to create user {} in Keycloak: {}", userDTO.getUsername(),
            response.getStatusInfo().toString());
      }
    } catch (Exception e) {
      // Log the exception
      log.error("Exception occurred while creating user {} in Keycloak: {}",
          userDTO.getUsername(), e.getMessage(), e);
    }
  }

  public String login(LoginDto loginDto) {
    try {

      Keycloak keycloak = KeycloakBuilder.builder()
          .serverUrl(keycloakServerUrl)
          .realm(keycloakRealm)
          .clientId(keycloakClientId)
          .clientSecret(keycloakClientSecret)
          .username(loginDto.getUsername())
          .password(loginDto.getPassword())
          .grantType(OAuth2Constants.PASSWORD)
          .build();

      AccessTokenResponse accessTokenResponse = keycloak.tokenManager().getAccessToken();
      return accessTokenResponse.getIdToken();
    } catch (Exception e) {
      log.error("Exception occurred while logging in user {}: {}",
          loginDto.getUsername(), e.getMessage(), e);
    }
    throw new RuntimeException("Invalid username or password");
  }
}
