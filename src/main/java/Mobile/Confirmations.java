/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mobile;

import Bot.Main;
import Bot.Steam;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JTextArea;
import org.json.JSONObject;

/**
 *
 * @author Константин
 */
public class Confirmations {

    private Steam steam;

    public Confirmations(Steam steamCommunity) {
        this.steam = steamCommunity;
    }

    public List<Confirmation> fetchConfirmations() throws Exception {
        String url = this.generateConfirmationUrl("conf");
        List<Confirmation> confirmations = new ArrayList<Confirmation>();
        String response = "";
        try {
            Map<String, String> params = new HashMap<String, String>();
            response = this.steam.cURL(url, null, params, true);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (response.indexOf("<div>Nothing to confirm</div>") != -1) {
            return confirmations;
        }
        if (response.indexOf("Invalid authenticator") != -1) {
            throw new Exception();
        }
        if (response.indexOf("<div>Nothing to confirm</div>") == -1) {
            String confIdRegex = "data-confid=\"(\\d+)\"";
            String confKeyRegex = "data-key=\"(\\d+)\"";
            String confOfferRegex = "data-creator=\"(\\d+)\"";
            String confDescRegex = "<div>((Confirm|Trade with|Sell -) .+?)<\\/div>";

            List<String> confIdMatches = new ArrayList<String>();
            Matcher matcher = Pattern.compile(confIdRegex).matcher(response);
            while (matcher.find()) {
                confIdMatches.add(matcher.group(1));
            }

            System.out.println("confIdMatches: " + confIdMatches.toString());

            List<String> confKeyMatches = new ArrayList<String>();
            matcher = Pattern.compile(confKeyRegex).matcher(response);
            while (matcher.find()) {
                confKeyMatches.add(matcher.group(1));
            }

            System.out.println("confKeyRegex: " + confKeyMatches.toString());

            List<String> confOfferMatches = new ArrayList<String>();
            matcher = Pattern.compile(confOfferRegex).matcher(response);
            while (matcher.find()) {
                confOfferMatches.add(matcher.group(1));
            }

            System.out.println("confOfferRegex: " + confOfferMatches.toString());

            List<String> confDescMatches = new ArrayList<String>();
            matcher = Pattern.compile(confDescRegex).matcher(response);
            while (matcher.find()) {
                confDescMatches.add(matcher.group(1));
            }

            System.out.println("confDescRegex: " + confOfferMatches.toString());

            if (confIdMatches.size() > 0 && confKeyMatches.size() > 0 && confOfferMatches.size() > 0) {
                List<String> checkedConfIds = new ArrayList<String>();

                for (int i = 0; i < confIdMatches.size(); i++) {
                    String confId = confIdMatches.get(i);

                    if (checkedConfIds.contains(confId)) {
                        continue;
                    }

                    try {
                        String confKey = confKeyMatches.get(i);
                        String confOfferId = confOfferMatches.get(i);
                        String confDesc = "Confirmation";
                        confirmations.add(new Confirmation(confId, confKey, confOfferId, confDesc));
                        checkedConfIds.add(confId);
                    } catch (Exception ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                throw new Exception();
            }
        }
        return confirmations;
    }

    public String generateConfirmationUrl(String tag) throws Exception {
        return "https://steamcommunity.com/mobileconf/conf?" + this.generateConfirmationQueryParams(tag);
    }

    public String generateConfirmationQueryParams(String tag) throws Exception {
        long time = steam.getSteamTime();
        return "p=" + this.steam.getDeviceId() + "&a=" + this.steam.getSteamId() + "&k=" + generateConfirmationHashForTime(time, tag) + "&t=" + time + "&m=android&tag=" + tag;
    }

    private String generateConfirmationHashForTime(long time, String tag) throws Exception {

        byte[] identitySecret = Base64.getDecoder().decode(this.steam.getIdentitySecret());
        int n2 = 8;
        if (tag != null) {
            if (tag.length() > 32) {
                n2 = 8 + 32;
            } else {
                n2 = 8 + tag.length();
            }
        }
        byte[] array = new byte[n2];
        int n3 = 8;
        while (true) {
            int n4 = n3 - 1;
            if (n3 <= 0) {
                break;
            }
            array[n4] = (byte) time;
            time >>= 8;
            n3 = n4;
        }
        System.arraycopy(tag.getBytes("UTF-8"), 0, array, 8, n2 - 8);

        byte[] code = hash_hmac(array, identitySecret);

        return Base64.getEncoder().encodeToString(code);
    }

    public byte[] hash_hmac(byte[] timeHash, byte[] sharedSecret)
            throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {

        Mac hasher = Mac.getInstance("HmacSHA1");
        hasher.init(new SecretKeySpec(sharedSecret, "HmacSHA1"));

        return hasher.doFinal(timeHash);
    }

    public String getConfirmationTradeOfferId(Confirmation confirmation) throws Exception {
        String url = "https://steamcommunity.com/mobileconf/details/" + confirmation.getConfirmationId() + "?" + generateConfirmationQueryParams("details");
        String response = "";
        try {
            Map<String, String> params = new HashMap<String, String>();
            response = this.steam.cURL(url, null, params, true);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!"".equals(response)) {
            JSONObject result = new JSONObject(response);
            if (result.getBoolean("success")) {
                String html = result.getString("html");
                List<String> tradeoffer = new ArrayList<String>();
                Matcher matcher = Pattern.compile("<div class=\"tradeoffer\" id=\"tradeofferid_(\\d+)\" >").matcher(html);
                while (matcher.find()) {
                    tradeoffer.add(matcher.group(1));
                }
                if (!"false".equals(tradeoffer.get(0))) {
                    return tradeoffer.get(0);
                }
            }

        }
        return "0";
    }

    public boolean acceptConfirmation(Confirmation confirmation) throws Exception {
        return this.sendConfirmationAjax(confirmation, "allow");
    }

    public boolean cancelConfirmation(Confirmation confirmation) throws Exception {
        return this.sendConfirmationAjax(confirmation, "cancel");
    }

    private boolean sendConfirmationAjax(Confirmation confirmation, String op) throws Exception {
        String url = "https://steamcommunity.com/mobileconf/ajaxop?op=" + op + "&" + generateConfirmationQueryParams(op) + "&cid=" + confirmation.getConfirmationId() + "&ck=" + confirmation.getConfirmationKey();
        String response = "";
        try {
            Map<String, String> params = new HashMap<String, String>();
            response = this.steam.cURL(url, null, params, true);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!"".equals(response)) {
            JSONObject result = new JSONObject(response);
            return result.getBoolean("success");
        }
        return false;
    }

}
