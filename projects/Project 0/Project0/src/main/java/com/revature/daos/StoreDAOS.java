package com.revature.daos;

import com.revature.models.Item;
import java.util.List;

public interface StoreDAOS {

    Item getItems(Item i);
    void addItem(Item i);
    Item removeItem(Item i);
    Item getItemById(int id);
    Item getItemByName(String itemName);
    List<Item> getItemList();

}
