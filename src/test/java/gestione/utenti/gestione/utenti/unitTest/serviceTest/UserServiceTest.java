package gestione.utenti.gestione.utenti.unitTest.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import gestione.utenti.gestione.utenti.exception.NameAlreadyExist;
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

        //verifico che i metodi del repository vengano chiamati
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

    @Test
    public void getUserByIdSuccess() {
        UserModel user1 = new UserModel("rosario", "ro@hotmail.it");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        Optional<UserModel> risultato = userService.getUserById(1L);

        assertEquals(user1, risultato.get());
        assertTrue(risultato.isPresent());
        verify(userRepository).findById(1L);
    }

    @Test
    public void getUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<UserModel> result = userService.getUserById(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository).findById(1L);
    }

    @Test
    public void createUserSuccess() {
        UserModel user1 = new UserModel("rosario", "ro@hotmail.it");

        //simulo che il nome non esiste già sul db e successivamente lo salvo
        when(userRepository.findByName("rosario")).thenReturn(Optional.empty());
        when(userRepository.save(user1)).thenReturn(user1);
        
        UserModel result = userService.createUser(user1);

        assertEquals(user1, result);
        verify(userRepository).save(user1); 
    }

    @Test
    public void createUserFailed_NameAlreadyExist() {
        UserModel user1 = new UserModel("rosario", "ro@hotmail.it");

        when(userRepository.findByName("rosario")).thenReturn(Optional.of(user1));

        Exception exception = assertThrows(NameAlreadyExist.class, () -> {
            userService.createUser(user1);
        });

        assertEquals("Il nome rosario esiste già", exception.getMessage());
        verify(userRepository, never()).save(any(UserModel.class));
    }

    @Test
    public void deleteUserSuccess() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(true);

        userService.deleteUser(userId);
        
        verify(userRepository).deleteById(userId);
    }

    @Test
    public void deleteUserFailed() {
        Long user1 = 1L;
        
        Exception exception = assertThrows(IllegalArgumentException.class, () ->  {
            userService.deleteUser(user1);
        });

        assertEquals("Utente con id: 1 non trovato", exception.getMessage());
        verify(userRepository, never()).deleteById(user1);
    }

    @Test
    public void updateUserByIdSuccess() {
        //nuovi valori da aggiornare
        UserModel user = new UserModel("aldo", "aldo@outlook.com");
        
        //utente gia esistente nel db
        UserModel existigUser = new UserModel("rosario", "rosario@outlook.com");
        existigUser.setId(1L);
        
        //mock comportamenti repository
        when(userRepository.findById(1L)).thenReturn(Optional.of(existigUser));
        when(userRepository.save(existigUser)).thenReturn(existigUser);

        //chiamata al metodo che devo testare
        UserModel updateUserById = userService.updateUserById(1L, user);
        
        //verifico
        assertEquals("aldo", updateUserById.getName());
        assertEquals("aldo@outlook.com", updateUserById.getEmail());
        verify(userRepository).save(existigUser);
    }

    @Test
    public void updateUserByIdFailed() {
        UserModel user = new UserModel("rosario", "rosario@outlook.com");

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUserById(1L, user);
        });

        assertEquals("utente con id: 1 non trovato", exception.getMessage());
        verify(userRepository).findById(1L);
        verify(userRepository, never()).save(any(UserModel.class));
    }
}
