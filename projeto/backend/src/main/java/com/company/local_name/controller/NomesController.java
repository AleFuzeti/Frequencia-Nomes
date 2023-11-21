import com.company.local_name.DAO.impl.PgNomeDAO;
import com.company.local_name.model.Nome;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nomes")
public class NomesController {

    private final PgNomeDAO nomeDAO;

    @Autowired
    public NomesController(PgNomeDAO nomeDAO) {
        this.nomeDAO = nomeDAO;
    }

    @PostMapping
    public ResponseEntity<String> createNome(@RequestBody Nome nome) {
        try {
            nomeDAO.create(nome);
            return ResponseEntity.status(HttpStatus.CREATED).body("Nome created successfully");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create Nome");
        }
    }

    

}
