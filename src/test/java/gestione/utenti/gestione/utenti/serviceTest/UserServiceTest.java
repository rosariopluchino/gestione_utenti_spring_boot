package gestione.utenti.gestione.utenti.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import gestione.utenti.gestione.utenti.model.UserModel;
import gestione.utenti.gestione.utenti.repository.UserRepository;
import gestione.utenti.gestione.utenti.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        userService = new UserService(userRepository);
    }
    
    @Test
    public void getAllUsersSuccess() {
        UserModel user1 = new UserModel("rosario", "ro@hotmail.it");
        UserModel user2 = new UserModel("melissa", "me@live.it");
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<UserModel> result = userService.getAllUsers();

        assertEquals(2, result.size());
        assertTrue(result.contains(user1));
        assertTrue(result.contains(user2));
        verify(userRepository).findAll();
    }

    @Test
    public void getAllUsersEmpty(){
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        //chiamo il service
        List<UserModel> result = userService.getAllUsers();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository).findAll();
    }
}
