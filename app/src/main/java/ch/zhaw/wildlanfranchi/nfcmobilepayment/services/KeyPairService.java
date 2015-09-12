package ch.zhaw.wildlanfranchi.nfcmobilepayment.services;

import android.content.Context;
import android.security.KeyPairGeneratorSpec;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import javax.security.auth.x500.X500Principal;

import ch.zhaw.wildlanfranchi.nfcmobilepayment.R;

/**
 * Created by Nino Lanfranchi on 16.08.15.
 */
public class KeyPairService {

    public static KeyPair generateKeyPair(Context applicationContext) throws NoSuchProviderException, NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException, InvalidAlgorithmParameterException {
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        cal.add(Calendar.YEAR, 1);
        Date end = cal.getTime();
        String alias = applicationContext.getString(R.string.key_pair_alias);

        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");

        if (android.os.Build.VERSION.SDK_INT >= 18) {
            kpGen.initialize(new KeyPairGeneratorSpec.Builder(applicationContext)
                    .setAlias(alias)
                    .setStartDate(now)
                    .setEndDate(end)
                    .setSerialNumber(BigInteger.valueOf(1))
                    .setSubject(new X500Principal("CN=test1"))
                    .build());
        }

        return kpGen.generateKeyPair();
    }

    public static Enumeration<String> getKeyStoreEntries() throws CertificateException, NoSuchAlgorithmException, IOException, KeyStoreException {
        KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
        ks.load(null);
        return ks.aliases();
    }
}
