package com.revature.daos;

import com.revature.models.Item;

public interface StoreDAOS {

    Item getItems(Item i);
    void addItem(Item i);
    Item removeItem();

}
