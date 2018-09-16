package bts.eazipay;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class HomeScreenActivity extends AppCompatActivity {

    LinearLayout bttnReceivePay =null;
    LinearLayout bttnDisbursePay =null;
    Button reportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

//        toolbar = (Toolbar) findViewById(R.id.toolbar_tournament_home);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_store_24px);


        bttnReceivePay = (LinearLayout)findViewById(R.id.bttnReceivePay);
        bttnDisbursePay = (LinearLayout)findViewById(R.id.bttnDisbursePay);

        reportButton = (Button)findViewById(R.id.view_reports_button);


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

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent frmIntent= new Intent();
                frmIntent.setClass(getApplicationContext() , HistoryActivity.class );

                startActivity(frmIntent);
            }
        });


    }
}
