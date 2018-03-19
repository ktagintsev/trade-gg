package Mobile;

import Bot.Steam;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author ktagintsev
 */
public class SteamGuard {

    private Steam steam;
    private String sharedSecret = "";
    int[] codeTranslations = {50, 51, 52, 53, 54, 55, 56, 57, 66, 67, 68, 70, 71, 72, 74, 75, 77, 78, 80, 81, 82, 84, 86, 87, 88, 89};

    public SteamGuard(String sharedSecret, Steam steam) {
        this.sharedSecret = sharedSecret;
        this.steam = steam;
    }

    public String generateSteamGuardCode() throws Exception {
        return generateSteamGuardCodeForTime(steam.getSteamTime());
    }

    public String generateSteamGuardCodeForTime(long time) throws Exception {
        if ("".equals(this.sharedSecret)) {
            return "";
        }
        byte[] sharedSecret = Base64.getDecoder().decode(this.sharedSecret);
        byte[] timeHash = concat(packN(0), packN((int) Math.floor(time / 30)));
        byte[] hash_hmac = hash_hmac(timeHash, sharedSecret);
        int b = (hash_hmac[19] & 0xF) & 0xff;
        int codePoint = (hash_hmac[b] & 0x7F) << 24 | (hash_hmac[b + 1] & 0xFF) << 16 | (hash_hmac[b + 2] & 0xFF) << 8 | (hash_hmac[b + 3] & 0xFF);      
        String code = "";
        for (int i = 0; i < 5; ++i) {
            code = code + (char) codeTranslations[codePoint % 26];
            codePoint = codePoint / 26;
        }
        return code;
    }

    public byte[] hash_hmac(byte[] timeHash, byte[] sharedSecret)
            throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {

        Mac hasher = Mac.getInstance("HmacSHA1");
        hasher.init(new SecretKeySpec(sharedSecret, "HmacSHA1"));

        return hasher.doFinal(timeHash);
    }

    public byte[] packN(int value) throws UnsupportedEncodingException {
        byte[] bytes = ByteBuffer.allocate(4).putInt(new Integer(value)).array();
        return bytes;
    }

    public byte[] concat(byte[] a, byte[] b) {
        final byte[] ret = new byte[a.length + b.length];
        System.arraycopy(a, 0, ret, 0, a.length);
        System.arraycopy(b, 0, ret, a.length, b.length);
        return ret;
    }
    

}
