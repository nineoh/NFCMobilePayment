package ch.zhaw.wildlanfranchi.nfcmobilepayment.services;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Parcelable;

import java.nio.charset.Charset;

/**
 * Created by Nino Lanfranchi on 12.08.15.
 */
public class NfcService {

    public static NdefMessage createMessage(String text) {
        String mimeType = "application/ch.zhaw.wildlanfranchi.nfcmobilepayment";
        byte[] mimeBytes = mimeType.getBytes(Charset.forName("US-ASCII"));

        //GENERATE PAYLOAD
        byte[] payLoad = text.getBytes();

        //GENERATE NFC MESSAGE
        return new NdefMessage(
                new NdefRecord[]{
                        new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
                                mimeBytes,
                                null,
                                payLoad),
                        NdefRecord.createApplicationRecord("ch.zhaw.wildlanfranchi.nfcmobilepayment")
                });
    }

    public static String extractPayload(Intent beamIntent) {
        Parcelable[] messages = beamIntent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefMessage message = (NdefMessage) messages[0];
        NdefRecord record = message.getRecords()[0];
        String payload = new String(record.getPayload());

        return payload;
    }
}
