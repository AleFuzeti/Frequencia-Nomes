package com.company.local_name.DAO.impl;

import com.company.local_name.DAO.CidadeDAO;
import com.company.local_name.model.Cidade;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PgCidadeDAO implements CidadeDAO{

    private final Connection connection;
    public PgCidadeDAO(Connection connection) {
        this.connection = connection;
    }

    private static final String CREATE_CIDADE = "INSERT INTO local_names_db.cidade(id, nome, sigla_estado)" + "VALUES (?,?,?)";
    private static final String GET_CIDADE = "SELECT * FROM local_names_db.cidade  WHERE id = ?";
    private static final String GET_ALL_CIDADES = "SELECT * FROM local_names_db.cidade";
    private static final String UPDATE_CIDADE = "UPDATE * FROM local_names_db.cidade SET nome=?, sigla_estado=? WHERE id = ?";
    private static final String DELETE_CIDADE = "DELETE * FROM local_names_db.cidade WHERE id = ?";

    @Override
    public void create(Cidade object) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_CIDADE)) {

            statement.setString(1, object.getId());
            statement.setString(2, object.getNome());
            statement.setString(3, object.getSigla_estado());
            statement.execute();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro ao criar cidade");
        }
    }

    @Override
    public Cidade get(Object key) throws SQLException {
        Cidade cidade = null;
        try (PreparedStatement statement = connection.prepareStatement(GET_CIDADE)) {
            statement.setInt(1, (int)key);
            statement.executeQuery();

            while (statement.getResultSet().next()) {
                cidade = new Cidade(statement.getResultSet().getString("id"), statement.getResultSet().getString("nome"), statement.getResultSet().getString("sigla_estado"));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro na busca de cidade");
        }

        return cidade;
    }

    @Override
    public List<Cidade> getAll() throws SQLException {
        List<Cidade> cidadesList= new ArrayList<Cidade>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_CIDADES)) {
            statement.executeQuery();

            while (statement.getResultSet().next()) {
                Cidade cidade = new Cidade(statement.getResultSet().getString("id"), statement.getResultSet().getString("nome"),statement.getResultSet().getString("sigla_estado"));
                cidadesList.add(cidade);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro na busca de todas as cidades");
        }

        return cidadesList;
    }

    @Override
    public void update(Cidade object) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CIDADE)) {
            statement.setString(1, object.getId());
            statement.setString(2, object.getNome());
            statement.setString(3, object.getSigla_estado());
            statement.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro no update de cidade");
        }
    }

    @Override
    public void delete(Object key) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CIDADE)) {
            statement.setInt(1, (int)key);
            statement.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new SQLException("Erro no delete de cidade");
        }
    }
}