package com.phumlanidev.techhivestore.service.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.phumlanidev.techhivestore.dto.LoginDto;
import com.phumlanidev.techhivestore.dto.UserDto;
import com.phumlanidev.techhivestore.enums.RoleMapping;
import com.phumlanidev.techhivestore.exception.auth.AuthenticationFailedException;
import com.phumlanidev.techhivestore.mapper.AddressMapper;
import com.phumlanidev.techhivestore.mapper.UserMapper;
import com.phumlanidev.techhivestore.model.Address;
import com.phumlanidev.techhivestore.model.User;
import com.phumlanidev.techhivestore.repository.AddressRepository;
import com.phumlanidev.techhivestore.repository.UserRepository;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

class AuthServiceImplTest {

  @InjectMocks
  private AuthServiceImpl authService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private AddressRepository addressRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private Keycloak keycloak;

  @Mock
  private UserMapper userMapper;

  @Mock
  private AddressMapper addressMapper;

  @Mock
  private RealmResource realmResource;

  @Mock
  private UsersResource usersResource;

  @BeforeEach
  void setUp() {

    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldRegisterSuccessfully() {
    // Given
    UserDto userDto = new UserDto();
    userDto.setUsername("testuser");
    userDto.setPassword("rawpassword");
    userDto.setRole(RoleMapping.USER);

    User user = new User();
    Address address = new Address();

    // Mock Mappers
    when(userMapper.toEntity(any(UserDto.class), any(User.class))).thenReturn(user);
    when(addressMapper.toEntity(any(), any())).thenReturn(address);

    // Mock DB Repositories
    when(addressRepository.save(any())).thenReturn(address);
    when(userRepository.save(any())).thenReturn(user);

    // Mock Password Encoding
    when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

    // Mock Keycloak
    when(keycloak.realm(any())).thenReturn(realmResource);
    when(realmResource.users()).thenReturn(usersResource);
    Response response = mock(Response.class);
    when(usersResource.create(any(UserRepresentation.class))).thenReturn(response);
    when(response.getStatus()).thenReturn(Response.Status.CREATED.getStatusCode());
    when(response.getLocation()).thenReturn(URI.create("/users/12345"));

    // When
    authService.registerUser(userDto);

    // Then
    verify(userRepository).save(any());
    verify(addressRepository).save(any());
    verify(usersResource).create(any(UserRepresentation.class));
  }

  // ===== Test Successful Login =====
  @Test
  void shouldLoginSuccessfully() {
    LoginDto loginDto = new LoginDto();
    loginDto.setUsername("testuser");
    loginDto.setPassword("password");

    // Mock AccessTokenResponse
    AccessTokenResponse tokenResponse = mock(AccessTokenResponse.class);
    when(tokenResponse.getToken()).thenReturn("mocked-jwt-token");

    // Mock TokenManager
    TokenManager tokenManager = mock(TokenManager.class);
    when(tokenManager.getAccessToken()).thenReturn(tokenResponse);

    // Mock Keycloak client
    Keycloak mockedKeycloak = mock(Keycloak.class);
    when(mockedKeycloak.tokenManager()).thenReturn(tokenManager);

    // Mock static KeycloakBuilder
    try (MockedStatic<KeycloakBuilder> keycloakBuilderMock = mockStatic(KeycloakBuilder.class)) {
      KeycloakBuilder builder = mock(KeycloakBuilder.class);

      when(KeycloakBuilder.builder()).thenReturn(builder);
      when(builder.serverUrl(any())).thenReturn(builder);
      when(builder.realm(any())).thenReturn(builder);
      when(builder.clientId(any())).thenReturn(builder);
      when(builder.clientSecret(any())).thenReturn(builder);
      when(builder.grantType(any())).thenReturn(builder);
      when(builder.username(any())).thenReturn(builder);
      when(builder.password(any())).thenReturn(builder);
      when(builder.build()).thenReturn(mockedKeycloak);

      // When
      String token = authService.login(loginDto);

      // Then
      assertThat(token).isEqualTo("mocked-jwt-token");
    }
  }


  // ===== Test Login Failure =====
  @Test
  void shouldThrowExceptionWhenLoginFails() {
    LoginDto loginDto = new LoginDto();
    loginDto.setUsername("wronguser");
    loginDto.setPassword("wrongpass");

    // Mock Keycloak throwing exception
    try (MockedStatic<KeycloakBuilder> keycloakBuilderMock = mockStatic(KeycloakBuilder.class)) {
      KeycloakBuilder builder = mock(KeycloakBuilder.class);
      when(KeycloakBuilder.builder()).thenReturn(builder);
      when(builder.serverUrl(any())).thenReturn(builder);
      when(builder.realm(any())).thenReturn(builder);
      when(builder.clientId(any())).thenReturn(builder);
      when(builder.clientSecret(any())).thenReturn(builder);
      when(builder.grantType(any())).thenReturn(builder);
      when(builder.username(any())).thenReturn(builder);
      when(builder.password(any())).thenReturn(builder);
      when(builder.build()).thenThrow(new RuntimeException("Invalid login"));

      // Then
      assertThatThrownBy(() -> authService.login(loginDto)).isInstanceOf(
          AuthenticationFailedException.class).hasMessageContaining("Invalid username or password");
    }
  }
}