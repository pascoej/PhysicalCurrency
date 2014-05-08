
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by john on 5/3/14.
 */
public class Hash {
    public static void main(String[] args) {
        System.out.println(hash("teoumbvwbwas",30));
    }
    public static String hash(String base, int length) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]).toUpperCase();
                if (hex.length() == 1)
                    stringBuffer.append('0');
                stringBuffer.append(hex);
            }
            return stringBuffer.toString().substring(0,length-1);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "error";
    }
}
