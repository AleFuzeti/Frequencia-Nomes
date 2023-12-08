package com.company.local_name.DAO;

import com.company.local_name.model.Nome;

import java.sql.SQLException;
import java.util.Optional;

public interface NomeDAO extends DAO<Nome>{
    Optional<Nome> findById(String cidade) throws SQLException;
}
