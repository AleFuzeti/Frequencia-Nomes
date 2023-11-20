package main.java.DAO;

import java.sql.Connection;

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