package jp.nfcgroup.tabekuranavi.util;

public class TagInfo {
    public static final String[][] TECH_LIST = {
    	// NTAG203
    	{
    		android.nfc.tech.Ndef.class.getName(),
    		android.nfc.tech.MifareUltralight.class.getName(),
    		android.nfc.tech.NfcA.class.getName()
    	},
    	// Mifare Classic
    	{
    		android.nfc.tech.Ndef.class.getName(),
    		android.nfc.tech.MifareClassic.class.getName(),
    		android.nfc.tech.NfcA.class.getName()
    	}
    	/*
        {
            android.nfc.tech.NfcA.class.getName(),
            android.nfc.tech.NfcB.class.getName(),
            android.nfc.tech.IsoDep.class.getName(),
            android.nfc.tech.MifareClassic.class.getName(),
            android.nfc.tech.MifareUltralight.class.getName(),
            android.nfc.tech.NdefFormatable.class.getName(),
            android.nfc.tech.NfcV.class.getName(),
            android.nfc.tech.NfcF.class.getName(),
        }
        */
    };
}
