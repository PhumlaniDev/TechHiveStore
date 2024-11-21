package com.phumlanidev.techhivestore.auth;

import com.phumlanidev.techhivestore.dto.LoginDto;
import com.phumlanidev.techhivestore.dto.UserDto;
import com.phumlanidev.techhivestore.mapper.AddressMapper;
import com.phumlanidev.techhivestore.mapper.UserMapper;
import com.phumlanidev.techhivestore.model.Address;
import com.phumlanidev.techhivestore.model.User;
import com.phumlanidev.techhivestore.repository.AddressRepository;
import com.phumlanidev.techhivestore.repository.UserRepository;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    private AuthService authService;
    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private PasswordEncoder passwordEncoder;
    private Keycloak keycloak;
    private UserMapper userMapper;
    private AddressMapper addressMapper;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        addressRepository = mock(AddressRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        keycloak = mock(Keycloak.class);
        userMapper = mock(UserMapper.class);
        addressMapper = mock(AddressMapper.class);
        authService = new AuthService(userRepository, addressRepository, passwordEncoder, keycloak, userMapper, addressMapper);
    }

    @Test
    void registerUserSuccessfully() {
        UserDto userDto = new UserDto();
        userDto.setPassword("password");
        User user = new User();
        Address address = new Address();
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userMapper.toEntity(any(UserDto.class), any(User.class))).thenReturn(user);
        when(addressMapper.toEntity(any(), any())).thenReturn(address);
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        when(userRepository.save(any(User.class))).thenReturn(user);

        authService.registerUser(userDto);

        verify(userRepository).save(user);
        verify(addressRepository).save(address);
    }

    @Test
    void registerUserKeycloakFailure() {
        UserDto userDto = new UserDto();
        userDto.setPassword("password");
        User user = new User();
        Address address = new Address();
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userMapper.toEntity(any(UserDto.class), any(User.class))).thenReturn(user);
        when(addressMapper.toEntity(any(), any())).thenReturn(address);
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        when(userRepository.save(any(User.class))).thenReturn(user);
        RealmResource realmResource = mock(RealmResource.class);
        UsersResource usersResource = mock(UsersResource.class);
        when(keycloak.realm(anyString())).thenReturn(realmResource);
        when(realmResource.users()).thenReturn(usersResource);
        when(usersResource.create(any(UserRepresentation.class))).thenReturn(Response.serverError().build());

        assertThrows(RuntimeException.class, () -> authService.registerUser(userDto));
    }

    @Test
    void loginSuccessfully() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("username");
        loginDto.setPassword("password");
        Keycloak keycloakMock = mock(Keycloak.class);
        when(KeycloakBuilder.builder()
                .serverUrl(anyString())
                .realm(anyString())
                .clientId(anyString())
                .clientSecret(anyString())
                .grantType(anyString())
                .username(anyString())
                .password(anyString())
                .build()).thenReturn(keycloakMock);
        when(keycloakMock.tokenManager().getAccessToken().getToken()).thenReturn("token");

        String token = authService.login(loginDto);

        assertEquals("token", token);
    }

    @Test
    void loginFailure() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("username");
        loginDto.setPassword("password");
        when(KeycloakBuilder.builder()
                .serverUrl(anyString())
                .realm(anyString())
                .clientId(anyString())
                .clientSecret(anyString())
                .grantType(anyString())
                .username(anyString())
                .password(anyString())
                .build()).thenThrow(new RuntimeException("Invalid username or password"));

        assertThrows(RuntimeException.class, () -> authService.login(loginDto));
    }

}