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

    Button view_reports_button=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        bttnReceivePay = (LinearLayout)findViewById(R.id.bttnReceivePay);
        bttnDisbursePay = (LinearLayout)findViewById(R.id.bttnDisbursePay);
        view_reports_button=(Button)findViewById(R.id.view_reports_button) ;

        view_reports_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
