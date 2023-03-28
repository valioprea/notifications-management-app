package com.vali.notifications.daos;

import com.vali.notifications.daos.mappers.NotificationRowMapper;
import com.vali.notifications.entities.Notification;
import com.vali.notifications.exceptions.CustomDatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

@Component
public class NotificationDAOImpl implements NotificationDAO {

    @Value("${spring.datasource.url}")
    private String dbURL;
    @Value("${spring.datasource.username}")
    private String dbUser;
    @Value("${spring.datasource.password}")
    private String dbPass;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Notification insertNotificationInDatabase(Notification notification) {

        String insertSql = """
                INSERT INTO notifications_table (user_id, time_stamp, message, has_been_read)
                VALUES (:user_id, :time_stamp, :message, :has_been_read);
                """;

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", notification.getUserId());
        parameters.put("time_stamp", notification.getTimeStamp());
        parameters.put("message", notification.getMessage());
        parameters.put("has_been_read", notification.getHasBeenRead());

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        try {
            int affectedRows = namedParameterJdbcTemplate.update(insertSql, new MapSqlParameterSource(parameters), generatedKeyHolder);
            if (affectedRows == 0) {
                throw new Exception("Exception: on inserting a new notification");
            }
            Integer sqlNotificationId = Objects.requireNonNull(generatedKeyHolder.getKey()).intValue();
            notification.setNotificationId(sqlNotificationId);
            return notification;
        } catch (Exception e) {
            throw new CustomDatabaseException("Exception in inserting a new notification");
        }
    }

    @Override
    public List<Notification> retrieveLastNotifications(Integer userId, Integer numberOfNotifications) {
        String querySql = "SELECT * FROM notifications_table WHERE user_id = ? ORDER BY time_stamp DESC LIMIT ?";
        List<Notification> results = new ArrayList<>();
        try (
                Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
                PreparedStatement ps = conn.prepareStatement(querySql);
        ) {
            ps.setInt(1, userId);
            ps.setInt(2, numberOfNotifications);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Notification notification = new NotificationRowMapper().mapRow(rs, rs.getRow());
                results.add(notification);
            }
            return results;
        } catch (Exception e) {
            throw new CustomDatabaseException("Exception in retrieving last notifications");
        }
    }

    @Override
    public boolean updateNotificationOfUser(Integer userId, Integer notificationId, Notification notification) {
        String updateSql = """
                UPDATE notifications_table
                SET time_stamp = ?,
                    message = ?,
                    has_been_read = ?
                WHERE notification_id = ? AND user_id = ?;
                """;
        try {
            jdbcTemplate.update(
                    updateSql,
                    notification.getTimeStamp(),
                    notification.getMessage(),
                    notification.getHasBeenRead(),
                    notification.getNotificationId(),
                    notification.getUserId()
            );
            return true;
        } catch (Exception e) {
            throw new CustomDatabaseException("Exception in updating notifications of user");
        }
    }

    @Override
    public List<Notification> retrieveAllNotifications() {
        String selectSQL = "SELECT * FROM NOTIFICATIONS_TABLE;";
        try {
            return jdbcTemplate.query(selectSQL, new NotificationRowMapper());
        } catch (Exception e) {
            throw new CustomDatabaseException("Exception in retrieving all notifications");
        }
    }

    @Override
    public Notification retrieveNotificationByIdAndUser(Integer userId, Integer notificationId) {
        String selectSQL = "SELECT count(*) from notifications_table where user_id = ? and notification_id = ?;";

        try (
                Connection con = DriverManager.getConnection(dbURL, dbUser, dbPass);
                PreparedStatement ps = con.prepareStatement(selectSQL);
        ) {

            ps.setInt(1, userId);
            ps.setInt(2, notificationId);

            ResultSet rs = ps.executeQuery();
            Notification notification = new Notification();
            notification.setNotificationId(-1);
            if (rs.next()) {
                if (rs.getInt(1) != 0) {
                    notification.setNotificationId(rs.getInt(1));
                }
            }
            return notification;
        } catch (Exception e) {
            throw new CustomDatabaseException("Exception in retrieving notifications by id and user");
        }
    }
}
