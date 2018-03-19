package Bot;

import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class CookieManager {

    private Map store = new HashMap();

    private static final String SET_COOKIE = "Set-Cookie";
    private static final String COOKIE_VALUE_DELIMITER = ";";
    private static final String PATH = "path";
    private static final String SECURE = "secure";
    private static final String EXPIRES = "expires";
    private static final String DATE_FORMAT = "EEE, dd-MMM-yyyy hh:mm:ss z";
    private static final String SET_COOKIE_SEPARATOR = "; ";
    private static final String COOKIE = "Cookie";

    private static final char NAME_VALUE_SEPARATOR = '=';
    private static final char DOT = '.';

    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);

    public void storeCookies(URLConnection conn) throws IOException {

        String domain = getDomainFromHost(conn.getURL().getHost());

        Map domainStore;

        if (store.containsKey(domain)) {
            domainStore = (Map) store.get(domain);
        } else {
            domainStore = new HashMap();
            store.put(domain, domainStore);
        }

        String headerName = null;
        for (int i = 1; (headerName = conn.getHeaderFieldKey(i)) != null; i++) {
            if (headerName.equalsIgnoreCase(SET_COOKIE)) {
                Map cookie = new HashMap();
                StringTokenizer st = new StringTokenizer(conn.getHeaderField(i), COOKIE_VALUE_DELIMITER);

                if (st.hasMoreTokens()) {
                    String token = st.nextToken().trim();
                    System.out.println("Token: " + token);
                    String name = token.substring(0, token.indexOf(NAME_VALUE_SEPARATOR));
                    String value = token.substring(token.indexOf(NAME_VALUE_SEPARATOR) + 1, token.length());
                    domainStore.put(name, cookie);
                    cookie.put(name, value);

                }

                while (st.hasMoreTokens()) {
                    String token = st.nextToken().trim();
                    System.out.println("Token: " + token);
                    if ("secure".equals(token) || "HttpOnly".equals(token)) {
                        cookie.put(token, token);
                    } else {
                        cookie.put(token.substring(0, token.indexOf(NAME_VALUE_SEPARATOR)).toLowerCase(),
                                token.substring(token.indexOf(NAME_VALUE_SEPARATOR) + 1, token.length()));
                    }
                }
            }
        }
    }

    public String setCookies(URLConnection conn, String cookies) throws IOException {

        URL url = conn.getURL();
        String domain = getDomainFromHost(url.getHost());
        String path = url.getPath();
        String protocol = url.getProtocol();

        Map domainStore = (Map) store.get(domain);
        if (domainStore == null) {
            System.out.println("\nSET COOCKIE: " + cookies);
            return cookies;
        }
        StringBuffer cookieStringBuffer = new StringBuffer();

        Iterator cookieNames = domainStore.keySet().iterator();
        while (cookieNames.hasNext()) {
            String cookieName = (String) cookieNames.next();
            Map cookie = (Map) domainStore.get(cookieName);
            if (comparePaths((String) cookie.get(PATH), path) && isNotExpired((String) cookie.get(EXPIRES))) {
                if (cookieName.equals("secure")
                        || cookieName.equals("HttpOnly")
                        || (protocol.equals("http") && SECURE.equals(cookie.get(SECURE)))) {
                    continue;
                }
                cookieStringBuffer.append(cookieName);
                cookieStringBuffer.append("=");
                cookieStringBuffer.append((String) cookie.get(cookieName));
                if (cookieNames.hasNext()) {
                    cookieStringBuffer.append(SET_COOKIE_SEPARATOR);
                }
            }
        }
        try {
            String bufferCookies = cookieStringBuffer.toString();
            if(!"".equals(cookies) && bufferCookies.length() > 0){
                bufferCookies = " " + cookieStringBuffer.toString();
            }
            System.out.println("\nSET COOCKIE: " + cookies + bufferCookies);
            return cookies + bufferCookies;
        } catch (java.lang.IllegalStateException ise) {
            IOException ioe = new IOException("Illegal State! Cookies cannot be set on a URLConnection that is already connected. "
                    + "Only call setCookies(java.net.URLConnection) AFTER calling java.net.URLConnection.connect().");
            throw ioe;
        }
    }

    private String getDomainFromHost(String host) {
        if (host.indexOf(DOT) != host.lastIndexOf(DOT)) {
            return host.substring(host.indexOf(DOT) + 1);
        } else {
            return host;
        }
    }

    private boolean isNotExpired(String cookieExpires) {
        if (cookieExpires == null) {
            return true;
        }
        Date now = new Date();
        try {
            return (now.compareTo(dateFormat.parse(cookieExpires))) <= 0;
        } catch (java.text.ParseException pe) {
            pe.printStackTrace();
            return false;
        }
    }

    private boolean comparePaths(String cookiePath, String targetPath) {
        if (cookiePath == null) {
            return true;
        } else if (cookiePath.equals("/")) {
            return true;
        } else if (targetPath.regionMatches(0, cookiePath, 0, cookiePath.length())) {
            return true;
        } else {
            return false;
        }

    }

    public String toString() {
        return store.toString();
    }

    public Map getStore() {
        return store;
    }

}
