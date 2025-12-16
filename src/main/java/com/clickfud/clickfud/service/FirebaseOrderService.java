package com.clickfud.clickfud.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class FirebaseOrderService {

    public List<Map<String, Object>> getTodayOrders() throws Exception {

        Firestore db = FirestoreClient.getFirestore();
        CollectionReference ordersRef = db.collection("orders");

        Query query = ordersRef.limit(20);

        ApiFuture<QuerySnapshot> future = query.get();
        List<QueryDocumentSnapshot> docs = future.get().getDocuments();

        List<Map<String, Object>> result = new ArrayList<>();

        for (QueryDocumentSnapshot doc : docs) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", doc.getId());
            map.put("customer", doc.getString("customerName"));
            map.put("restaurant", doc.getString("restaurantName"));
            map.put("amount", doc.getDouble("totalAmount"));
            map.put("status", doc.getString("status"));
            result.add(map);
        }

        return result;
    }

}
