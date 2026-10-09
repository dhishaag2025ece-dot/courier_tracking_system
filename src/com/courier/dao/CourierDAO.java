
package com.courier.dao;

import com.courier.model.Courier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CourierDAO {

    public boolean addCourier(Courier c) throws Exception {

        String sql = "INSERT INTO courier "
                + "(tracking_id, sender_name, receiver_name, "
                + "sender_address, receiver_address, booking_date, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getTrackingId());
            ps.setString(2, c.getSenderName());
            ps.setString(3, c.getReceiverName());
            ps.setString(4, c.getSenderAddress());
            ps.setString(5, c.getReceiverAddress());
            ps.setDate(6, c.getBookingDate());
            ps.setString(7, c.getStatus());

            return ps.executeUpdate() > 0;
        }
    }

    public Courier getCourierByTrackingId(String trackingId)
            throws Exception {

        String sql = "SELECT * FROM courier WHERE tracking_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, trackingId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapCourier(rs);
                }
            }
        }

        return null;
    }

    public List<Courier> getAllCouriers() throws Exception {

        List<Courier> list = new ArrayList<>();
        String sql = "SELECT * FROM courier ORDER BY courier_id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapCourier(rs));
            }
        }

        return list;
    }

    public boolean updateStatus(String trackingId, String status)
            throws Exception {

        String sql = "UPDATE courier SET status = ? WHERE tracking_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setString(2, trackingId);

            return ps.executeUpdate() > 0;
        }
    }

    private Courier mapCourier(ResultSet rs) throws Exception {

        Courier c = new Courier();

        c.setCourierId(rs.getInt("courier_id"));
        c.setTrackingId(rs.getString("tracking_id"));
        c.setSenderName(rs.getString("sender_name"));
        c.setReceiverName(rs.getString("receiver_name"));
        c.setSenderAddress(rs.getString("sender_address"));
        c.setReceiverAddress(rs.getString("receiver_address"));
        c.setBookingDate(rs.getDate("booking_date"));
        c.setStatus(rs.getString("status"));

        return c;
    }
}