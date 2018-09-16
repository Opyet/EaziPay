package bts.eazipay;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

import bts.eazipay.Adapter.BanksAdapter;
import bts.eazipay.Data.DataAccessMethods;
import bts.eazipay.Data.DataBaseCreateHelper;
import bts.eazipay.helper.AlertMessageBox;
import bts.eazipay.helper.BanksInfo;
import bts.eazipay.helper.UtilityHelper;

public class ReceivePaymentActivity extends AppCompatActivity {
    DataBaseCreateHelper db;
    Switch swtInternetMode=null;
    String tag=this.getClass().getName();
    Spinner cmbBanks=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_payment);

        cmbBanks=(Spinner)findViewById(R.id.cmbBanks) ;
        db=new DataBaseCreateHelper(this);

        swtInternetMode=(Switch)findViewById(R.id.swtInternetMode);

        swtInternetMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){

                    swtInternetMode.setText("Online");
                }else{
                    swtInternetMode.setText("Offline");
                    try {
                        AlertMessageBox.Show(getApplicationContext(),
                                "Offline Mode",
                                "Application Would use USSD (*8472***#)",
                                AlertMessageBox.AlertMessageBoxIcon.Info);
                    }catch (Exception ex)
                    {
                        Log.e(tag,ex.getMessage());
                    }
                }
            }
        });
    }

    private void LoadBanksOnline(){

        try {
            if (UtilityHelper.hasNetwork(this)) {
                final ProgressDialog dialog = new ProgressDialog(this);
                new LoadBanksOnlineTask() {
                    @Override
                    protected void onPreExecute() {
                        //dialog = new ProgressDialog(DataPreparationActivity.this);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setMessage("Getting all Registered Banks ...");
                        dialog.show();
                    }
                    @Override
                    protected void onPostExecute( ArrayList<BanksInfo> result) {

                        dialog.dismiss();
                        if (result != null) {
                            BanksAdapter adapter = new BanksAdapter(getApplicationContext(),result);
                            // apply the Adapter:

                            cmbBanks.setAdapter(adapter);
                            for (BanksInfo banksInfo : result) {

                                BanksInfo bOld=db.BanksByCodeGet(banksInfo.getBankCode());
                                if (bOld ==null || bOld.getId()==0) {

                                    int saved=db.BanksAdd(banksInfo);

                                }
                            }




                        }else {

                            Toast.makeText(getApplicationContext(),"Invalid Username or Password", Toast.LENGTH_LONG).show();
                        }
                        dialog.hide();
                        dialog.dismiss();
                    }
                }.execute();
            }else{
                AlertMessageBox.Show(this,
                        "No Network Connection",
                        "Please ensure you have a valid internet connection.",
                        AlertMessageBox.AlertMessageBoxIcon.Error);

            }
        } catch (Exception e) {
            Log.e("", e.getMessage() + e.getStackTrace());
        }
    }

    private class LoadBanksOnlineTask extends AsyncTask<Void, Integer, ArrayList<BanksInfo>> {
        @Override
        protected ArrayList<BanksInfo> doInBackground(Void... args){

            DataAccessMethods rfidacc=new DataAccessMethods();
            return rfidacc.LoadBanks();

        }


    }



    private void VerifyAccountNumber(String acctNumber,String bankCode){

        try {
            if (UtilityHelper.hasNetwork(this) ){
                final ProgressDialog dialog = new ProgressDialog(this);
                new VerifyAccountNumberTask() {
                    @Override
                    protected void onPreExecute() {
                        //dialog = new ProgressDialog(DataPreparationActivity.this);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setMessage("Verifying Account Please Wait ...");
                        dialog.show();
                    }
                    @Override
                    protected void onPostExecute( String result) {

                        dialog.dismiss();
                        if (result != null) {

                           // txtReceiverName.setText(result);

                        }else {

                            Toast.makeText(getApplicationContext(),"Could Not Retrive Account Name. Ensure you entered a valid Account No. Also Confirm you have a valid Internet Connection.", Toast.LENGTH_LONG).show();
                        }
                        dialog.hide();
                        dialog.dismiss();
                    }
                }.execute(acctNumber,bankCode);
            }else{
                AlertMessageBox.Show(this,
                        "No Network Connection",
                        "Please ensure you have a valid internet connection.",
                        AlertMessageBox.AlertMessageBoxIcon.Error);

            }
        } catch (Exception e) {
            Log.e("", e.getMessage() + e.getStackTrace());
        }
    }

    private class VerifyAccountNumberTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... args){

            DataAccessMethods rfidacc=new DataAccessMethods();
            String account_number=args[0].toString();
            String bank_code=args[1].toString();
            return rfidacc.VerifyAccountNumber(account_number, bank_code);

        }


    }

}
