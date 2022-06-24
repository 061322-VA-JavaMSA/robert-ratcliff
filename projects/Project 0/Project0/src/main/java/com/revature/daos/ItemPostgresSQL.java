package com.revature.daos;

import com.revature.models.Item;
import com.revature.util.ConnectionUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemPostgresSQL implements ItemDAOS{
    @Override
    public void changePrice(Item i, float price) {
        if(getItem(i) != null){
            String sql = "update item set price = ? where item_name = ?;";

            try (Connection c = ConnectionUtil.getConnectionFromFile()) {
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setFloat(1, price);
                ps.setString(2, i.getName());

                ps.executeUpdate();
                i.setPrice(price);
                System.out.println("Item: " + i.getName() + " price has been created.");

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("File with credentials not found.");
            }
        }else{
            System.out.println("Item does not exist.");
        }
    }

    @Override
    public void insertItem(Item i) {
        if(getItem(i) != null){
            System.out.println("Can't insert item, as this item already exists in the database.");
        }else{
            String sql = "insert into item (item_name, price) values (?,?) returning id;";
            try (Connection c = ConnectionUtil.getConnectionFromFile()) {
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, i.getName());
                ps.setFloat(2, i.getPrice());

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    i.insertItem(i);
                    System.out.println("Item: " + i.getName() + " has been created.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("File with credentials not found.");
            }
        }

    }

    @Override
    public Item getById(int id) {
        String sql = "select * from item where id = ?;";
        Item i = null;

        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setInt(1, id); // this refers to the 1st ? in the sql String

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                i = new Item();
                i.setId(rs.getInt("id"));
                i.setName(rs.getString("item_name"));
                i.setPrice(rs.getFloat("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }catch(IOException e){
            System.out.println("File with credentials not found.");
        }
        return i;
    }

    @Override
    public Item getItem(Item i) {
        if(i.getId() != 0){
            return getById(i.getId());
        }else if(i.getName() != null){
            return getByItemName(i.getName());
        }
        else {
            return null;
        }
    }

    @Override
    public Item getByItemName(String itemName) {
        String sql = "select * from item where item_name  = ?;";
        Item i = null;

        try (Connection c = ConnectionUtil.getConnectionFromFile()){
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setString(1, itemName);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                i = new Item();
                i.setId(rs.getInt("id"));
                i.setName(rs.getString("item_name"));
                i.setPrice(rs.getFloat("price"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }catch(IOException e){
            System.out.println("File with credentials not found.");
        }

        return i;
    }

    public void changeAvailability(Item i, boolean avail){
        if(getItem(i) != null){
            String sql = "update item set availability = ? where item_name = ?;";

            try (Connection c = ConnectionUtil.getConnectionFromFile()) {
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setBoolean(1, avail);
                ps.setString(2, i.getName());

                ps.executeUpdate();
                i.setAvailable(avail);
                System.out.println("Item: " + i.getName() + " availability has been changed.");

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("File with credentials not found.");
            }
        }else{
            System.out.println("Item does not exist.");
        }
    }

    public List<Item> getAllAvailableItems(){
        String sql = "select * from item where availability = true;";
        List<Item> itemList = new ArrayList();

        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            PreparedStatement ps = c.prepareStatement(sql);


            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Item i = getById(rs.getInt("id"));
                itemList.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }catch(IOException e){
            System.out.println("File with credentials not found.");
        }
        return itemList;
    }

    @Override
    public void removeItem(Item i) {
        if(getItem(i) == null){
            System.out.println("Can't remove item, as this item does not exist in the database.");
        }else{
            String sql = "delete from item where item_name = ?;";
            try (Connection c = ConnectionUtil.getConnectionFromFile()) {
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, i.getName());

                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("File with credentials not found.");
            }
        }
    }
}
