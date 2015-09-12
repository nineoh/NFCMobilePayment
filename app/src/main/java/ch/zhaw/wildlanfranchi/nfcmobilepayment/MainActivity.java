package ch.zhaw.wildlanfranchi.nfcmobilepayment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.Objects;

import ch.zhaw.wildlanfranchi.nfcmobilepayment.enrollment.EnrollmentActivity;
import ch.zhaw.wildlanfranchi.nfcmobilepayment.services.KeyPairService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Fix this in KeyPairService
        // Check if enrollment has been made.
        try {
            boolean isEnrollmentCompleted = android.os.Build.VERSION.SDK_INT < 19;

            if (android.os.Build.VERSION.SDK_INT >= 19) {
                Enumeration aliases = KeyPairService.getKeyStoreEntries();

                while (aliases.hasMoreElements()) {
                    if (Objects.equals(aliases.nextElement().toString(), getString(R.string.key_pair_alias))) {
                        isEnrollmentCompleted = true;
                        break;
                    }
                }
            }

            if (!isEnrollmentCompleted) {
                Intent intent = new Intent(getApplicationContext(), EnrollmentActivity.class);
                startActivity(intent);
                return;
            }
            Toast.makeText(getApplicationContext(), "Enrollment Key found :)", Toast.LENGTH_LONG).show();

            initializeEventHandlers();

        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeEventHandlers() {
        this.findViewById(R.id.btnCommunicate).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), CommunicationActivity.class);
                        startActivity(intent);
                    }
                }
        );

        this.findViewById(R.id.btnCheckSaldo).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

}
