package bts.eazipay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    EditText txtSurname=null;
    EditText txtOtherNames=null;
    EditText txtBizname=null;
    EditText txtMobileNo=null;
    EditText txtEmail=null;
    EditText txtPwd1=null;

    TextView bttnLogin=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtSurname=(EditText)findViewById(R.id.txtSurname);



    }
}
