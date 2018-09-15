package bts.eazipay;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bts.eazipay.Data.DataAccessMethods;
import bts.eazipay.helper.AlertMessageBox;
import bts.eazipay.helper.UsersInfo;
import bts.eazipay.helper.UtilityHelper;

public class LoginActivity extends AppCompatActivity {

    Button bttnLogin =null;
    TextView bttnRegister =null;
    EditText txtMobile=null;
    EditText txtPassword=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bttnLogin = (Button)findViewById(R.id.bttnLogin);
        bttnRegister = (TextView) findViewById(R.id.bttnRegister);

        bttnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent frmIntent= new Intent();
                frmIntent.setClass(getApplicationContext() , RegisterActivity.class );

                startActivity(frmIntent);
            }
        });

        bttnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    txtMobile = (EditText)findViewById(R.id.txtMobile);
                    txtPassword = (EditText)findViewById(R.id.txtPassword);
                    String mobile=txtMobile.getText().toString().trim();
                    String pwd=txtPassword.getText().toString().trim();
                   /* if (mobile.length()<7 || pwd.length()<6) {

                        return;
                    }*/
                    ///TODO
                    Intent frmIntent =new Intent();
                    frmIntent.setClass(LoginActivity.this , HomeScreenActivity.class );
                    startActivity(frmIntent);
                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                    //super.onDestroy();
                    return ;

                   // LoginUserOnline(view,mobile,pwd);
                } catch (Exception e)
                {
                    Log.e("", e.getMessage() + e.getStackTrace());

                }

            }
        });



    }

    private void LoginUserOnline(final View v,String username,String Password){

        try {
            if (UtilityHelper.hasNetwork(this)) {
                final ProgressDialog dialog = new ProgressDialog(this);
                new LoginUserTask() {
                    @Override
                    protected void onPreExecute() {
                        //dialog = new ProgressDialog(DataPreparationActivity.this);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.setMessage("Login User In Progress ...");
                        dialog.show();
                    }
                    @Override
                    protected void onPostExecute( ArrayList<UsersInfo> result) {

                        dialog.dismiss();
                        if (result != null) {


                            for (UsersInfo uInfo : result)
                            {


                                //controlsView.setVisibility(View.GONE);
                                Intent frmIntent= new Intent();

                                if (uInfo !=null && uInfo.getIsActive() && uInfo.getUsername().length()>5) {

                                  /*  if (chkrememberPwd.isChecked()) {

                                    }*//*  if (chkrememberPwd.isChecked()) {

                                    }*/
                                    getSharedPreferences("hacking",MODE_PRIVATE)
                                            .edit()
                                            .putBoolean("IsLoggedIn", true)
                                            .putString("Username", uInfo.getUsername().trim())
                                            .putString("Firstname", uInfo.getFirstname().trim())
                                            .putString("MobileNo", uInfo.getMobileNo())
                                            .putString("Othernames", uInfo.getOthernames())
                                            .putString("Surname", uInfo.getSurname().trim())
                                           // .putBoolean("rememberPwd", chkrememberPwd.isChecked())
                                            .commit();

                                    frmIntent.setClass(LoginActivity.this , HomeScreenActivity.class );
                                    startActivity(frmIntent);
                                    finish();
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    //super.onDestroy();
                                    return ;


                                }else{
                                    Toast.makeText(getApplicationContext(),"Your Account is NOT Active.", Toast.LENGTH_LONG).show();
                                }



                            }



                        }else {

                            Toast.makeText(getApplicationContext(),"Invalid Username or Password | Also Confirm you have a valid Internet Connection.", Toast.LENGTH_LONG).show();
                        }
                        dialog.hide();
                        dialog.dismiss();
                    }
                }.execute(username,Password);
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

    private class LoginUserTask extends AsyncTask<String, Integer, ArrayList<UsersInfo>> {
        @Override
        protected ArrayList<UsersInfo> doInBackground(String... args){

            DataAccessMethods rfidacc=new DataAccessMethods();
            String username=args[0].toString();
            String pwd=args[1].toString();
            return rfidacc.LoginUser(username,pwd);

        }


    }

}
