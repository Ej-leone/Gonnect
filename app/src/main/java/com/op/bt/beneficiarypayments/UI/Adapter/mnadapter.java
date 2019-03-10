package com.op.bt.beneficiarypayments.UI.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.op.bt.beneficiarypayments.Order.Order;
import com.op.bt.beneficiarypayments.Payment.Crypto;
import com.op.bt.beneficiarypayments.R;

import java.util.ArrayList;

public class mnadapter extends RecyclerView.Adapter<mnadapter.viewholder> {

    public ArrayList<Order> dester = new ArrayList<>();
    public Context ctx;
    ArrayList<Order> filterList;
    private OnItemCheckListener onItemCheckListener;

    public mnadapter(ArrayList<Order> dester, Context ctx, OnItemCheckListener onItemCheckListener) {
        this.dester = dester;
        this.filterList = dester;
        this.ctx = ctx;
        this.onItemCheckListener = onItemCheckListener;
    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cdes = LayoutInflater.from(ctx).inflate(R.layout.d_recycler, parent, false);

        return new viewholder(cdes);
    }

    @Override
    public void onBindViewHolder(final viewholder holder, int position) {

        final Order strbucks = dester.get(position);

        if (strbucks.isAvailable_locally()){
            holder.cbb.setVisibility(View.GONE);
            holder.rrl.setCardBackgroundColor(ctx.getResources().getColor(R.color.Red));
        }
        holder.textvww.setText(strbucks.getName());
        holder.cbb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (holder.cbb.isChecked()) {
                    // Log.e("unchecked",strbucks.getName()+"has been checked add this");

                    //Todo:add check whether the  item is already
                    onItemCheckListener.onchecked(strbucks);


                } else if (!holder.cbb.isChecked()) {
                    // Log.e("unchecked",strbucks.getName()+"has been unchecked has been unchecked remove");
                    onItemCheckListener.onunchecked(strbucks);
                }
            }
        });

    }

    @Override
    public int getItemCount() {

        if (dester.size() == 0) {
            return 0;
        } else {
            return dester.size();
        }
    }


    public interface OnItemCheckListener {
        void onchecked(Order item);

        void onunchecked(Order item);
        //void onasent( roll_call item);
    }

    static class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textvww;
        CheckBox cbb;
        CardView rrl;

        public viewholder(View itemView) {
            super(itemView);

            textvww = itemView.findViewById(R.id.ctext_view);
            cbb = itemView.findViewById(R.id.dialog_cb);
            rrl = itemView.findViewById(R.id.adicard);


        }


        @Override
        public void onClick(View view) {

        }
    }
}
