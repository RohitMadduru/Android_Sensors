package com.rohit.sensors;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;


public class NetworkChange extends BroadcastReceiver {




    @Override
    public void onReceive(Context context, Intent intent) {
        if(!ConnectionCheck.isConnected(context)){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View layout_dialog = LayoutInflater.from(context).inflate(R.layout.check_internet,null);
            builder.setView(layout_dialog);
            AppCompatButton btnRetry = layout_dialog.findViewById(R.id.retry);
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.setCancelable(false);
            dialog.getWindow().setGravity(Gravity.CENTER);
            btnRetry.setOnClickListener(v -> {
                 dialog.dismiss();
                 onReceive(context,intent);

            });

        }
    }
}
