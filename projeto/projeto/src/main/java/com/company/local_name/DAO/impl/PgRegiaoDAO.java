package com.company.local_name.DAO.impl;

import com.company.local_name.DAO.RegiaoDAO;
import com.company.local_name.model.Regiao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PgRegiaoDAO implements RegiaoDAO{
    private Connection connection;

    public PgRegiaoDAO(Connection connection) {
        this.connection = connection;
    }

    private static final String CREATE_REGIAO   = "INSERT INTO local_names_db.regiao(id, nome_regiao, sigla_regiao)" + "VALUES (?,?,?)";
    private static final String GET_REGIAO      = "SELECT * FROM local_names_db.regiao WHERE id = ? AND sigla_regiao = ?";
    private static final String GET_ALL_REGIOES = "SELECT * FROM local_names_db.regiao";
    private static final String UPDATE_REGIAO   = "UPDATE * FROM local_names_db.regiao SET id=?, nome_regiao=?, sigla_regiao=? WHERE sigla_regiao = ? AND id = ?";
    private static final String DELETE_REGIAO   = "DELETE * FROM local_names_db.regiao  WHERE sigla_regiao = ? AND id = ?";


    @Override
    public void create(Regiao object) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_REGIAO)) {

            statement.setString(1, object.getId());
            statement.setString(2, object.getSigla());
            statement.setString(3, object.getNome());

            statement.execute();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro ao criar regiao");
        }
    }

    @Override
    public Regiao get(Object key) throws SQLException {

        Regiao regiao = null;
        try (PreparedStatement statement = connection.prepareStatement(GET_REGIAO)) {
            statement.setString(1, ((Regiao) key).getId());
            statement.setString(2, ((Regiao) key).getSigla());
            statement.executeQuery();

            while (statement.getResultSet().next()) {
                regiao = new Regiao(statement.getResultSet().getString("id"), statement.getResultSet().getString("nome"), statement.getResultSet().getString("sigla"));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro na busca de regiao");
        }

        return regiao;
    }

    @Override
    public List<Regiao> getAll() throws SQLException {
        List<Regiao> regioes = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_REGIOES)) {
            statement.executeQuery();

            while (statement.getResultSet().next()) {
                regioes.add( new Regiao(statement.getResultSet().getString("id"), statement.getResultSet().getString("nome"), statement.getResultSet().getString("sigla")));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro ao listar regioes");
        }

        return regioes;
    }

    @Override
    public void update(Regiao object) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_REGIAO)) {
            statement.setString(1, object.getId());
            statement.setString(2, object.getNome());
            statement.setString(3, object.getSigla());

            statement.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro ao atualizar regiao");
        }
    }

    @Override
    public void delete(Object key) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_REGIAO)) {
            statement.setString(1, ((Regiao) key).getSigla());
            statement.setString(1, ((Regiao) key).getId());
            statement.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro ao deletar regiao");
        }
    } 
}