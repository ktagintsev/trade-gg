/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trade;

import Bot.Main;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ktagintsev
 */
public class TradeOffer {

    private String tradeofferid;
    private int accountid_other;
    private String message = "";
    private String expirationTime;
    private int trade_offer_state;
    private List<Item> itemsToGive;
    private List<Item> itemsToReceive;
    private boolean is_our_offer;
    private int time_created = 0;
    private int time_updated = 0;
    private String tradeid = "";
    private boolean from_real_time_trade = false;
    private int escrow_end_date = 0;
    private int confirmation_method = 0;

    public TradeOffer(JSONObject tradeOffer) throws JSONException {
        System.out.println(tradeOffer.toString());
        this.tradeofferid = (String) getParamObject(tradeOffer, "tradeofferid");
        this.accountid_other = (int) getParamObject(tradeOffer, "accountid_other");
        this.message = (String) getParamObject(tradeOffer, "message");
        this.trade_offer_state = (int) getParamObject(tradeOffer, "trade_offer_state");

        try {
            this.itemsToReceive = new ArrayList<Item>();
            JSONArray items_to_receive = getParamArray(tradeOffer, "items_to_receive");
            for (int i = 0; i < items_to_receive.length(); i++) {
                Item item = new Item(new JSONObject(items_to_receive.get(i).toString()));
                itemsToReceive.add(item);
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            this.itemsToGive = new ArrayList<Item>();
            JSONArray items_to_give = getParamArray(tradeOffer, "items_to_give");
            for (int i = 0; i < items_to_give.length(); i++) {
                Item item = new Item(new JSONObject(items_to_give.get(i).toString()));
                itemsToGive.add(item);
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.is_our_offer = (boolean) getParamObject(tradeOffer, "is_our_offer");
        this.time_created = (int) getParamObject(tradeOffer, "time_created");
        this.time_updated = (int) getParamObject(tradeOffer, "time_updated");
        this.tradeid = (String) getParamObject(tradeOffer, "tradeid");
        this.from_real_time_trade = (boolean) getParamObject(tradeOffer, "from_real_time_trade");
        this.escrow_end_date = (int) getParamObject(tradeOffer, "escrow_end_date");
        this.confirmation_method = (int) getParamObject(tradeOffer, "confirmation_method");

    }

    public Object getParamObject(JSONObject tradeOffer, String key) {
        Object param = "";
        try {
            param = tradeOffer.get(key);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return param;
    }

    public JSONArray getParamArray(JSONObject tradeOffer, String key) {
        JSONArray array = new JSONArray();
        try {
            array = new JSONArray(tradeOffer.get(key).toString());
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return array;
    }

    public boolean is_our_offer() {
        return is_our_offer;
    }

    public String getTradeofferid() {
        return tradeofferid;
    }

    public int getAccountid_other() {
        return accountid_other;
    }

    public void getInfo() {
        System.out.println("tradeofferid:" + this.tradeofferid);
        System.out.println("accountid_other:" + this.accountid_other);
        System.out.println("message:" + this.message);
        System.out.println("expirationTime:" + this.expirationTime);
        System.out.println("itemsToGive:");
        getItemToGive();
        System.out.println("itemsToReceive:");
        getItemsToReceive();
        System.out.println("is_our_offer:" + this.is_our_offer);
        System.out.println("time_created:" + this.time_created);
        System.out.println("time_updated:" + this.time_updated);
        System.out.println("tradeid:" + this.tradeid);
        System.out.println("from_real_time_trade:" + this.from_real_time_trade);
        System.out.println("escrow_end_date:" + this.escrow_end_date);
        System.out.println("confirmation_method:" + this.confirmation_method);
    }

    public void getItemToGive() {
        for (int i = 0; i < itemsToGive.size(); i++) {
            Item info = itemsToGive.get(i);
            System.out.println("       -------------------");
            info.getInfo();
        }
    }
    
    public void getItemsToReceive() {
        for (int i = 0; i < itemsToReceive.size(); i++) {
            Item info = itemsToReceive.get(i);
            System.out.println("       -------------------");
            info.getInfo();
        }
    }

    public String getMessage() {
        return message;
    }

}
