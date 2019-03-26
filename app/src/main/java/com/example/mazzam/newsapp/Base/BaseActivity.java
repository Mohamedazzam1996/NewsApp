package com.example.mazzam.newsapp.Base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

public class BaseActivity extends AppCompatActivity {
    protected AppCompatActivity activity;
    MaterialDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
    }

    public MaterialDialog showConfirmationMessage(int titleResId, int messageResId,int positiveTextResId,MaterialDialog.SingleButtonCallback onPositive) {
        dialog= new MaterialDialog.Builder(this)
                .title(titleResId)
                .content(messageResId)
                .cancelable(false)
                .positiveText(positiveTextResId)
                .onPositive(onPositive)
                .show();


        return dialog;
    }
    public MaterialDialog showMessage(int titleResId, int messageResId,int positiveTextResId,int negativeText) {
      dialog= new MaterialDialog.Builder(this)
              .title(titleResId)
              .content(messageResId)
              .cancelable(false)
              .positiveText(positiveTextResId)
              .negativeText(negativeText)
              .onNegative(new MaterialDialog.SingleButtonCallback() {
                  @Override
                  public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                      dialog.dismiss();
                  }
              })
              .onPositive(new MaterialDialog.SingleButtonCallback() {
                  @Override
                  public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                      dialog.dismiss();
                   finish();
                  }
              })
              .show();
     return dialog;
    }

    public MaterialDialog showMessage(String title, String message,String positiveText) {
        dialog=new MaterialDialog.Builder(this)
                .title(title)
                .content(message)
                .positiveText(positiveText)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
     return dialog;
    }
    public MaterialDialog showprogressBar(){
        dialog=new MaterialDialog.Builder(this)
                .progress(true,0)
                .title("Downloading")
                .content("plz wait while Downloading")
                .cancelable(false)
                .show();
       return dialog;
    }
    public MaterialDialog hidePrgressBar(){
      if(dialog!=null&&dialog.isShowing())
          dialog.dismiss();
    return dialog;
    }
}