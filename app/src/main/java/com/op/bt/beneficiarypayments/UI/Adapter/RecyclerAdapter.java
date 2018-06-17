package com.op.bt.beneficiarypayments.UI.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.op.bt.beneficiarypayments.Payment.Payment;
import com.op.bt.beneficiarypayments.R;
import com.op.bt.beneficiarypayments.UI.PaymentScreen;
import com.op.bt.beneficiarypayments.Util.CustomFilter;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.viewholder> implements Filterable {

    public ArrayList<Payment> dester = new ArrayList<>();
    public Context ctx;
    ArrayList<Payment> filterList;
    CustomFilter customfilter;

    public RecyclerAdapter(ArrayList<Payment> dester, Context ctx) {
        this.dester = dester;
        this.filterList = dester;
        this.ctx = ctx;

    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cdes = LayoutInflater.from(ctx).inflate(R.layout.item_text, parent, false);

        return new viewholder(cdes);
    }

    @Override
    public void onBindViewHolder(viewholder holder, int position) {
        final Payment strbucks = dester.get(position);

        Log.e("dss", String.valueOf(dester.size()));
        holder.bname.setText(strbucks.getBenname());
        holder.amount.setText(strbucks.getTotamount());
        holder.oname.setText(strbucks.getBennatid());


        holder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sa = new Intent(ctx, PaymentScreen.class);
                Bundle nw = new Bundle();
                nw.putString("name", strbucks.getBenname());
                nw.putString("nid", strbucks.getBennatid());
                nw.putString("uid", strbucks.getManifestid());
                nw.putString("gender", strbucks.getGender());
                nw.putString("amount", strbucks.getTotamount());
                sa.putExtra("payment", nw);
                ctx.startActivity(sa);

            }
        });
        //todo:add necessary number

    }

    @Override
    public int getItemCount() {
        Log.e("dss", String.valueOf(dester.size()));
        return dester.size();
    }

    @Override
    public Filter getFilter() {

        if (customfilter == null) {
            customfilter = new CustomFilter(filterList, this);
        }

        return customfilter;
    }


    static class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView bname, oname, amount;
        Button pay;


        public viewholder(View itemView) {
            super(itemView);

            bname = itemView.findViewById(R.id.bname);
            oname = itemView.findViewById(R.id.oname);
            amount = itemView.findViewById(R.id.amount);
            pay = itemView.findViewById(R.id.btn_pay);


        }


        @Override
        public void onClick(View view) {

        }
    }

}
