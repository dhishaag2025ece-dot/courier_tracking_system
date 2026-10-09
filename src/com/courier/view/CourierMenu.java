
package com.courier.view;

import com.courier.model.Courier;
import com.courier.service.CourierService;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class CourierMenu {

    private final Scanner sc = new Scanner(System.in);
    private final CourierService service = new CourierService();

    public void start() {

        while (true) {
            System.out.println("\n===== COURIER TRACKING SYSTEM =====");
            System.out.println("1. Book Courier");
            System.out.println("2. Track Courier");
            System.out.println("3. View All Couriers");
            System.out.println("4. Update Courier Status");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            String choice = sc.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        bookCourier();
                        break;
                    case "2":
                        trackCourier();
                        break;
                    case "3":
                        viewAllCouriers();
                        break;
                    case "4":
                        updateStatus();
                        break;
                    case "5":
                        System.out.println("Thank you for using Courier Tracking System!");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void bookCourier() throws Exception {

        System.out.print("Enter sender name: ");
        String sender = sc.nextLine().trim();

        System.out.print("Enter receiver name: ");
        String receiver = sc.nextLine().trim();

        System.out.print("Enter sender address: ");
        String senderAddress = sc.nextLine().trim();

        System.out.print("Enter receiver address: ");
        String receiverAddress = sc.nextLine().trim();

        System.out.print("Enter booking date (YYYY-MM-DD): ");
        Date date = Date.valueOf(sc.nextLine().trim());

        boolean added = service.addCourier(
                sender, receiver, senderAddress, receiverAddress, date);

        if (added) {
            System.out.println("Courier booked successfully!");
            System.out.println("Check the database for its tracking ID.");
        } else {
            System.out.println("Courier booking failed.");
        }
    }

    private void trackCourier() throws Exception {

        System.out.print("Enter tracking ID: ");
        String trackingId = sc.nextLine().trim();

        Courier courier = service.trackCourier(trackingId);

        if (courier == null) {
            System.out.println("Courier not found.");
        } else {
            displayCourier(courier);
        }
    }

    private void viewAllCouriers() throws Exception {

        List<Courier> couriers = service.getAllCouriers();

        if (couriers.isEmpty()) {
            System.out.println("No courier records found.");
            return;
        }

        for (Courier courier : couriers) {
            displayCourier(courier);
            System.out.println("------------------------------");
        }
    }

    private void updateStatus() throws Exception {

        System.out.print("Enter tracking ID: ");
        String trackingId = sc.nextLine().trim();

        System.out.println("1. BOOKED");
        System.out.println("2. IN_TRANSIT");
        System.out.println("3. OUT_FOR_DELIVERY");
        System.out.println("4. DELIVERED");
        System.out.print("Choose new status: ");

        String choice = sc.nextLine().trim();
        String status;

        switch (choice) {
            case "1":
                status = "BOOKED";
                break;
            case "2":
                status = "IN_TRANSIT";
                break;
            case "3":
                status = "OUT_FOR_DELIVERY";
                break;
            case "4":
                status = "DELIVERED";
                break;
            default:
                System.out.println("Invalid status choice.");
                return;
        }

        if (service.updateStatus(trackingId, status)) {
            System.out.println("Courier status updated successfully!");
        } else {
            System.out.println("Courier not found or update failed.");
        }
    }

    private void displayCourier(Courier c) {

        System.out.println("\n--- Courier Details ---");
        System.out.println("Courier ID: " + c.getCourierId());
        System.out.println("Tracking ID: " + c.getTrackingId());
        System.out.println("Sender: " + c.getSenderName());
        System.out.println("Receiver: " + c.getReceiverName());
        System.out.println("Sender Address: " + c.getSenderAddress());
        System.out.println("Receiver Address: " + c.getReceiverAddress());
        System.out.println("Booking Date: " + c.getBookingDate());
        System.out.println("Status: " + c.getStatus());
    }
}