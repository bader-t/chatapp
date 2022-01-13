package ma.ensa.client.Utils;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class HashPassword {
    public static String getHash(String input) throws Exception{
        String hashValue="";
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        ByteArrayInputStream fis = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        byte[] dataBytes= new byte[1024];
        int nread=0;
        while ((nread=fis.read()) != -1){
            md.update(dataBytes,0,nread);
        }
        byte[] mdbytes = md.digest();

        //convert the byte to hex format
        StringBuffer hexString = new StringBuffer();
        for ( int i = 0 ; i < mdbytes.length;i++){
            hexString.append(Integer.toHexString(0xFF & mdbytes[i]));
        }
        hashValue=hexString.toString();
        return hashValue;
    }
}
