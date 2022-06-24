package com.revature.services;

import com.revature.daos.ItemDAOS;
import com.revature.daos.ItemPostgresSQL;
import com.revature.models.Item;

import java.util.List;

public class ItemService {

    private ItemDAOS ids = new ItemPostgresSQL();

    public Item createItem(Item i) {
        ids.insertItem(i);
        return i;
    }

    public Item getItemById(int id){
        return ids.getById(id);
    }

    public Item getItem(Item i){
        return ids.getItem(i);
    }

    public Item getItemName(String name){
        return ids.getByItemName(name);
    }

    public List<Item> getAllItems(){
        return ids.getAllAvailableItems();
    }

    public void insertItem(Item i){ids.insertItem(i);}
    public Item getByItemName(String itemName){return ids.getByItemName(itemName);}
    public void changeAvailability(Item i, boolean avail){ids.changeAvailability(i, avail);}
    public void changePrice(Item i, float price){ids.changePrice(i,price);}
}
