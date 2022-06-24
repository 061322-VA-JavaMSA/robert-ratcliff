package com.revature.daos;

import com.revature.models.Item;
import com.revature.models.User;

public interface SystemDAOS {

    float calculateWeeklyPayment(User u);
    float calculateItemPayment(User u, Item i);
    void rejectOtherOffers(Item i);
    void updateOwnership(User u, Item i);
}
