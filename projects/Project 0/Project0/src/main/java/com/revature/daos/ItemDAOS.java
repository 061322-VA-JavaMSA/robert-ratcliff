package com.revature.daos;

import com.revature.models.Item;

public interface ItemDAOS {

    void changePrice(Item i);
    void insertItem(Item i);
    Item getById(int id);
    Item getItem(Item i);
    Item getByItemName(String itemName);

}
