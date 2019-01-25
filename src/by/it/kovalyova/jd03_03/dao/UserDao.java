package by.it.kovalyova.jd03_03.dao;

import by.it.kovalyova.jd03_03.beans.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDao implements InterfaceDao<User>{
    public boolean create(User user) throws SQLException {
        String sql = String.format(
                "INSERT INTO `users` (`login`, `password`, `email`, `roles_id`) " +
                        "VALUES ('%s', '%s', '%s', '%d')",
                user.getLogin(), user.getPassword(), user.getEmail(), user.getRoles_Id()
        );
        long id = Dao.executeCreateAndGetId(sql);
        if (id > 0) {
            user.setId(id);
            return true;
        } else
            return false;
    }

   public boolean delete(User user) throws SQLException {
        String sql = String.format(
                "DELETE FROM `users` WHERE `users`.`id` = %d",
                user.getId()
        );
        return Dao.executeUpdate(sql);
    }

    public boolean update(User user) throws SQLException {
        String sql = String.format(
                "UPDATE `users` SET " +
                        "`login` = '%s', `password` = '%s', " +
                        "`email` = '%s',  `roles_id` = '%d' " +
                        "WHERE `users`.`id` = %d",
                user.getLogin(), user.getPassword(),
                user.getEmail(), user.getRoles_Id(),
                user.getId()
        );
        return Dao.executeUpdate(sql);
        }


   public User read(long id) throws SQLException {
        String sql = String.format("SELECT `id`, `login`, `password`, `email`, `roles_id` " +
                "FROM `users` WHERE id=%d", id);
        try (Connection connection = Connect.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                // id ok
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                long roles_Id = resultSet.getLong("roles_Id");
                return new User(id, login, password, email, roles_Id);
            } else
                return null;

        }
    }

    @Override
    public List<User> getAll(String sqlSuffix) throws SQLException {
        return null;
    }

}
