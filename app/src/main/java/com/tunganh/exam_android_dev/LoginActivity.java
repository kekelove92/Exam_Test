package com.tunganh.exam_android_dev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.edt_email)
    TextInputEditText edtEmail;
    @BindView(R.id.edt_name)
    TextInputEditText edtName;
    @BindView(R.id.edt_email_confirm)
    TextInputEditText edtEmailConfirm;
    @BindView(R.id.edt_pw)
    TextInputEditText edtPW;
    @BindView(R.id.edt_pw_confirm)
    TextInputEditText edtPwConfirm;
    @BindView(R.id.btn_register)
    MaterialButton btnRegister;

    @BindView(R.id.layout_edt_pw)
    TextInputLayout layoutPw;

    @BindView(R.id.layout_edt_confirm_pw)
    TextInputLayout layoutPwConfirm;

    private String mName;
    private String mEmail;
    private String mConfirmEmail;
    private String mPw;
    private String mConfirmPw;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        btnRegister.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_register) {
            if (edtName.getText() == null
                    || edtEmail.getText() == null
                    || edtEmailConfirm.getText() == null
                    || edtPW.getText() == null
                    || edtPwConfirm.getText() == null) {
                return;
            }

            mName = edtName.getText().toString();
            mEmail = edtEmail.getText().toString().trim();
            mConfirmEmail = edtEmailConfirm.getText().toString().trim();
            mPw = edtPW.getText().toString().trim();
            mConfirmPw = edtPwConfirm.getText().toString().trim();

            if (checkEmpty(mName,edtName )
                    && checkEmpty(mEmail, edtEmail)
                    && checkEmpty(mConfirmEmail, edtEmailConfirm)
                    && checkEmpty(mPw, layoutPw)
                    && checkEmpty(mConfirmPw, layoutPwConfirm)) {

                if (!mEmail.matches(emailPattern)) {
//                    Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                    edtEmail.setError(getResources().getString(R.string.invalid_email));
                    return;
                }

                if (!mConfirmPw.equals(mPw)){
                    edtPwConfirm.setError(getResources().getString(R.string.pw_not_match));
                    return;
                }

                if (!mConfirmEmail.equals(mEmail)){
                    edtEmailConfirm.setError(getResources().getString(R.string.email_not_match));
                    return;
                }

                Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                startActivity(intent);


            }


        }
    }

    private boolean checkEmpty(String value, TextInputEditText textInputEditText) {
        if (value == null || value.isEmpty()) {
            textInputEditText.setError(getResources().getString(R.string.error_empty_value));
            return false;
        }

        return true;
    }

    private boolean checkEmpty(String value, TextInputLayout textInputLayout) {
        if (value == null || value.isEmpty()) {
            textInputLayout.setError(getResources().getString(R.string.error_empty_value));
            return false;
        }

        return true;
    }

}
