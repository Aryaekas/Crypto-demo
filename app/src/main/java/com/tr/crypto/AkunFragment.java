package com.tr.crypto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


public class AkunFragment extends Fragment {

    View akunFragment;
    TextView mProfile, mNamapanjang, mNamaakun, mEmail, mPassword;

    private FirebaseAuth fAuth;
    private DatabaseReference databaseReference;
    final String TAG = this.getClass().getName().toUpperCase();
    Map<String, String> userMap;
    FirebaseDatabase mDatabase;
    DatabaseReference fAkunRootRef;


    public AkunFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        akunFragment = inflater.inflate(R.layout.fragment_akun, container, false);

        mProfile = akunFragment.findViewById(R.id.profile);
        mNamapanjang = akunFragment.findViewById(R.id.namapanjang_textView);
        mNamaakun = akunFragment.findViewById(R.id.namaakun_textView);
        mEmail = akunFragment.findViewById(R.id.email_textView);
        mPassword = akunFragment.findViewById(R.id.password_textView);

        fAuth = FirebaseAuth.getInstance();
        fAkunRootRef = FirebaseDatabase.getInstance().getReference("akuns");

        return akunFragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String UID = fAuth.getCurrentUser().getUid();
        DatabaseReference fUIDRef = fAkunRootRef.child(UID);
        DatabaseReference fAkunNamapanjang = fAkunRootRef.child(UID).child("Nama_panjang");
        DatabaseReference fAkunNamaakun = fAkunRootRef.child(UID).child("Nama_akun");
        DatabaseReference fAkunEmail = fAkunRootRef.child(UID).child("email");
        DatabaseReference fAkunPassword = fAkunRootRef.child(UID).child("password");


        fAkunNamapanjang.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mNamapanjang.setText(String.valueOf(snapshot.getValue(String.class)));
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fAkunNamaakun.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mNamaakun.setText(String.valueOf(snapshot.getValue(String.class)));
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fAkunEmail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mEmail.setText(String.valueOf(snapshot.getValue(String.class)));
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fAkunPassword.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mPassword.setText(String.valueOf(snapshot.getValue(String.class)));
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailAkunFragment profileDetailFragment = new DetailAkunFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, profileDetailFragment);
                transaction.commit();
            }
        });
    }
}

