package com.crud.CrudApp;

import com.crud.CrudApp.User.User;
import com.crud.CrudApp.User.UserDetailsServiceImpl;
import com.crud.CrudApp.User.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.mockito.Mockito.when;

public class UserDetailsServiceImplTest {
//    Should be used if any dependency needs to be autowired. If using add @SpringBootTest annotation to this class.
//    @Autowired
//    private UserDetailsServiceImpl userDetailsService;
//
//    @MockBean
//    private UserRepository userRepository;

//    Alternate way
    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "zzz"
    })
    public void loadUserByUsernameTest(String username) {
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName(username).password("1234").roles(List.of("USER")).build());
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        Assertions.assertNotNull(userDetails);
    }
}
