package com.company.local_name.DAO;

import java.sql.Connection;

import com.company.local_name.DAO.impl.PgNomeDAO;
import com.company.local_name.DAO.impl.PgRegiaoDAO;
import com.company.local_name.DAO.impl.PgEstadoDAO;
import com.company.local_name.DAO.impl.PgCidadeDAO;

public class PgDAOFactory extends DAOFactory {

    public PgDAOFactory(Connection connection) {
        this.connection = connection;
    }

    @Override
    public CidadeDAO getCidadeDAO() {
        return new PgCidadeDAO(connection);
    }

    @Override
    public EstadoDAO getEstadoDAO() {
        return new PgEstadoDAO(connection);
    }

    @Override
    public RegiaoDAO getRegiaoDAO() {
        return new PgRegiaoDAO(connection);
    }

    @Override
    public NomeDAO getNomeDAO() {
        return new PgNomeDAO(connection);
    }

}