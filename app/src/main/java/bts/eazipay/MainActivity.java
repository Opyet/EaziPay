package bts.eazipay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView bttnCustomer =null;
    ImageView bttnMerchant =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bttnCustomer = (ImageView)findViewById(R.id.bttnCustomer);
        bttnMerchant = (ImageView)findViewById(R.id.bttnMerchant);

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
                frmIntent.setClass(getApplicationContext() , HomeScreenActivity.class );

                startActivity(frmIntent);
            }
        });


    }
}
