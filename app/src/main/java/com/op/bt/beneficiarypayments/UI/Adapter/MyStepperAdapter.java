package com.op.bt.beneficiarypayments.UI.Adapter;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.op.bt.beneficiarypayments.R;
import com.op.bt.beneficiarypayments.UI.Fragments.BlankFragment;
import com.op.bt.beneficiarypayments.UI.Fragments.Data;
import com.op.bt.beneficiarypayments.UI.Fragments.Picture;
import com.op.bt.beneficiarypayments.UI.Fragments.Produce_Card;
import com.op.bt.beneficiarypayments.UI.Fragments.userprints;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;


/**
 * Created by Ej on 3/22/17.
 */

public class MyStepperAdapter extends AbstractFragmentStepAdapter
{

    public MyStepperAdapter(FragmentManager fm , Context context)
    {
        super(fm,context);
    }

    @Override
    public Step createStep(@IntRange(from = 0L) int position)
    {
        switch (position)
        {
            case 0:
                return  new Data();

            case 1:
                return  new Picture();

            case 2:
                return  new userprints() ;

            case 3:
                return  new BlankFragment();


            default:
                throw   new IllegalArgumentException("Unsupported Position :"+ position);
        }

    }

    @Override
    public int getCount() {
        return 4;
    }


    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0L) int position) {
        StepViewModel.Builder builder = new StepViewModel.Builder(context);
        //Ovveride this Method if Using Tabs not necessary for other Methods

        switch(position)
        {
            case 0:
                builder.setTitle("Personal Information");
                break;
            case 1:
                builder.setTitle("Picture");
                break;
            case 2:
                builder.setTitle("Principal Fingerprints");
                break;
            case 3:
                builder.setTitle("Alternative Fingerprints");

                break;

        }
        //.setTitle(R.string.tab_title)

        //can be a CharSequence instead
       // .create();
        return builder.create();
    }

}
