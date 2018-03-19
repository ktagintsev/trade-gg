/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot;

import Mobile.Confirmations;
import Trade.TradeOffers;
import Mobile.SteamGuard;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import static java.lang.Thread.sleep;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import org.json.JSONObject;

/**
 *
 * @author Константин
 */
public class Steam {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String loginOkay = "LoginOkay";
    public static final String generalFailure = "GeneralFailure";
    public static final String badRSA = "BadRSA";
    public static final String badCredentials = "BadCredentials";
    public static final String needCaptcha = "NeedCaptcha";
    public static final String need2FA = "Need2FA";
    public static final String needEmail = "NeedEmail";
    public static final String createdOkay = "CreatedOkay";

    private String username = "";
    private String password = "";
    private boolean mobile = false;
    private String sharedSecret;
    private String identitySecret;
    private String deviceId;
    private String apiKey;
    private String steamId = "";
    private String sessionId;
    private boolean requires2FA = false;
    public String twoFactorCode = "";
    private boolean loggedIn = false;
    private long loggedInTime = 0;

    private TradeOffers tradeOffers;
    private HashMap<String, String> oauth;
    private CookieManager userCookie = new CookieManager();
    private SteamGuard steamGuard;
    private Confirmations confirmations;

    Steam(Map<String, Object> params) throws Exception {

        if (params.containsKey("username")) {
            this.username = (String) params.get("username");
        }
        if (params.containsKey("password")) {
            this.password = (String) params.get("password");
        }
        if (params.containsKey("apiKey")) {
            this.apiKey = (String) params.get("apiKey");
        }

        if (params.containsKey("mobileAuth")) {
            Map<String, String> mobileAuth = (Map<String, String>) params.get("mobileAuth");
            this.sharedSecret = mobileAuth.get("sharedSecret");
            this.identitySecret = mobileAuth.get("identitySecret");
            this.deviceId = mobileAuth.get("deviceId");
        }

        this.steamGuard = new SteamGuard(this.sharedSecret, this);
        this.confirmations = new Confirmations(this);

    }

