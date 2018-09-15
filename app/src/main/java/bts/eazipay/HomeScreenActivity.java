package bts.eazipay;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class HomeScreenActivity extends AppCompatActivity {

    ImageView bttnReceivePay =null;
    ImageView bttnDisbursePay =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        bttnReceivePay = (ImageView)findViewById(R.id.bttnReceivePay);
        bttnDisbursePay = (ImageView)findViewById(R.id.bttnDisbursePay);


        //Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //setSupportActionBar(myToolbar);

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
