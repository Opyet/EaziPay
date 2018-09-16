package bts.eazipay;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class HomeScreenActivity extends AppCompatActivity {

    ImageView bttnReceivePay =null;
    ImageView bttnDisbursePay =null;
    Button bttnReport=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        bttnReceivePay = (ImageView)findViewById(R.id.bttnReceivePay);
        bttnDisbursePay = (ImageView)findViewById(R.id.bttnDisbursePay);
        bttnReport=(Button) findViewById(R.id.bttnReport);

        bttnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent frmIntent= new Intent();
                frmIntent.setClass(getApplicationContext() , HistoryActivity.class );

                startActivity(frmIntent);
            }
        });

        bttnReceivePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent frmIntent= new Intent();
                frmIntent.setClass(getApplicationContext() , ReceivePaymentActivity.class );

                startActivity(frmIntent);
            }
        });


        bttnDisbursePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent frmIntent= new Intent();
                frmIntent.setClass(getApplicationContext() , DisburseActivity.class );

                startActivity(frmIntent);
            }
        });


    }

}
