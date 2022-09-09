package cybersoft.java18.gamedoanso.repository;

import cybersoft.java18.gamedoanso.jdbc.MySqlConnection;
import cybersoft.java18.gamedoanso.model.Gues;
import cybersoft.java18.gamedoanso.store.GameStore;
import cybersoft.java18.gamedoanso.store.GameStoreHolder;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GuessRepository {

    public List<Gues> findBySession(String gameSession_id) {
        List<Gues> guesList = new ArrayList<>();
        String query = "select value, moment, session_id, result from guess where session_id = ?";
        Connection connection = MySqlConnection.getConnection();
        try {
            PreparedStatement statement =connection.prepareStatement(query);
            statement.setString(1,gameSession_id);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                guesList.add(new Gues(
                        result.getInt("value"),
                        getDateTime(result,"moment"),
                        result.getString("session_id"),
                        result.getInt("result")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
       return guesList;
    }

    public void save(Gues guess) {
        String query = "insert into guess( value, moment, session_id, result) values(?,?,?,?)";
        Connection connection = MySqlConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,guess.getValue());
            statement.setTimestamp(2, Timestamp.from(
                    guess.getTimestamp().toInstant(ZoneOffset.of("+07:00"))));
            statement.setString(3,guess.getGameSession());
            statement.setInt(4,guess.getResult());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private LocalDateTime getDateTime(ResultSet result, String columnName){
        Timestamp time;
        try {
            time=result.getTimestamp(columnName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return time.toLocalDateTime();
    }
}
