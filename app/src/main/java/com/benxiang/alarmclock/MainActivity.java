package com.benxiang.alarmclock;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

        private Button btnSet, btnCancel;
        private TextView info;
        private Calendar calendar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            btnSet = (Button) findViewById(R.id.setalarm);
            btnCancel = (Button) findViewById(R.id.cancelalarm);
            info = (TextView) findViewById(R.id.info);

            calendar = Calendar.getInstance();



            btnSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    int mHour = calendar.get(Calendar.HOUR_OF_DAY);
                    int mMinute = calendar.get(Calendar.MINUTE);
                    new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    // TODO Auto-generated method stub
                                    calendar.setTimeInMillis(System.currentTimeMillis());
                                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                    calendar.set(Calendar.MINUTE, minute);
                                    calendar.set(Calendar.SECOND, 0);
                                    calendar.set(Calendar.MILLISECOND, 0);
                                    // 建立Intent和PendingIntent来调用目标组件
                                    Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                                    PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
                                    // 获取闹钟管理的实例
                                    AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                                    // 设置闹钟
                                    am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                                    // 设置周期闹钟
//                                    am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (10 * 1000), (24 * 60 * 60 * 1000), pendingIntent);
                                    String tmpS = "设置闹钟时间为" + format(hourOfDay) + ":" + format(minute);
                                    info.setText(tmpS);
                                }
                            }, mHour, mMinute, true).show();
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
                    // 获取闹钟管理实例
                    AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                    // 取消
                    am.cancel(pendingIntent);
                    info.setText("闹钟已经取消");
                }
            });
        }

        // 格式化字符串7:3-->07:03
    private String format(int x) {
        String s = "" + x;
        if (s.length() == 1) {
            s = "0" + s;
        }
        return s;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.activity_main, menu);
//        return true;
//    }
}
