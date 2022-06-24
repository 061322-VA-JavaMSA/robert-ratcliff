package com.revature.daos;

import com.revature.models.Item;
import com.revature.models.User;
import com.revature.services.ItemService;
import com.revature.util.ConnectionUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserPostgreSQL implements UserDAOS{

    @Override
    public void insertUser(User u) {
        if(getUser(u) != null){
            System.out.println("Can't insert user, as this user already exists");
        }else {
            String sql = "insert into customer (username, password) values (?,?) returning id;";
            try (Connection c = ConnectionUtil.getConnectionFromFile()) {
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setString(1, u.getUsername());
                ps.setString(2, u.getPassword());

                ps.executeUpdate();
                u.insertUser(u);
                System.out.println("User: " + u.getUsername() + " has been created.");

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("File with credentials not found.");
            }
        }
    }

    @Override
    public User getById(int id) {
        String sql = "select * from customer where id = ?;";
        User user = null;

        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setInt(1, id); // this refers to the 1st ? in the sql String

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }catch(IOException e){
            System.out.println("File with credentials not found.");
        }
        return user;
    }

    @Override
    public User getUser(User u) {
        return getByLogin(u.getUsername(), u.getPassword());//might change get by login to getUser
    }

    @Override
    public User getByUsername(String username) {
        String sql = "select * from customer where username  = ?;";
        User u = null;

        try (Connection c = ConnectionUtil.getConnectionFromFile()){
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                u = new User();
                u.setUserId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }catch(IOException e){
            System.out.println("File with credentials not found.");
        }

        return u;
    }

    @Override
    public User getByLogin(String username, String passwd) throws NullPointerException {
        String sql = "select * from customer where username = ? and password = ?; ";
        User u = null;

        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, passwd);

            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                u = new User();
                u.setUserId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setEmployee(rs.getBoolean("isEmployee"));
                return u;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }catch(IOException e){
            System.out.println("File with credentials not found.");
        }

        return u;
    }

    @Override
    public List<Item> getOwnedItems(User u) {
        String sql = "select * from owned_items where customer_id = ?; ";
        ArrayList<Item> il = new ArrayList();

        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, u.getUserId());

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                ItemService is = new ItemService();
                Item i = is.getItemById(rs.getInt("item_id"));
                il.add(i);
            }
            return il;
        }catch (SQLException e) {
            e.printStackTrace();
        }catch(IOException e){
            System.out.println("File with credentials not found.");
        }

        return il;
    }


}
