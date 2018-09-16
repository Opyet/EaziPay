package bts.eazipay;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import bts.eazipay.Data.DataAccessMethods;
import bts.eazipay.helper.AlertMessageBox;
import bts.eazipay.helper.UsersInfo;
import bts.eazipay.helper.UtilityHelper;

public class RegisterActivity extends AppCompatActivity {

    EditText txtSurname=null;
    EditText txtOtherNames=null;
    EditText txtBizname=null;
    EditText txtMobileNo=null;
    EditText txtEmail=null;
    EditText txtPwd1=null;
    EditText txtPwd2=null;

    TextView bttnLogin=null;
    Button bttnRegister=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtSurname=(EditText)findViewById(R.id.txtSurname);
        txtOtherNames=(EditText)findViewById(R.id.txtOtherNames);
        txtBizname=(EditText)findViewById(R.id.txtBizname);
        txtMobileNo=(EditText)findViewById(R.id.txtMobileNo);
        txtEmail=(EditText)findViewById(R.id.txtEmail);
        txtPwd1=(EditText)findViewById(R.id.txtPwd1);
        txtPwd2=(EditText)findViewById(R.id.txtPwd2);
        bttnRegister=(Button)findViewById(R.id.bttnRegister);
        bttnLogin=(TextView)findViewById(R.id.bttnLogin);

        bttnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent frmIntent =new Intent();
                frmIntent.setClass(RegisterActivity.this , LoginActivity.class );
                startActivity(frmIntent);
            }
        });


        bttnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  if (txtPwd1.getText().toString().length()<7 || txtPwd1.getText().toString().length()<6) {

                      AlertMessageBox.Show(RegisterActivity.this,
                              "Password Length",
                              "Passwords Must be atleast 7 digits.",
                              AlertMessageBox.AlertMessageBoxIcon.Error);
                        return;
                    }

                if ( txtPwd1.getText().toString().equals(txtPwd2.getText().toString()) !=true ){

                    AlertMessageBox.Show(RegisterActivity.this,
                            "Password Missmatch",
                            "The two Passwords does not Match.",
                            AlertMessageBox.AlertMessageBoxIcon.Error);
                    return;
                }
                UsersInfo uInfo=new UsersInfo();
                uInfo.setBizName(txtBizname.getText().toString());
                uInfo.setEmail(txtEmail.getText().toString());
                uInfo.setFirstname(txtOtherNames.getText().toString());
                uInfo.setMobileNo(txtMobileNo.getText().toString());
                uInfo.setOthernames(txtOtherNames.getText().toString());
                uInfo.setSurname(txtSurname.getText().toString());
                uInfo.setPassCode(txtPwd1.getText().toString());

                UsersCreate(uInfo);
            }
        });





    }



    private void UsersCreate(UsersInfo uinfo){

        try {
            if (UtilityHelper.hasNetwork(this)) {
                final ProgressDialog dialog = new ProgressDialog(this);
                new UsersCreateTask() {
                    @Override
                    protected void onPreExecute() {
                        //dialog = new ProgressDialog(DataPreparationActivity.this);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setMessage("Sending Data for Registration ...");
                        dialog.show();
                    }
                    @Override
                    protected void onPostExecute( String result) {

                        dialog.dismiss();
                        if (result != null) {


                            if (result=="User Created successfully.") {
                                AlertMessageBox.Show(RegisterActivity.this,
                                        "Registration",
                                        "Your Registration was Successfull. Close the Box and login.",
                                        AlertMessageBox.AlertMessageBoxIcon.Info);
                               /* Intent frmIntent =new Intent();
                                frmIntent.setClass(RegisterActivity.this , OTPDailogActivity.class );
                                startActivity(frmIntent);*/
                                ShowPopUpOTPRegistration();
                            }else{
                                AlertMessageBox.Show(RegisterActivity.this,
                                        "Registration",
                                        result,
                                        AlertMessageBox.AlertMessageBoxIcon.Error);
                            }


                        }else {

                            //Toast.makeText(getActivity(),"Invalid Username or Password", Toast.LENGTH_LONG).show();
                        }
                        dialog.hide();
                        dialog.dismiss();
                    }
                }.execute(uinfo);
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

    private class UsersCreateTask extends AsyncTask<UsersInfo, Integer, String> {
        @Override
        protected String doInBackground(UsersInfo... args){

            DataAccessMethods rfidacc=new DataAccessMethods();
            UsersInfo uinfo=args[0];

            return rfidacc.UsersAdd(uinfo);

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