    public void setSession(boolean isMobile, long updateAuthInterval) {

        if (0 != this.loggedInTime) {
            long curentTime = System.currentTimeMillis() / 1000;
            long diff = curentTime - this.loggedInTime;
            if (diff > updateAuthInterval) {
               userCookie.getStore().clear();
               this.loggedIn = false;
            }
        }

        Map<String, String> params = new HashMap<String, String>();
        try {
            String response = "";
            if (isMobile) {
                response = cURL("https://steamcommunity.com/login?oauth_client_id=DE45CD61&oauth_scope=read_profile%20write_profile%20read_client%20write_client", null, params, isMobile);
            } else {
                response = cURL("http://steamcommunity.com/", null, params, isMobile);
            }
            List<String> steamIDs = new ArrayList<String>();
            Matcher matcher = Pattern.compile("g_steamID = (.*?);").matcher(response);
            while (matcher.find()) {
                steamIDs.add(matcher.group(1));
            }

            if ("false".equals(steamIDs.get(0)) && "".equals(this.steamId)) {
                this.steamId = "0";
            } else {
                String steamId = steamIDs.get(0).replaceAll("\"", "");
                if (!"false".equals(steamId)) {
                    this.steamId = steamIDs.get(0).replaceAll("\"", "");
                }
            }

            System.out.println("g_steamID: " + this.steamId);

            List<String> sessionIDs = new ArrayList<String>();
            matcher = Pattern.compile("g_sessionID = (.*?);").matcher(response);
            while (matcher.find()) {
                sessionIDs.add(matcher.group(1));
            }
            String g_sessionID = sessionIDs.get(0).replaceAll("\"", "");
            if (!"".equals(g_sessionID)) {
                this.sessionId = g_sessionID;
            }

            System.out.println("g_sessionID: " + this.sessionId);

        } catch (Exception ex) {
            Logger.getLogger(Steam.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String cURL(String url, String ref, Map<String, String> params, boolean isMobile) throws Exception {
        try {
            URL obj = new URL(url);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setInstanceFollowRedirects(true);
            HttpURLConnection.setFollowRedirects(true);

            String cookies = "";
            if (isMobile) {
                cookies = "mobileClientVersion=0 (2.1.3); mobileClient=android; Steam_Language=english; dob=;";
                con.setRequestProperty("Accept", "*/*");
                con.setRequestProperty("X-Requested-With", "com.valvesoftware.android.steam.community");
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.1.1; en-us; Google Nexus 4 - 4.1.1 - API 16 - 768x1280 Build/JRO03S) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
            } else {
                con.setRequestProperty("Accept", "*/*");
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
            }

            cookies = userCookie.setCookies(con, cookies);
            con.setRequestProperty("Cookie", cookies);

            if (!"".equals(ref)) {
                con.setRequestProperty("Referer", ref);
            }

            if (!params.isEmpty()) {
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                String urlParameters = "";
                con.setRequestMethod("POST");
                int i = 0;
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    entry.getKey();
                    entry.getValue();
                    if (i++ == params.size() - 1) {
                        urlParameters = urlParameters + entry.getKey() + "=" + entry.getValue();
                    } else {
                        urlParameters = urlParameters + entry.getKey() + "=" + entry.getValue() + "&";
                    }

                }
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();
                System.out.println("Sending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters);
            } else {
                con.setRequestMethod("GET");
                System.out.println("Sending 'GET' request to URL : " + url);
            }

            int responseCode = con.getResponseCode();

            boolean redirect = false;
            if (responseCode != HttpURLConnection.HTTP_OK) {
                if (responseCode == HttpURLConnection.HTTP_MOVED_TEMP
                        || responseCode == HttpURLConnection.HTTP_MOVED_PERM
                        || responseCode == HttpURLConnection.HTTP_SEE_OTHER) {
                    redirect = true;
                }
            }

            System.out.println("Response Code : " + responseCode);

            if (redirect) {

                String newUrl = con.getHeaderField("Location");
                System.out.println(newUrl);
                con = (HttpURLConnection) new URL(newUrl).openConnection();
                con.setRequestProperty("Cookie", cookies);
                if (isMobile) {
                    con.setRequestProperty("X-Requested-With", "com.valvesoftware.android.steam.community");
                    con.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.1.1; en-us; Google Nexus 4 - 4.1.1 - API 16 - 768x1280 Build/JRO03S) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
                } else {
                    con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
                }

                System.out.println("Redirect to URL : " + newUrl);

            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            userCookie.storeCookies(con);
            return response.toString();

        } catch (Exception ex) {
            Logger.getLogger(Steam.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";

    }

    public String doLogin(boolean isMobile) throws Exception {

        if (this.loggedIn) {
            return loginOkay;
        }

        Map<String, String> params = new HashMap<String, String>();
        params.put("username", this.username);

        String rsaResponse = cURL("https://steamcommunity.com/login/getrsakey", null, params, isMobile);

        JSONObject result = new JSONObject(rsaResponse);
        if ("".equals(rsaResponse)) {
            return generalFailure;
        }

        boolean rsa = result.getBoolean("success");
        if (!rsa) {
            return badRSA;
        }

        String publickey_mod = result.getString("publickey_mod");
        String publickey_exp = result.getString("publickey_exp");
        String rsatimestamp = result.getString("timestamp");

        BigInteger modulus = new BigInteger(publickey_mod, 16);
        BigInteger exp = new BigInteger(publickey_exp, 16);

        byte[] encrypt = encrypt(this.password.getBytes(), modulus, exp);
        String encryptedPassword = URLEncoder.encode(Base64.getEncoder().encodeToString(encrypt), "UTF-8");

        params.put("username", this.username);
        params.put("password", encryptedPassword);
        params.put("twofactorcode", getTwoFactorCode());
        params.put("captchagid", "-1");
        params.put("captcha_text", "");
        params.put("emailsteamid", "");
        params.put("emailauth", "");
        params.put("rsatimestamp", rsatimestamp);
        params.put("remember_login", "false");

        if (isMobile) {
            params.put("oauth_client_id", "DE45CD61");
            params.put("oauth_scope", "read_profile write_profile read_client write_client");
            params.put("loginfriendlyname", "#login_emailauth_friendlyname_mobile");
        }

        String loginResponse = cURL("https://steamcommunity.com/login/dologin/", null, params, isMobile);

        result = new JSONObject(loginResponse);

        System.out.println("Do Login result: " + loginResponse + " isMobile: " + isMobile);

        boolean success = getBooleanParam(result, "success");
        boolean captcha_needed = getBooleanParam(result, "captcha_needed");
        boolean emailauth_needed = getBooleanParam(result, "emailauth_needed");
        boolean requires_twofactor = getBooleanParam(result, "requires_twofactor");
        boolean login_complete = getBooleanParam(result, "login_complete");

        if ("".equals(loginResponse)) {
            return generalFailure;
        } else if (captcha_needed) {
            return needCaptcha;
        } else if (emailauth_needed) {
            return needEmail;
        } else if (requires_twofactor && !success) {
            this.requires2FA = true;
            return need2FA;
        } else if (!login_complete) {
            return badCredentials;
        } else if (success) {

            try {
                String oauth = result.getString("oauth");
                result = new JSONObject(oauth.toString());
                String steamid = result.getString("steamid");
                String account_name = result.getString("account_name");
                String oauth_token = result.getString("oauth_token");
                String wgtoken = result.getString("wgtoken");
                String wgtoken_secure = result.getString("wgtoken_secure");
                String webcookie = result.getString("webcookie");

                this.oauth = new HashMap<String, String>();
                this.oauth.put("steamid", steamid);
                this.oauth.put("account_name", account_name);
                this.oauth.put("oauth_token", oauth_token);
                this.oauth.put("wgtoken", wgtoken);
                this.oauth.put("wgtoken_secure", wgtoken_secure);
                this.oauth.put("webcookie", webcookie);

                System.out.println("steamid " + steamid);
                System.out.println("account_name " + account_name);
                System.out.println("oauth_token " + oauth_token);
                System.out.println("wgtoken " + wgtoken);
                System.out.println("wgtoken_secure " + wgtoken_secure);
                System.out.println("webcookie " + webcookie);
            } catch (Exception ex) {
                System.out.println(ANSI_RED + ex.getMessage());
            }

            this.loggedIn = true;
            this.loggedInTime = System.currentTimeMillis() / 1000;
            return loginOkay;

        }
        return generalFailure;
    }

    private static byte[] encrypt(byte[] b1, BigInteger modulus, BigInteger pubExp) throws Exception {

        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, pubExp);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(keySpec);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] plainText = cipher.doFinal(b1);

        return plainText;
    }

    public String getTwoFactorCode() throws Exception {
        String code = steamGuard.generateSteamGuardCode();
        this.twoFactorCode = code;
        while (this.twoFactorCode.equals(code)) {
            sleep(1000);
            System.out.println("Generate fa code!");
            code = steamGuard.generateSteamGuardCode();
        }
        this.twoFactorCode = code;
        return code;
    }

    public long getSteamTime() {
        return (System.currentTimeMillis() / 1000) + getTimeDifference();
    }

    public long getTimeDifference() {
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("steamid", "0");
            String response = cURL("http://api.steampowered.com/ITwoFactorService/QueryTime/v0001", null, params, false);
            JSONObject result = new JSONObject(response);
            result = new JSONObject(result.get("response").toString());
            long server_time = result.getLong("server_time");
            long difference = server_time - System.currentTimeMillis() / 1000;
            return difference;
        } catch (Exception ex) {
            Logger.getLogger(Steam.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public void refreshMobileSession() {
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("access_token", this.oauth.get("oauth_token"));
            String response = cURL("https://api.steampowered.com/IMobileAuthService/GetWGToken/v0001", null, params, true);
            JSONObject result = new JSONObject(new JSONObject(response).getString("response"));
            String token = result.getString("token");
            String token_secure = result.getString("token_secure");
            oauth.put("wgtoken", token);
            oauth.put("wgtoken_secure", token_secure);
        } catch (Exception ex) {
            Logger.getLogger(Steam.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public String getSteamId() {
        return this.steamId;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setTwoFactorCode(String twoFactorCode) {
        this.twoFactorCode = twoFactorCode;
    }

    public TradeOffers tradeOffers() {
        return this.tradeOffers;
    }

    public CookieManager getUserCookie() {
        return userCookie;
    }

    public String getIdentitySecret() {
        return identitySecret;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Confirmations getConfirmations() {
        return confirmations;
    }

    public boolean getBooleanParam(JSONObject data, String key) {
        try {
            return data.getBoolean(key);
        } catch (Exception ex) {
            System.out.println(ANSI_RED + ex.getMessage());
            return false;
        }
    }

    public SteamGuard getSteamGuard() {
        return steamGuard;
    }

}
