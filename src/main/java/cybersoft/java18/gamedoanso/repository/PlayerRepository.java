package cybersoft.java18.gamedoanso.repository;
import cybersoft.java18.gamedoanso.jdbc.MySqlConnection;
import cybersoft.java18.gamedoanso.model.Player;
import cybersoft.java18.gamedoanso.store.GameStore;
import cybersoft.java18.gamedoanso.store.GameStoreHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerRepository {

    public Player findByUsername(String username) {
        String query ="select  username, password, name from player where username = ? ";
        Connection connection = MySqlConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,username);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                return new Player(
                        result.getString("username"),
                        result.getString("password"),
                        result.getString("name")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean existedByUsername(String username) {
        String query = "select * from player where username = ?";
        Connection connection = MySqlConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,username);
            ResultSet result = statement.executeQuery();
            return result.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void save(Player player) {
        String query = "insert into player(username, password, name) values(?,?,?)";
        Connection connection = MySqlConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,player.getUsername());
            statement.setString(2, player.getPassword());
            statement.setString(3,player.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
