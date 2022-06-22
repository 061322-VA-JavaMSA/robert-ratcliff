package com.revature.models;

import com.revature.daos.ItemDAOS;

import java.util.Objects;

public class Item {
    private int id;
    private String name;
    private String description;
    private float price; //Will decide later on what data type.

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", available=" + available +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    private boolean available;
    public void changePrice(Item i) {

    }

    public void insertItem(Item i) {

    }

    public Item getById(int id) {
        return null;
    }

    public Item getItem(Item i) {
        return null;
    }

    public Item getByItemName(String itemName) {
        return null;
    }
}
