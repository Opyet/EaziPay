package bts.eazipay;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
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
    //Switch swtAcctType=null;
   //Spinner cmbBanks=null;
    Button bttnContinue=null;
    EditText txtAmount=null;
    EditText txtMobileNo=null;

    String tag=this.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_payment);

        //cmbBanks=(Spinner)findViewById(R.id.cmbBanks) ;
        txtAmount=(EditText)findViewById(R.id.txtAmount) ;
        txtMobileNo=(EditText)findViewById(R.id.txtMobileNo);

        bttnContinue=(Button)findViewById(R.id.bttnContinue) ;

        bttnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!swtInternetMode.isChecked() ){

                    String encodedHash = Uri.encode("#");
                    String uri = "tel: *8472*" + txtAmount.getText().toString() + "*"
                            + txtMobileNo.getText().toString() + encodedHash ;
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(uri));
                    // startActivityForResult(intent, 2);
                    startActivity(intent);
                }else{

                    ShowPopUpOTPRegistration();
                }

            }
        });

        db=new DataBaseCreateHelper(this);

        swtInternetMode=(Switch)findViewById(R.id.swtInternetMode);
        //swtAcctType=(Switch)findViewById(R.id.swtAcctType);



        swtInternetMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){

                    swtInternetMode.setText("Online Mode");
                }else{
                    swtInternetMode.setText("Offline Mode");
                    try {
                        AlertMessageBox.Show(ReceivePaymentActivity.this,
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
       /* swtAcctType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){

                    swtAcctType.setText("Current Account");
                }else{
                    swtAcctType.setText("Savings Account");

                }
            }
        });
        LoadBanksOnline();*/
    }

   /* private void LoadBanksOnline(){

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

                            Toast.makeText(getApplicationContext(),"Banks Not Setup", Toast.LENGTH_LONG).show();
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

*/

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



    private boolean ShowPopUpOTPRegistration() {

        try {
            //ImageView tempImageView = imageView;


            final AlertDialog.Builder imageDialog = new AlertDialog.Builder(this);
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE);

            final View layout = inflater.inflate(R.layout.activity_otpdailog,
                    (ViewGroup) this.findViewById(R.id.layout_root));

           // Button bttnAuthenticate=(Button)layout.findViewById(R.id.bttnAuthenticate);
            final EditText txtOTP = (EditText) layout.findViewById(R.id.txtOTP);


           /* bttnAuthenticate.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    String code=txtOTP.getText().toString();

                    if (code.length()<4) {
                        AlertMessageBox.Show(layout.getContext(),
                                "OTP",
                                "Please Enter a Valid OTP.",
                                AlertMessageBox.AlertMessageBoxIcon.Info);
                        //dialog.dismiss();
                        return ;
                    }



                }
            });*/
            imageDialog.setView(layout);

            imageDialog.setNeutralButton("Close", new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialog, int which) {


                    dialog.dismiss();
                }

            });

            imageDialog.setPositiveButton("Continue", new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialog, int which) {

                    String code=txtOTP.getText().toString();

                    if (code.length()<4) {
                        AlertMessageBox.Show(layout.getContext(),
                                "OTP",
                                "Please Enter a Valid OTP.",
                                AlertMessageBox.AlertMessageBoxIcon.Info);
                        //dialog.dismiss();
                        return ;
                    }

                    AlertMessageBox.Show(layout.getContext(),
                            "Success",
                            "Transaction was Successful.",
                            AlertMessageBox.AlertMessageBoxIcon.Info);

                    dialog.dismiss();
                }

            });

            imageDialog.create();
            imageDialog.show();
            return true;




        } catch (Exception e) {
            Log.e("ShowPopupRegistration>>", e.getMessage() + e.getStackTrace());
        }
        return false;
    }


}
