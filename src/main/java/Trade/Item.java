/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trade;

import Bot.Main;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author ktagintsev
 */
public class Item {
    
    private int appid;
    private String contextid;
    private String assetid;
    private String currencyId;
    private String classid;
    private String instanceid = "0";
    private long amount = 1;
    private boolean missing = false;

    public Item(JSONObject item)
    {
        this.appid = (int) getParam(item, "appid");
        this.contextid = (String) getParam(item, "contextid");
        this.assetid = (String) getParam(item, "assetid");
        this.classid = (String) getParam(item, "classid");
        this.instanceid = (String) getParam(item, "instanceid");
        this.missing = (boolean) getParam(item, "missing");
        
        System.out.println("####################################");
        System.out.println("appid: " + this.appid);
        System.out.println("contextid: " + this.contextid);
        System.out.println("assetid: " + this.assetid);
        System.out.println("classid: " + this.classid);
        System.out.println("instanceid: " + this.instanceid);
        System.out.println("missing: " + this.missing);
    }

    public Object getParam(JSONObject tradeOffer, String key){
        Object param = "";
        try {
            param = tradeOffer.get(key);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return param;
    }

    void getInfo() {
        System.out.println("       appid:" + this.appid);
        System.out.println("       contextid:" + this.contextid);
        System.out.println("       assetid:" + this.assetid);
        System.out.println("       currencyId:" + this.currencyId);
        System.out.println("       classid:" + this.classid);
        System.out.println("       instanceid:" + this.instanceid);
        System.out.println("       amount:" + this.amount);
        System.out.println("       missing:" + this.missing);
    }
}
