package jp.nfcgroup.tabekuranavi.util;

import java.io.UnsupportedEncodingException;

public class NfcUtil {

    public static String getText(byte[] payload) throws UnsupportedEncodingException {
        String encode = ((payload[0] & 0200) == 0)?"UTF-8":"UTF-16";
        int length = payload[0] & 0077;
        //String languageCode = new String(payload,1,length,"US-ASCII");
        return new String(payload,length+1,payload.length-length-1,encode);
    }
    
}
