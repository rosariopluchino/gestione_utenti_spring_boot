package gestione.utenti.gestione.utenti.exception;

public class NameAlreadyExist extends RuntimeException{

    public NameAlreadyExist() {}

    public NameAlreadyExist(String message) {
        super(message);
    }
}
