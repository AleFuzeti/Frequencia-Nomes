package com.company.local_name.DAO.impl;

import com.company.local_name.DAO.NomeDAO;
import com.company.local_name.model.Nome;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PgNomeDAO implements NomeDAO{
    private Connection connection;

    public PgNomeDAO(Connection connection) {
        this.connection = connection;
    }

    private static final String CREATE_NOME   = "INSERT INTO local_names_db.nome(cidade, nome, frequencia, ranking)" + "VALUES (?,?,?,?)";
    private static final String GET_NOME      = "SELECT * FROM local_names_db.nome WHERE cidade = ? AND nome = ?";
    private static final String UPDATE_NOME   = "UPDATE * FROM local_names_db.nome SET frequencia=?, ranking=? WHERE nome = ? AND cidade = ?";
    private static final String DELETE_NOME   = "DELETE * FROM local_names_db.nome  WHERE nome = ? AND cidade = ?";


    @Override
    public void create(Nome object) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_NOME)) {

            statement.setString(1, object.getCidade());
            statement.setInt(2, object.getFrequencia());
            statement.setString(3, object.getNome());
            statement.setInt(4, object.getRank());

            statement.execute();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro ao criar nome");
        }
    }

    //@Override
    public Nome get(Object key) throws SQLException {

        Nome nome = null;
        try (PreparedStatement statement = connection.prepareStatement(GET_NOME)) {
            statement.setString(1, ((Nome) key).getNome());
            statement.setString(2, ((Nome) key).getCidade());
            statement.executeQuery();

            while (statement.getResultSet().next()) {
                nome = new Nome(statement.getResultSet().getString("cidade"), statement.getResultSet().getString("nome"), statement.getResultSet().getInt("frequencia"), statement.getResultSet().getInt("ranking"));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro na busca de nome");
        }

        return nome;
    }

    @Override
    public void update(Nome object) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_NOME)) {
            statement.setString(1, object.getCidade());
            statement.setString(2, object.getNome());
            statement.setInt(3, object.getFrequencia());
            statement.setInt(4, object.getRank());

            statement.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro ao atualizar nome");
        }
    }

    //@Override
    public void delete(Object key) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_NOME)) {
            statement.setString(1, ((Nome) key).getCidade());
            statement.setString(1, ((Nome) key).getNome());
            statement.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro ao deletar nome");
        }
    } 
}