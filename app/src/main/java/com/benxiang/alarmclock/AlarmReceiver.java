package com.benxiang.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 *
 * Created by ZeQiang Fang on 2018/5/30.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "您设置的时间到了！", Toast.LENGTH_LONG).show();
    }
}
