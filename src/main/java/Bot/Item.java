/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ktagintsev
 */
public class Item {

    public String name = "";
    public long classid;
    public long instanceid;
    public JSONArray sell_offers = new JSONArray();
    public int sell_count_offers = 0;
    public JSONArray buy_offers = new JSONArray();
    public int buy_count_offers = 0;
    public long sell_offer_leader = 0;
    public long buy_offer_leader = 0;
    public JSONArray history;
    public long my_sell_offer;
    public long my_buy_offer;
    public long idInTrades;
    public JSONObject sells;
    public JSONObject buys;
    public long minBuyPrice;
    public long maxBuyPrice;
    public String ui_id;
    public String image;
    public boolean isStar = false;
    public int countStar = 1;
    public boolean isStickers;
    public long averagePrice;
    public long hisMaxPrice;

    public Item(JSONObject item) throws JSONException {
        this.classid = item.getLong("classid");
        this.instanceid = item.getLong("instanceid");
        this.name = new JSONObject(item.getString("info")).getString("market_hash_name");
        this.image = new JSONObject(item.getString("info")).getString("image");

        if (!"false".equals(item.getString("sell_offers"))) {
            this.sells = new JSONObject(item.getString("sell_offers"));
            this.sell_offers = new JSONArray(sells.getString("offers"));
            this.sell_count_offers = this.getOrdersCount(this.sell_offers);
            this.sell_offer_leader = sells.getLong("best_offer");
            this.my_sell_offer = sells.get("my_offers") == null || "null".equals(sells.getString("my_offers")) ? 0 : new JSONArray(sells.getString("my_offers")).getLong(0);
        }

        if (!"false".equals(item.getString("buy_offers"))) {
            this.buys = new JSONObject(item.getString("buy_offers"));
            this.buy_offers = new JSONArray(buys.getString("offers"));
            this.buy_count_offers = this.getOrdersCount(this.buy_offers);
            this.buy_offer_leader = buys.getLong("best_offer");
            this.my_buy_offer = buys.get("my_offer") == null || "null".equals(buys.getString("my_offer")) ? 0 : buys.getLong("my_offer");
        }

        if ("false".equals(item.getString("history"))) {
            this.history = new JSONArray();
            this.averagePrice = 0;
            this.hisMaxPrice = 0;
        } else {
            JSONObject objHis = new JSONObject(item.getString("history"));
            this.averagePrice = objHis.getLong("average");
            this.hisMaxPrice = objHis.getLong("max");
            this.history = new JSONArray(objHis.getString("history"));
        }
    }

    public Item() {

    }

    public boolean isMySellOffer() {
        return sell_offer_leader == my_sell_offer;
    }

    public boolean isMyBuyOffer() {
        return buy_offer_leader == my_buy_offer;
    }

    public boolean isValidSellOrders() {
        return sell_offer_leader > 0 && sell_count_offers > 3;
    }

    public boolean isValidBuyOrders() {
        return buy_offer_leader > 0 && buy_count_offers > 3;
    }

    public void print() {
        System.out.println("classid_instanceid: " + this.classid + "_" + this.instanceid);
        System.out.println("sell_offers: " + this.sell_offers.toString());
        System.out.println("buy_offers: " + this.buy_offers.toString());
        System.out.println("sell_offer_leader: " + this.sell_offer_leader);
        System.out.println("buy_offer_leader: " + this.buy_offer_leader);
        System.out.println("my_sell_offer: " + this.my_sell_offer);
        System.out.println("my_buy_offer: " + this.my_buy_offer);
        System.out.println("maxBuyPrice: " + this.maxBuyPrice);
        System.out.println("sells: " + this.sells);
        System.out.println("buys: " + this.buys);
        System.out.println("History: " + this.history.toString());
        System.out.println("hisMaxPrice: " + this.hisMaxPrice);
        System.out.println("averagePrice: " + this.averagePrice);
    }

    public void calMaxBuyPrice(Game game) throws JSONException {
        long minPriceBuy = Math.round(this.buy_offer_leader * (1 - (double) game.discount / 100));
        long maxPriceSell = Math.round(this.sell_offer_leader * (1 - (double) game.commission / 100));
        this.maxBuyPrice = this.buy_offer_leader + maxPriceSell - minPriceBuy;
        if (this.hisMaxPrice < this.maxBuyPrice) {
            this.maxBuyPrice = this.hisMaxPrice;
        }
    }

    public long calMinBuyPrice(Game game, long paid) {
        return (long) Math.ceil(paid / (1 - ((double) game.commission / 100)));
    }

    public int getMyBuyPos() throws JSONException {
        if (isMyBuyOffer()) {
            return 1;
        }
        for (int i = 0; i < this.buy_offers.length(); i++) {
            long price = new JSONArray(this.buy_offers.get(i).toString()).getLong(0);
            if (price == my_buy_offer) {
                return i + 1;
            }
        }
        return 0;
    }

    private int getOrdersCount(JSONArray orders) {
        int total = 0;
        try {
            for (int i = 0; i < orders.length(); i++) {
                int count = new JSONArray(orders.get(i).toString()).getInt(1);
                total = total + count;
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    public long getBuyOfferPriceByIndex(int index) throws JSONException {
        return new JSONArray(this.buy_offers.get(index).toString()).getLong(0);
    }

    public long getSellOfferPriceByIndex(int index) throws JSONException {
        return new JSONArray(this.sell_offers.get(index).toString()).getLong(0);
    }

    public long getHistoryTimeByIndex(int index) throws JSONException {
        return new JSONArray(this.history.get(index).toString()).getLong(0);
    }

    public long getHistoryPriceByIndex(int index) throws JSONException {
        return new JSONArray(this.history.get(index).toString()).getLong(1);
    }

    public void setHistory(JSONArray his) {
        this.history = new JSONArray();
        for (int i = 0; i < his.length(); i++) {
            try {
                JSONObject obj = his.getJSONObject(i);
                long price = obj.getLong("l_price");
                long time = obj.getLong("l_time");
                this.history.put(new JSONArray().put(time).put(price));
            } catch (JSONException ex) {
                Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
