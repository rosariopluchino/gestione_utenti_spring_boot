package gestione.utenti.gestione.utenti.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import gestione.utenti.gestione.utenti.model.UserModel;
import gestione.utenti.gestione.utenti.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    //non è necessario mettere @Autowired perche spring dalla v.4.3 capisce che deve iniettare UserRepository perche c'è un
    //un solo costruttore, @Autowired si mette solo se ci sono piu costruttori cosi indichi cosa vuoi iniettare
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserModel> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public UserModel createUser(UserModel user) {
        return userRepository.save(user);
    }

    public void deleteUser(UserModel user) {
        userRepository.delete(user);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
