package com.revature.daos;

import com.revature.models.Item;

import java.util.List;

public interface ItemDAOS {

    void changePrice(Item i, float price);
    void insertItem(Item i);
    Item getById(int id);
    Item getItem(Item i);
    Item getByItemName(String itemName);
    List<Item> getAllAvailableItems();
    void changeAvailability(Item i, boolean avail);
    void removeItem(Item i);

}
