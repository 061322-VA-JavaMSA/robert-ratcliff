package com.revature.daos;

import com.revature.models.Item;
import com.revature.models.User;

import java.util.List;

public interface SystemDAOS {

    float calculateWeeklyPayment(User u);
    float calculateItemPayment(User u, Item i);
    List getAllPayments();
    void rejectOtherOffers(Item i);
    void updateOwnership(User u, Item i, float amount);
    List getAllOffers();
    public float getItemOffer(User u, Item i);
}
