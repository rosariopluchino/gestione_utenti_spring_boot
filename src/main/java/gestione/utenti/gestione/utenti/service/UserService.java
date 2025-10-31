package gestione.utenti.gestione.utenti.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import gestione.utenti.gestione.utenti.exception.NameAlreadyExist;
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
        if(userRepository.findByName(user.getName()).isPresent()) {
            throw new NameAlreadyExist("Il nome " + user.getName() + " esiste già");
        }
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Utente con id: " + id + " non trovato");
        }
        userRepository.deleteById(id);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
