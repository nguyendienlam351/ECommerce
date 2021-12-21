package com.example.ecommerce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.example.ecommerce.adapters.AddressAdapter;
import com.example.ecommerce.models.AddressModel;
import com.example.ecommerce.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress {
    Button addAddress;
    RecyclerView recyclerView;

    List<AddressModel> addressModels;
    AddressAdapter addressAdapter;

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    Button addAddressBtn, paymentBtn;
    Toolbar toolbar;
    String mAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        toolbar = findViewById(R.id.address_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.address_recycler);
        paymentBtn = findViewById(R.id.payment_btn);
        addAddressBtn = findViewById(R.id.add_address_btn);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addressModels = new ArrayList<>();
        addressAdapter = new AddressAdapter(this, addressModels, this);
        recyclerView.setAdapter(addressAdapter);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        AddressModel addressModel = document.toObject(AddressModel.class);
                        addressModels.add(addressModel);
                        addressAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(AddressActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddressActivity.this, PaymentActivity.class));
            }
        });

        addAddress = findViewById(R.id.add_address_btn);

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddressActivity.this, AddAddressActivity.class));
            }
        });
    }

    @Override
    public void setAddress(String address) {
        mAddress = address;
    }
}