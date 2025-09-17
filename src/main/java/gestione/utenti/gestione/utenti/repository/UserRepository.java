package gestione.utenti.gestione.utenti.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gestione.utenti.gestione.utenti.model.UserModel;

//GRAZIE A QUESTA CLASSE POSSO LEGGERE E SCRIVERE SUL DB SENZA SCRIVERE QUERY MANUALI
public interface UserRepository extends JpaRepository<UserModel, Long> {
    public Optional<UserModel> findByName(String name);
}
