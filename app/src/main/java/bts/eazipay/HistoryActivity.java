package bts.eazipay;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.zip.Inflater;

import javax.xml.transform.TransformerFactoryConfigurationError;

import bts.eazipay.Adapter.TransfersDashAdapter;
import bts.eazipay.helper.TransferFundsInfo;
import bts.eazipay.helper.UtilityHelper;

public class HistoryActivity extends AppCompatActivity {

    // This is the Adapter being used to display the list's data
    ListView transaction_history_list;
    String itemsListA[] = {"Smooth 1", "Smooth 2", "Smooth 3", "Smooth 4", "Smooth 5", "Smooth 6"};
    String itemsListB[] = {"2000", "50000", "5000", "40000", "6000", "8400"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        transaction_history_list = (ListView) findViewById(R.id.transaction_history_list);

        List<TransferFundsInfo> iList=new ArrayList<>();


       try {
           int cnt=0;
           for (int i = 0; i < 100; i++) {
                cnt=++cnt;
               TransferFundsInfo tInfo=new TransferFundsInfo();
               //String  uniqueID = UUID.randomUUID().toString();
               long generatedLong = new Random().nextLong();
              String uniqueID =String.valueOf(generatedLong);
               tInfo.Amount=2000 * cnt;
               tInfo.setTransactionDetails("Smooth " + cnt);
               tInfo.setDateCreated(UtilityHelper.getValidDateAsString());
               tInfo.setTransactionCode(uniqueID.substring(0,7));
               iList.add(tInfo);
           }


           TransfersDashAdapter customAdapter = new TransfersDashAdapter(getApplicationContext(), iList);
           transaction_history_list.setAdapter(customAdapter);
       }catch (Exception ex){
           Log.e("",ex.getMessage());
       }

    }
}
