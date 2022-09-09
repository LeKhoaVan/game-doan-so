package cybersoft.java18.gamedoanso.repository;

import cybersoft.java18.gamedoanso.jdbc.MySqlConnection;
import cybersoft.java18.gamedoanso.model.GameSession;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class GameSessionRepository {

    public List<GameSession> findByUsername(String username) {
        List<GameSession> listGameSession = new ArrayList<>();
        String query = "select id, target, start_time, end_time, is_completed, is_active, username from game_session where username = ?";
        Connection connection = MySqlConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                listGameSession.add(new GameSession(
                        result.getString("id"),
                        result.getInt("target"),
                        getDateTimeFromResultSet("start_time", result),
                        getDateTimeFromResultSet("end_time", result),
                        result.getInt("is_completed") == 1 ? true : false,
                        result.getInt("is_active") == 1 ? true : false,
                        result.getString("username")
                ));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listGameSession;
    }

    public void save(GameSession gameSession) {
        String query = "insert into game_session(id,target,start_time,is_completed,is_active,username) values(?,?,?,?,?,?)";
        Connection connection = MySqlConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, gameSession.getId());
            statement.setInt(2, gameSession.getTarget());
            statement.setTimestamp(3, Timestamp.from(
                    gameSession.getStartTime().toInstant(ZoneOffset.of("+07:00"))
            ));
            statement.setInt(4, (gameSession.getIsCompleted() == true) ? 1 : 0);
            statement.setInt(5, (gameSession.getIsActive() == true) ? 1 : 0);
            statement.setString(6, gameSession.getUsername());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private LocalDateTime getDateTimeFromResultSet(String columnName, ResultSet result) {
        Timestamp time;
        try {
            time = result.getTimestamp(columnName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (time == null)
            return null;
        return time.toLocalDateTime();
    }

    public GameSession findGameSessionById(String gameSession_id) {
        List<GameSession> gameSessionList = new ArrayList<>();
        String query = "select id,target,start_time,end_time,is_completed,is_active,username from game_session where id= ?";
        Connection connection = MySqlConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, gameSession_id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return (new GameSession(
                        result.getString("id"),
                        result.getInt("target"),
                        getDateTimeFromResultSet("start_time", result),
                        getDateTimeFromResultSet("end_time", result),
                        result.getInt("is_completed") == 1 ? true : false,
                        result.getInt("is_active") == 1 ? true : false,
                        result.getString("username")));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void gameCompleted(String gameSession_id) {
        String query = "update game_session set is_completed = 1 where id =?";
        Connection connection = MySqlConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, gameSession_id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deactivateAllGames(String username) {
        final String query = """
                update game_session
                set is_active = 0
                where username = ?
                """;
        Connection connection = MySqlConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void endTimeToGame(String gameSession_id) {
        String query = "update game_session set end_time = ? where id =?";
        Connection connection = MySqlConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setTimestamp(1, Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.of("+07:00"))));
            statement.setString(2, gameSession_id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<GameSession> ratingPlayer() {
        ArrayList<GameSession> gameSessionList = new ArrayList<>();
        String query = "select id, target, start_time, end_time, is_completed, is_active, username from game_session where end_time is not null ";
        Connection connection = MySqlConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                gameSessionList.add(new GameSession(
                        result.getString("id"),
                        result.getInt("target"),
                        getDateTimeFromResultSet("start_time", result),
                        result.getTimestamp("end_time").toLocalDateTime(),
                        result.getInt("is_completed") == 1 ? true : false,
                        result.getInt("is_active") == 1 ? true : false,
                        result.getString("username")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return gameSessionList;
    }
}
