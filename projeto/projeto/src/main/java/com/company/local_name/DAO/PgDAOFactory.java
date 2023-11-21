package com.company.local_name.DAO;

import java.sql.Connection;

import main.java.DAO.impl.PgCidadeDAO;
import main.java.DAO.impl.PgEstadoDAO;
import main.java.DAO.impl.PgNomeDAO;
import main.java.DAO.impl.PgRegiaoDAO;

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
    public RegiaoDAO RegiaoDAO() {
        return new PgRegiaoDAO(connection);
    }

    @Override
    public NomeDAO NomeDAO() {
        return new PgNomeDAO(connection);
    }

}