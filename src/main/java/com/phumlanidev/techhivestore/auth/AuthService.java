package com.phumlanidev.techhivestore.auth;


import com.phumlanidev.techhivestore.dto.LoginDto;
import com.phumlanidev.techhivestore.dto.UserDto;
import com.phumlanidev.techhivestore.mapper.AddressMapper;
import com.phumlanidev.techhivestore.mapper.UserMapper;
import com.phumlanidev.techhivestore.model.Address;
import com.phumlanidev.techhivestore.model.Users;
import com.phumlanidev.techhivestore.repository.AddressRepository;
import com.phumlanidev.techhivestore.repository.UsersRepository;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
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

import java.net.URI;

/**
 * <p> comment </p>.
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersRepository usersRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final Keycloak keycloak;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;

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

    public void registerUser(UserDto userDto) {
      // Step 1: Hash the password
      userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

      // Step 2: Map DTO to Entity
      Users users = userMapper.toEntity(userDto, new Users());
      Address address = addressMapper.toEntity(userDto.getAddress(), new Address());

      // Step 3: Save Address and Users in PostgreSQL
      Address savedAddress = addressRepository.save(address);
      users.setAddress(savedAddress);
      usersRepository.save(users);

      // Step 4: Register the user in Keycloak
      registerKeycloakUser(userDto);

    }

    private void registerKeycloakUser(UserDto userDto) {
      try {
        UsersResource usersResource = keycloak.realm(keycloakRealm).users();

        // Create a new Keycloak user
        UserRepresentation keycloakUser = createUserRepresentation(userDto);
        keycloakUser.setUsername(userDto.getUsername());
        keycloakUser.setEmail(userDto.getEmail());
        keycloakUser.setFirstName(userDto.getFirstName());
        keycloakUser.setLastName(userDto.getLastName());
        keycloakUser.setEnabled(true);

        // Create the Keycloak user
        Response response = usersResource.create(keycloakUser);

        // Check if user creation was successful
        try {
          if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            log.info("Keycloak user created successfully for username: {}", userDto.getUsername());

            // Step 5: Set user credentials (password)
            String userId = extractUserId(response);
            setPasswordForUser(usersResource, userId, userDto.getPassword());

          } else {
            log.error("Failed to create Keycloak user: {}", response.getStatusInfo().toString());
            throw new RuntimeException("Keycloak user creation failed");
          }
        } catch (NotAuthorizedException e) {
          log.error("Authorization failed during user creation: {}", e.getMessage());
          // Handle the 401 Unauthorized error here
        }
      } catch (Exception e) {
      log.error("Exception occurred while creating user {} in Keycloak: {}", userDto.getUsername(), e.getMessage(), e);
    }

    }


    private String getUserIdFromLocation(URI location) {
      String path = location.getPath();
      return path.substring(path.lastIndexOf('/') + 1);
    }

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
    String locationPath = response.getLocation().getPath();
    return locationPath.substring(locationPath.lastIndexOf('/') + 1);
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
