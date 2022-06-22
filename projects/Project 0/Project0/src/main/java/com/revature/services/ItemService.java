package com.revature.services;

import com.revature.daos.ItemDAOS;
import com.revature.daos.ItemPostgresSQL;
import com.revature.models.Item;

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
}
