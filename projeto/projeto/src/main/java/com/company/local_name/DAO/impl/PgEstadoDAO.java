package com.company.local_name.DAO.impl;

import com.company.local_name.DAO.EstadoDAO;
import com.company.local_name.model.Estado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PgEstadoDAO implements EstadoDAO{

    private final Connection connection;
    public PgEstadoDAO(Connection connection) {
        this.connection = connection;
    }

    private static final String CREATE_ESTADO = "INSERT INTO local_names_db.estado(id, nome_estado, sigla_estado, sigla_regiao)" + "VALUES (?,?,?,?)";
    private static final String GET_ESTADO = "SELECT * FROM local_names_db.estado WHERE id = ? AND sigla_estado = ?";
    private static final String GET_ALL_ESTADOS = "SELECT * FROM local_names_db.estado";
    private static final String UPDATE_ESTADO = "UPDATE * FROM local_names_db.estado SET id=?, nome_estado=?, sigla_estado=? WHERE sigla_estado = ? AND id = ?";
    private static final String DELETE_ESTADO = "DELETE * FROM local_names_db.estado  WHERE sigla_estado = ? AND id = ?";

    @Override
    public void create(Estado object) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_ESTADO)) {

            statement.setString(1, object.getId());
            statement.setString(2, object.getSigla());
            statement.setString(3, object.getNome());
            statement.setString(4, object.getSigla_regiao());

            statement.execute();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro ao criar estado");
        }
    }

    @Override
    public Estado get(Object key) throws SQLException {

        Estado estado = null;
        try (PreparedStatement statement = connection.prepareStatement(GET_ESTADO)) {
            statement.setString(1, ((Estado) key).getId());
            statement.setString(2, ((Estado) key).getSigla());
            statement.executeQuery();

            while (statement.getResultSet().next()) {
                estado = new Estado(statement.getResultSet().getString("id"), statement.getResultSet().getString("nome"), statement.getResultSet().getString("sigla"), statement.getResultSet().getString("sigla_regiao"));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro na busca de estado");
        }

        return estado;
    }

    @Override
    public List<Estado> getAll() throws SQLException {
        List<Estado> estados = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_ESTADOS)) {
            statement.executeQuery();

            while (statement.getResultSet().next()) {
                estados.add( new Estado(statement.getResultSet().getString("id"), statement.getResultSet().getString("nome"), statement.getResultSet().getString("sigla"), statement.getResultSet().getString("sigla_regiao")));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro ao listar estados");
        }

        return estados;
    }

    @Override
    public void update(Estado object) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ESTADO)) {
            statement.setString(1, object.getId());
            statement.setString(2, object.getNome());
            statement.setString(3, object.getSigla());
            statement.setString(4, object.getSigla_regiao());

            statement.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro ao atualizar estado");
        }
    }

    @Override
    public void delete(Object key) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ESTADO)) {
            statement.setString(1, ((Estado) key).getSigla());
            statement.setString(1, ((Estado) key).getId());
            statement.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro ao deletar estado");
        }
    } 
}