package com.tr.crypto;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DaftarActivity extends AppCompatActivity {

    EditText mNamaPanjang, mNamaAkun, mPassword, mEmail;
    Button mDaftarBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    DatabaseReference fRootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        mNamaPanjang = findViewById(R.id.namaPanjang);
        mNamaAkun = findViewById(R.id.namaAkun);
        mPassword = findViewById(R.id.password);
        mEmail = findViewById(R.id.email);
        mDaftarBtn = findViewById(R.id.daftarBtn);
        mLoginBtn = findViewById(R.id.createText);

        fRootRef = FirebaseDatabase.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(com.tr.crypto.DaftarActivity.this, MainActivity.class));
            finish();
        }
    }

    public void signUpBtnClicked(View v) {
        String namapanjang = mNamaPanjang.getText().toString().trim();
        String namaakun = mNamaAkun.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if (TextUtils.isEmpty(namapanjang)) {
            mNamaPanjang.setError("Tidak Boleh Kosong!");
            return;
        }

        if (TextUtils.isEmpty(namaakun)){
            mNamaAkun.setError("Tidak Boleh Kosong!");
            return;
        }

        if (TextUtils.isEmpty(email)){
            mEmail.setError("Tidak Boleh Kosong!");
            return;
        }

        if (TextUtils.isEmpty(password)){
            mPassword.setError("Tidak Boleh Kosong!");
            return;
        }

        if(password.length() < 8) {
            mPassword.setError("Tidak Boleh Kosong!");
            return;
        }

        registerUser(namapanjang, namaakun, email, password);
    }

    private void registerUser(String namapanjang, String namaakun, String email, String password) {

        fAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("Nama_panjang", namapanjang);
                map.put("Nama_akun", namaakun);
                map.put("email", email);
                map.put("password", password);
                map.put("ID", fAuth.getCurrentUser().getUid());

                fRootRef.child("akuns").child(fAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(com.tr.crypto.DaftarActivity.this,
                              "Daftar Akun Berhasil", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(com.tr.crypto.DaftarActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(com.tr.crypto.DaftarActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void loginTextClicked(View v) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}