package com.revature.daos;

import com.revature.models.Item;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SystemPostgreSQL implements SystemDAOS{

    @Override
    public float calculateWeeklyPayment(User u) {
        float dues = 0;
        String sql = "select sum(balance) as total from payment where customer_id = ?;";
        try (Connection c = ConnectionUtil.getConnectionFromFile()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, u.getUserId());

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                dues = rs.getFloat("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File with credentials not found.");
        }
        return dues;
    }

    @Override
    public float calculateItemPayment(User u , Item i){
        float dues = 0;
        String sql = "select sum(balance) as total from payment where customer_id = ? and item_id = ?;";
        try (Connection c = ConnectionUtil.getConnectionFromFile()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, u.getUserId());
            ps.setInt(2, i.getId());

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                dues = rs.getFloat("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File with credentials not found.");
        }
        return dues;
    }

    @Override
    public List getAllPayments(){
        List totals = new ArrayList();
        String sql = "select sum(p.balance) as total, c.username from payment as p " +
                "join customer c ON c.id = p.customer_id  group by c.username;";
        try (Connection c = ConnectionUtil.getConnectionFromFile()) {
            PreparedStatement ps = c.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {

                totals.add("User, "+rs.getString("username")+ ", totals are: " +rs.getFloat("total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File with credentials not found.");
        }
        return totals;
    }

    @Override
    public void rejectOtherOffers(Item i) {
        String sql = "update offer set accepted = 'rejected' where item_id = ? and accepted != 'accepted';";
        try (Connection c = ConnectionUtil.getConnectionFromFile()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, i.getId());

            ps.executeUpdate();
            System.out.println("All other offers for Item, " +i.getName()+ ", rejected.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File with credentials not found.");
        }
    }

    @Override
    public void updateOwnership(User u, Item i, float amount) {

            String sql = "begin;" +
                    "insert into owned_items (customer_id, item_id) values (?,?);" +
                    "insert into payment (customer_id, item_id, balance) values (?,?,?);" +
                    "commit;";
            try (Connection c = ConnectionUtil.getConnectionFromFile()) {
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setInt(1, u.getUserId());
                ps.setInt(2, i.getId());
                ps.setInt(3, u.getUserId());
                ps.setInt(4, i.getId());
                ps.setFloat(5, amount);

                ps.executeUpdate();
                u.addOwnedItems(i);
                System.out.println("Item: " + i.getName() + " has a new owner " +u.getUsername());

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("File with credentials not found.");
            }
    }

    @Override
    public List getAllOffers() {
        List l = new ArrayList();
        String sql = "select * from offer where accepted = 'pending';";
        try (Connection c = ConnectionUtil.getConnectionFromFile()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                l.add("Customer id: "+ rs.getInt("customer_id") +
                        " Item_id: " +rs.getInt("item_id")+
                        " Amount: " +rs.getFloat("amount"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File with credentials not found.");
        }
        return l;
    }

    @Override
    public float getItemOffer(User u, Item i){
        String sql = "select amount from offer where customer_id = ? and item_id = ?;";
        float amount = 0f;

        try(Connection c = ConnectionUtil.getConnectionFromFile()){
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, u.getUserId());
            ps.setInt(2,i.getId());

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                amount = rs.getFloat("amount");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }catch(IOException e){
            System.out.println("File with credentials not found.");
        }
        return amount;
    }

}
