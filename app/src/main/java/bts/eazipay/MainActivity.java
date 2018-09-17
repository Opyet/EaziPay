package bts.eazipay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    LinearLayout bttnCustomer =null;
    RelativeLayout bttnMerchant =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bttnCustomer = (LinearLayout)findViewById(R.id.bttnCustomer);
        bttnMerchant = (RelativeLayout) findViewById(R.id.bttnMerchant);

        bttnMerchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent frmIntent= new Intent();
                frmIntent.setClass(getApplicationContext() , LoginActivity.class );

                startActivity(frmIntent);
            }
        });


        bttnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent frmIntent= new Intent();
                frmIntent.setClass(getApplicationContext() , RegisterCustomerActivity.class );

                startActivity(frmIntent);
            }
        });


    }
}
