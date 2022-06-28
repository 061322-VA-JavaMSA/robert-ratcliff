package com.revature.services;

import com.revature.daos.SystemDAOS;
import com.revature.daos.SystemPostgreSQL;
import com.revature.models.Item;
import com.revature.models.User;

import java.util.List;

public class SystemService {
    private SystemDAOS  sd = new SystemPostgreSQL();


    public List getTotalPayments(){return sd.getAllPayments();}
    public List getAllOffers(){return sd.getAllOffers();}
    public float getItemOffer(User u, Item i){return sd.getItemOffer(u,i);}
    public void updateOwnership(User u, Item i, float amount){sd.updateOwnership(u,i,amount);}
    public float getWeeklyPayment(User u){return sd.calculateWeeklyPayment(u);}
    public float getItemPayment(User u, Item i){return sd.calculateItemPayment(u, i);}
    public boolean isOfferAccepted(User u, Item i){return sd.isOfferAccepted(u,i);}
    public void rejectOtherOffers(Item i){sd.rejectOtherOffers(i);}
}
