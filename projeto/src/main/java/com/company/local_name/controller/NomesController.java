package com.company.local_name.controller;

//import com.company.local_name.DAO.impl.PgNomeDAO;
import com.company.local_name.model.Nome;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Optional;

@RestController
@RequestMapping("/nomes")
public class NomesController {

    //private final PgNomeDAO nomeDAO;

    //@Autowired
//    public NomesController(PgNomeDAO nomeDAO) {
//        this.nomeDAO = nomeDAO;
//    }

    //@PostMapping
//    public ResponseEntity<String> createNome(@RequestBody Nome nome) {
//        try {
//            Optional<Nome> existingNome = nomeDAO.findById(nome.getId());
//
//            if (existingNome.isPresent()) {
//                // O nome já existe, você pode decidir o que fazer aqui, como atualizar ou ignorar
//                return ResponseEntity.status(HttpStatus.OK).body("Nome already exists");
//            } else {
//                // O nome não existe, crie-o
//                nomeDAO.create(nome);
//                return ResponseEntity.status(HttpStatus.CREATED).body("Nome created successfully");
//            }
//        } catch (SQLException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create Nome");
//        }
//    }
}
