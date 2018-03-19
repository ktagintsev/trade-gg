/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trade;

import Bot.Http;
import Bot.Main;
import Bot.Steam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Константин
 */
public class TradeOffers {

    public final String BASE_URL = "http://steamcommunity.com/my/tradeoffers/";

    private Steam steam;
    Http http = new Http();

    public TradeOffers(Steam steamCommunity) {
        this.steam = steamCommunity;
    }

    public List<TradeOffer> getTradeOffersViaAPI(boolean activeOnly) throws Exception {

        String url = "https://api.steampowered.com/IEconService/GetTradeOffers/v1/?key=" + steam.getApiKey() + "&get_sent_offers=1&get_received_offers=1";
        if (activeOnly) {
            url = url + "&active_only=1&time_historical_cutoff=" + System.currentTimeMillis() / 1000;
        } else {
            url = url + "&active_only=0";
        }
        String response = http.sendGet(url);
        List<TradeOffer> tOffers = new ArrayList<TradeOffer>();

        JSONObject result = new JSONObject(response);
        String res = result.getString("response");
        result = new JSONObject(res);
        try {
            String trade_offers_received = result.getString("trade_offers_received");
            JSONArray tradeOffers = new JSONArray(trade_offers_received);
            for (int i = 0; i < tradeOffers.length(); i++) {
                TradeOffer tradeOffer = new TradeOffer(new JSONObject(tradeOffers.get(i).toString()));
                tOffers.add(tradeOffer);
            }

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            String trade_offers_received = result.getString("trade_offers_sent");
            JSONArray tradeOffers = new JSONArray(trade_offers_received);
            for (int i = 0; i < tradeOffers.length(); i++) {
                TradeOffer tradeOffer = new TradeOffer(new JSONObject(tradeOffers.get(i).toString()));
                tOffers.add(tradeOffer);
            }

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        return tOffers;
    }

    public boolean acceptTrade(TradeOffer tradeOffer) throws Exception
    {
        boolean result = false;
        if (!tradeOffer.is_our_offer()) {
            String url = "https://steamcommunity.com/tradeoffer/" + tradeOffer.getTradeofferid() + "/accept";
            String referer = "https://steamcommunity.com/tradeoffer/" + tradeOffer.getTradeofferid() + "/";
            Map<String, String> params = new HashMap<String, String>();
            params.put("serverid", "1");
            params.put("sessionid", this.steam.getSessionId());
            params.put("tradeofferid", tradeOffer.getTradeofferid());
            String accountid_other = Integer.toString(tradeOffer.getAccountid_other());
            String partner = Long.toString(toCommunityID(accountid_other));
            params.put("partner", partner);

            String response = this.steam.cURL(url, referer, params, false);
            System.out.println(response);
            if("true".equals(response)){
                result = true;
            }           
        }
        return result;
    }
    
    public long toCommunityID(String id) {
        if (id.matches("^STEAM_")) {
            String[] parts = id.split(":");
            return (Long.parseLong(parts[2]) * 2) + 76561197960265728L + Long.parseLong(parts[1]);
        } else if (id.length() < 16) {
            return Long.parseLong(id) + 76561197960265728L;
        } else {
            return Long.parseLong(id);
        }
    }

}
