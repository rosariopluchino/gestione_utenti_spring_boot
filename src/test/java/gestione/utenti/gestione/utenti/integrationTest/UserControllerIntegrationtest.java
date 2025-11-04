package gestione.utenti.gestione.utenti.integrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;

import gestione.utenti.gestione.utenti.model.UserModel;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//import java.util.Arrays;

@ActiveProfiles("test")
@SpringBootTest
public class UserControllerIntegrationtest extends BaseIntegrationTest{
    /*@Autowired
    private org.springframework.core.env.Environment env;
    @Test
    void testPrintDbUrl() {
        System.out.println("✅ Active profiles: " + Arrays.toString(env.getActiveProfiles()));
        System.out.println(" Database URL: " + env.getProperty("spring.datasource.url"));
    
    }*/
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreaUtente() throws Exception{
        UserModel user = new UserModel();
        user.setName("rosario");
        user.setEmail("rosario@outlook.com");

        mockMvc.perform(post("/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isCreated());
    }
}
