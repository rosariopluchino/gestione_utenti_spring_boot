package gestione.utenti.gestione.utenti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gestione.utenti.gestione.utenti.model.UserModel;

//GRAZIE A QUESTA CLASSE POSSO LEGGERE E SCRIVERE SUL DB SENZA SCRIVERE QUERY MANUALI
public interface UserRepository extends JpaRepository<UserModel, Long> {
    
}
