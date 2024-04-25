package id.ac.ui.cs.advprog.statustrackingorder.repository;

import id.ac.ui.cs.advprog.statustrackingorder.model.Tracking;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TrackingRepository {
    private Connection connection;

    public TrackingRepository(Connection connection) {
        this.connection = connection;
    }

    public void createTracking(Tracking tracking) throws SQLException {
        String sql = "INSERT INTO tracking (tracking_id, order_id, method, resi_code) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, tracking.getTrackingId());
            statement.setLong(2, tracking.getOrderId());
            statement.setString(3, tracking.getMethode());
            statement.setString(4, tracking.getResiCode());
            statement.executeUpdate();
        }
    }

    public Optional<Tracking> findTrackingById(Long trackingId) throws SQLException {
        String sql = "SELECT * FROM tracking WHERE tracking_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, trackingId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Tracking tracking = new Tracking();
                tracking.setTrackingId(resultSet.getLong("tracking_id"));
                tracking.setOrderId(resultSet.getLong("order_id"));
                tracking.setMethode(resultSet.getString("method"));
                tracking.setResiCode(resultSet.getString("resi_code"));
                return Optional.of(tracking);
            } else {
                return Optional.empty();
            }
        }
    }

    public List<Tracking> findTrackingByOrderId(Long orderId) throws SQLException {
        List<Tracking> trackingList = new ArrayList<>();
        String sql = "SELECT * FROM tracking WHERE order_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Tracking tracking = new Tracking();
                tracking.setTrackingId(resultSet.getLong("tracking_id"));
                tracking.setOrderId(resultSet.getLong("order_id"));
                tracking.setMethode(resultSet.getString("method"));
                tracking.setResiCode(resultSet.getString("resi_code"));
                trackingList.add(tracking);
            }
        }
        return trackingList;
    }

    public List<Tracking> findAllTracking() throws SQLException {
        List<Tracking> trackingList = new ArrayList<>();
        String sql = "SELECT * FROM tracking";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Tracking tracking = new Tracking();
                tracking.setTrackingId(resultSet.getLong("tracking_id"));
                tracking.setOrderId(resultSet.getLong("order_id"));
                tracking.setMethode(resultSet.getString("method"));
                tracking.setResiCode(resultSet.getString("resi_code"));
                trackingList.add(tracking);
            }
        }
        return trackingList;
    }
}
