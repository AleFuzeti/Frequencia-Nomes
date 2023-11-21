package main.java.DAO.impl;

import main.java.DAO.RegiaoDAO;
import main.java.model.Estado;
import main.java.model.Regiao;

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
            throw new SQLException("Erro ao criar estado");
        }
    }

    @Override
    public Estado get(Object key) throws SQLException {

        Estado estado = null;
        try (PreparedStatement statement = connection.prepareStatement(GET_REGIAO)) {
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