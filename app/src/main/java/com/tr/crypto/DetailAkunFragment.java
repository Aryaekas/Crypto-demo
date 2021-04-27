package com.tr.crypto;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tr.crypto.Akun;

public class DetailAkunFragment extends Fragment {
    private static final String TAG = DetailAkunFragment.class.getSimpleName();
    EditText gNamapanjang, gNamaakun, gEmail, gPassword;
    private Button gPerbaruiBtn;
    private FirebaseAuth fAuth;
    DatabaseReference fRootRef;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_akun, container, false);

        gNamapanjang = v.findViewById(R.id.namaPanjang);
        gNamaakun = v.findViewById(R.id.namaAkun);
        gEmail = v.findViewById(R.id.email);
        gPassword = v.findViewById(R.id.password);
        gPerbaruiBtn = v.findViewById(R.id.perbaruiBtn);

        fAuth = FirebaseAuth.getInstance();
        fRootRef = FirebaseDatabase.getInstance().getReference("akuns");

        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        fRootRef = FirebaseDatabase.getInstance().getReference().child("akuns");

        String UID = fAuth.getCurrentUser().getUid();
        DatabaseReference fUIDRef = fRootRef.child(UID);

        fUIDRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Gagal membaca judul app.", databaseError.toException());
            }
        });

        gPerbaruiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UID = fAuth.getCurrentUser().getUid();
                String Nama_panjang = gNamapanjang.getText().toString();
                String Nama_akun = gNamaakun.getText().toString();
                String email = gEmail.getText().toString();
                String password = gPassword.getText().toString();
                if (TextUtils.isEmpty(UID)) {
                    createUser(Nama_panjang, Nama_akun, email, password);
                } else {
                    updateUser(Nama_panjang, Nama_akun, email, password);
                }
            }
        });
        toggleButton();
        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String UID = fAuth.getCurrentUser().getUid();
        DatabaseReference fUIDRef = fRootRef.child(UID);
        DatabaseReference fAkunNamapanjang = fRootRef.child(UID).child("Nama_panjang");
        DatabaseReference fAkunNamaakun = fRootRef.child(UID).child("Nama_akun");
        DatabaseReference fAkunEmail = fRootRef.child(UID).child("email");
        DatabaseReference fAkunPassword = fRootRef.child(UID).child("password");

        fAkunNamapanjang.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gNamapanjang.setText(String.valueOf(snapshot.getValue(String.class)));
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fAkunNamaakun.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gNamaakun.setText(String.valueOf(snapshot.getValue(String.class)));
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fAkunEmail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gEmail.setText(String.valueOf(snapshot.getValue(String.class)));
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fAkunPassword.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gPassword.setText(String.valueOf(snapshot.getValue(String.class)));
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void toggleButton() {
        String UID = fAuth.getCurrentUser().getUid();
        DatabaseReference fUIDRef = fRootRef.child(UID);
        if (TextUtils.isEmpty(UID)) {
            gPerbaruiBtn.setText("Update");
        }
    }

    private void createUser(String Nama_panjang, String Nama_akun, String email, String password) {
        String UID = fAuth.getCurrentUser().getUid();
        DatabaseReference fUIDRef = fRootRef.child(UID);
        if (TextUtils.isEmpty(UID)) {
            UID = fRootRef.push().getKey();
        }

        Akun akun = new Akun (Nama_panjang, Nama_akun, email, password);

        fRootRef.child(UID).setValue(akun);

        addUserChangeListener();
    }

    private void addUserChangeListener() {
        String UID = fAuth.getCurrentUser().getUid();
        DatabaseReference fUIDRef = fRootRef.child(UID);
        fUIDRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    Akun akun = dataSnapshot.getValue(Akun.class);
                if (akun == null) {
                    Log.e(TAG, "akun data kosong!");
                    return;
                }

                Log.e(TAG, "Data akun diperbarui!" + akun.getNama_panjang() + ", " + akun.getNama_akun()+ ", " + akun.getEmail()+ ", " + akun.getPassword());

                gNamapanjang.setText(akun.getNama_panjang());
                gNamaakun.setText(akun.getNama_akun());
                gEmail.setText(akun.getEmail());
                gPassword.setText(akun.getPassword());

                toggleButton();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "Gagal mengambil data user", error.toException());
            }
        });
    }

    private void updateUser(String Nama_panjang, String Nama_akun, String email, String password) {
        String UID = fAuth.getCurrentUser().getUid();
        DatabaseReference fUIDRef = fRootRef.child(UID);
        if (!TextUtils.isEmpty(Nama_panjang))
            fRootRef.child(UID).child("Nama_panjang").setValue(Nama_panjang);

        if (!TextUtils.isEmpty(Nama_akun))
            fRootRef.child(UID).child("Nama_akun").setValue(Nama_akun);

        if (!TextUtils.isEmpty(email))
            fRootRef.child(UID).child("email").setValue(email);

        if (!TextUtils.isEmpty(password))
            fRootRef.child(UID).child("password").setValue(password);
    }

}
