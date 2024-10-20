package com.phumlanidev.techhivestore.service;

import com.phumlanidev.techhivestore.auth.AuthService;
import com.phumlanidev.techhivestore.dto.UserDto;
import com.phumlanidev.techhivestore.mapper.AddressMapper;
import com.phumlanidev.techhivestore.mapper.UserMapper;
import com.phumlanidev.techhivestore.repository.AddressRepository;
import com.phumlanidev.techhivestore.repository.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;

@ActiveProfiles("test")
class AuthServiceTest {

    @Mock
    private UsersRepository usersRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private AddressMapper addressMapper;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private AuthService authService;
    @Mock
    private AutoCloseable autoCloseable;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private Keycloak keycloak;




    @BeforeEach
    void setUp() {
        autoCloseable = openMocks(this);
//        authService = new AuthService(usersRepository, addressRepository, passwordEncoder, keycloak);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

//    @Test
//    void registerUserSuccessfully() {
//        UserDTO userDTO = getUserDTO();
//
//        Users user = new Users();
//        user.setUserId(1L);
//        user.setUsername("testuser");
//        user.setEmail("testuser@example.com");
//        user.setPhoneNumber("1234567890");
//        user.setFirstName("Test");
//        user.setLastName("User");
//        user.setPassword("password");
//        user.setAddressId(addressMapper.toEntity(userDTO.getAddress()));
//        when(usersRepository.save(any(Users.class))).thenReturn(user);
//        when(addressRepository.save(any(Address.class))).thenReturn(new Address());
//        doNothing().when(authService).registerUserInKeycloak(any(UserDTO.class));
//
//        UserDTO result = authService.registerUser(userDTO);
//
//        assertNotNull(result);
//        verify(usersRepository, times(1)).save(any(Users.class));
//        verify(addressRepository, times(1)).save(any(Address.class));
//        verify(authService, times(1)).registerUserInKeycloak(any(UserDTO.class));
//    }
//
//    private static UserDTO getUserDTO() {
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUserId(1L);
//        userDTO.setUsername("testuser");
//        userDTO.setEmail("testuser@example.com");
//        userDTO.setPhoneNumber("1234567890");
//        userDTO.setFirstName("Test");
//        userDTO.setLastName("User");
//        userDTO.setPassword("password");
//
//        AddressDTO addressDTO = new AddressDTO();
//        addressDTO.setAddressId(1L);
//        addressDTO.setCity("City");
//        addressDTO.setStreetName("Street");
//        addressDTO.setCountry("Country");
//        addressDTO.setProvince("Province");
//        addressDTO.setZipCode("12345");
//        userDTO.setAddress(addressDTO);
//        return userDTO;
//    }

//    @Test
//    void registerUserWithNullAddressThrowsException() {
//        UserDto userDTO = new UserDto();
//        userDTO.setUsername("testuser");
//        userDTO.setEmail("testuser@example.com");
//        userDTO.setFirstName("Test");
//        userDTO.setLastName("User");
//        userDTO.setPassword("password");
//        userDTO.setAddress(null);
//
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//            authService.registerUser(userDTO);
//        });
//
//        assertEquals("AddressDTO cannot be null", exception.getMessage());
//    }

//    @Test
//    void loginSuccessfully() {
//        LoginDto loginDto = new LoginDto();
//        loginDto.setUsername("testuser");
//        loginDto.setPassword("password");
//
//        Keycloak keycloakMock = mock(Keycloak.class);
//        AccessTokenResponse accessTokenResponse = new AccessTokenResponse();
//        accessTokenResponse.setIdToken("token");
//        when(keycloakMock.tokenManager().getAccessToken()).thenReturn(accessTokenResponse);
//
//        String token = authService.login(loginDto);
//
//        assertNotNull(token);
//        assertEquals("token", token);
//    }
//
//    @Test
//    void loginWithInvalidCredentialsThrowsException() {
//        LoginDto loginDto = new LoginDto();
//        loginDto.setUsername("invaliduser");
//        loginDto.setPassword("invalidpassword");
//
//        Keycloak keycloakMock = mock(Keycloak.class);
//        when(keycloakMock.tokenManager().getAccessToken()).thenThrow(new RuntimeException("Invalid username or password"));
//
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//            authService.login(loginDto);
//        });
//
//        assertEquals("Invalid username or password", exception.getMessage());
//    }
}