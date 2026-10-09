
package com.courier.service;

import com.courier.dao.CourierDAO;
import com.courier.model.Courier;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

public class CourierService {

    private final CourierDAO courierDAO = new CourierDAO();

    public boolean addCourier(String senderName, String receiverName,
                              String senderAddress, String receiverAddress,
                              Date bookingDate) throws Exception {

        if (senderName == null || senderName.trim().isEmpty()
                || receiverName == null || receiverName.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Sender and receiver names are required.");
        }

        Courier courier = new Courier();

        courier.setTrackingId("CR" +
                UUID.randomUUID().toString()
                        .replace("-", "").substring(0, 10).toUpperCase());

        courier.setSenderName(senderName);
        courier.setReceiverName(receiverName);
        courier.setSenderAddress(senderAddress);
        courier.setReceiverAddress(receiverAddress);
        courier.setBookingDate(bookingDate);
        courier.setStatus("BOOKED");

        return courierDAO.addCourier(courier);
    }

    public Courier trackCourier(String trackingId) throws Exception {
        if (trackingId == null || trackingId.trim().isEmpty()) {
            throw new IllegalArgumentException("Enter a tracking ID.");
        }

        return courierDAO.getCourierByTrackingId(trackingId.trim());
    }

    public List<Courier> getAllCouriers() throws Exception {
        return courierDAO.getAllCouriers();
    }

    public boolean updateStatus(String trackingId, String status)
            throws Exception {

        String[] validStatuses = {
            "BOOKED", "IN_TRANSIT",
            "OUT_FOR_DELIVERY", "DELIVERED"
        };

        boolean valid = false;

        for (String s : validStatuses) {
            if (s.equalsIgnoreCase(status.trim())) {
                status = s;
                valid = true;
                break;
            }
        }

        if (!valid) {
            throw new IllegalArgumentException("Invalid courier status.");
        }

        return courierDAO.updateStatus(trackingId.trim(), status);
    }
}