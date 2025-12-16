package com.clickfud.clickfud.service;

import com.clickfud.clickfud.dto.OrderDTO;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private final Firestore db = FirestoreClient.getFirestore();

    // 1️⃣ Get Today's Orders
    public List<OrderDTO> getTodayOrders() {
        List<OrderDTO> list = new ArrayList<>();

        String today = LocalDate.now().toString();

        try {
            ApiFuture<QuerySnapshot> future =
                    db.collection("orders")
                            .whereEqualTo("createdAt", today)
                            .get();

            for (DocumentSnapshot doc : future.get().getDocuments()) {
                list.add(toDTO(doc));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2️⃣ Order History (status + date filter)
    public List<OrderDTO> getHistoryOrders(String status, String date) {
        List<OrderDTO> list = new ArrayList<>();

        try {
            Query query = db.collection("orders");

            if (status != null && !status.isEmpty()) {
                query = query.whereEqualTo("status", status);
            }

            if (date != null && !date.isEmpty()) {
                query = query.whereEqualTo("createdAt", date);
            }

            ApiFuture<QuerySnapshot> future = query.get();

            for (DocumentSnapshot doc : future.get().getDocuments()) {
                list.add(toDTO(doc));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3️⃣ Update Order Status (Modal action)
    public void updateStatus(String orderId, String status) {
        try {
            db.collection("orders")
                    .document(orderId)
                    .update(Map.of(
                            "status", status
                    ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔁 Convert Firestore → DTO
    private OrderDTO toDTO(DocumentSnapshot doc) {
        OrderDTO dto = new OrderDTO();

        dto.setId(doc.getId());
        dto.setCustomerName(doc.getString("customerName"));
        dto.setCustomerPhone(doc.getString("customerPhone"));
        dto.setRestaurantId(doc.getString("restaurantId"));
        dto.setRestaurantName(doc.getString("restaurantName"));
        dto.setItem(doc.getString("item"));
        dto.setAmount(doc.getDouble("amount"));
        dto.setStatus(doc.getString("status"));
        dto.setCreatedAt(doc.getString("createdAt"));
        dto.setCreatedTimestamp(
                doc.getLong("createdTimestamp") != null
                        ? doc.getLong("createdTimestamp")
                        : 0
        );
        dto.setRiderId(doc.getString("riderId"));

        return dto;
    }
}
