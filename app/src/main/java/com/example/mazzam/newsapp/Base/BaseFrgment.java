package com.example.mazzam.newsapp.Base;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.afollestad.materialdialogs.MaterialDialog;

public class BaseFrgment extends Fragment {
    protected BaseActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (BaseActivity) context;
    }

    public MaterialDialog showMessage(int titleResId, int messageResId, int positiveTextResId, int negativeText) {
        return activity.showMessage(titleResId, messageResId, positiveTextResId, negativeText);
    }

    public MaterialDialog showMessage(String title, String message,String positiveText) {
        return activity.showMessage(title,message,positiveText);
    }

    public MaterialDialog showConfirmationMessage(int titleResId, int messageResId, int positiveTextResId, MaterialDialog.SingleButtonCallback onPositive) {
        return activity.showConfirmationMessage(titleResId, messageResId, positiveTextResId, onPositive);
    }

    public MaterialDialog showprogressBar(){
        return activity.showprogressBar();
    }
    public MaterialDialog hidePrgressBar(){
       return activity.hidePrgressBar();
    }
}

