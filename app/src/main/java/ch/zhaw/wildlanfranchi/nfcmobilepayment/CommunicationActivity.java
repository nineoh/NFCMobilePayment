package ch.zhaw.wildlanfranchi.nfcmobilepayment;

import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ch.zhaw.wildlanfranchi.nfcmobilepayment.services.NfcService;

public class CommunicationActivity extends AppCompatActivity {
    EditText txtMessage;
    TextView tvMessageToBeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);

        txtMessage = (EditText) findViewById(R.id.txtMsg);
        tvMessageToBeam = (TextView) findViewById(R.id.tvMessageToBeam);
        initializeEventHandlers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
        this.findViewById(R.id.btnSendMessage).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tvMessageToBeam.setText(txtMessage.getText());
                        txtMessage.setText("");
                    }
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        //set Ndef message to send by beam
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        assert nfcAdapter != null;
        nfcAdapter.setNdefPushMessageCallback(
                new NfcAdapter.CreateNdefMessageCallback() {
                    @Override
                    public NdefMessage createNdefMessage(NfcEvent event) {
                        return NfcService.createMessage(tvMessageToBeam.getText().toString());
                    }
                }, this);

    }

}
