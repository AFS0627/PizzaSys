package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Funcionario;

public class FuncionarioDAO {

    public static Funcionario realizarLogin(String login, String senha) throws SQLException {

        String sql = "SELECT id, nome, login, senha, funcao, salario "
                   + "FROM funcionario "
                   + "WHERE login = ? AND senha = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    return new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("login"),
                        rs.getString("senha"),
                        rs.getInt("funcao"),
                        rs.getDouble("salario")
                    );
                }
            }
        }

        return null;
    }
}