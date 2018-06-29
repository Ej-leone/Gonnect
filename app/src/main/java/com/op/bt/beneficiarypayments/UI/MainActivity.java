package com.op.bt.beneficiarypayments.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.EditText;

import com.op.bt.beneficiarypayments.Data.NoDaoSetException;
import com.op.bt.beneficiarypayments.Payment.Payment;
import com.op.bt.beneficiarypayments.Payment.PaymentLedger;
import com.op.bt.beneficiarypayments.R;
import com.op.bt.beneficiarypayments.UI.Adapter.RecyclerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView saw;
    RecyclerAdapter adapter;
    ArrayList<Payment> all = new ArrayList<>();
    PaymentLedger pledger;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        try {
            pledger = PaymentLedger.getInstance();
        } catch (NoDaoSetException e) {
            e.printStackTrace();
            // Log.e("Error", e.getLocalizedMessage());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init_ui();
        getinfo();
    }


    public void init_ui() {
        all = pledger.ListAll();

        saw = findViewById(R.id.maaan_rec);
        search = findViewById(R.id.search_edittext);
        adapter = new RecyclerAdapter(all, this);
        saw.setHasFixedSize(true);
        saw.setLayoutManager(new LinearLayoutManager(this));
        saw.setAdapter(adapter);


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String des = editable.toString();

                adapter.getFilter().filter(des);

            }
        });

    }


    public void getinfo() {

        //Log.e("alscreensl",all.toString());
        adapter.notifyDataSetChanged();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
