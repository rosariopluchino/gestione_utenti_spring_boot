package gestione.utenti.gestione.utenti.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity //serve per dire che questa è una tabella e non una normale classe
public class UserModel {
    
    @Id //senza Id Hibernate non sa a chi assegnare la chiave primaria
    @GeneratedValue //genera automaticamente l'id
    private Long id;
    private String name;
    private String email;

    public UserModel() {} //senza costruttore vuoto JPA non riesce a costruire gli oggetti, quindi serve il costruttore affinche
                          //vengano create le istanze (nuovo utente) altrimenti dovrei crearle io ogni volta con new

    public UserModel(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
