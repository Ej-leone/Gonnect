package com.op.bt.beneficiarypayments.Util;

import android.widget.Filter;

import com.op.bt.beneficiarypayments.Payment.Payment;
import com.op.bt.beneficiarypayments.UI.Adapter.RecyclerAdapter;

import java.util.ArrayList;

public class CustomFilter extends Filter {

    RecyclerAdapter adapter;
    ArrayList<Payment> filterList;


    public CustomFilter(ArrayList<Payment> filterList, RecyclerAdapter aadapter) {
        this.adapter = aadapter;
        this.filterList = filterList;

    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results = new FilterResults();
        //CHECK CONSTRAINT VALIDITY
        if (charSequence != null && charSequence.length() > 0) {
            //CHANGE TO UPPER
            charSequence = charSequence.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<Payment> filteredPlayers = new ArrayList<>();
            for (int i = 0; i < filterList.size(); i++) {

                if (filterList.get(i).getBenname().toUpperCase().contains(charSequence) || filterList.get(i).getBennatid().toUpperCase().contains(charSequence) || filterList.get(i).getTotamount().toUpperCase().contains(charSequence)) {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayers.add(filterList.get(i));
                }
            }
            results.count = filteredPlayers.size();
            results.values = filteredPlayers;
        } else {
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

        adapter.dester = (ArrayList<Payment>) filterResults.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
