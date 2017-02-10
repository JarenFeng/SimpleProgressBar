package cn.geektang.test;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.geektang.simpleprogressbar.widget.CircleProgressBar;
import cn.geektang.simpleprogressbar.widget.HorizontalProgressBar;
import cn.geektang.simpleprogressbar.widget.ProgressBarBase;


public class MainActivity extends AppCompatActivity {
    private HorizontalProgressBar hpb1;
    private HorizontalProgressBar hpb2;
    private HorizontalProgressBar hpb3;
    private HorizontalProgressBar hpb4;
    private HorizontalProgressBar hpb5;
    private CircleProgressBar cpb1;
    private CircleProgressBar cpb2;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            hpb2.setProgress(hpb2.getProgress() + 1);
            hpb3.setProgress(hpb3.getProgress() + 1);
            hpb4.setProgress(hpb4.getProgress() + 1);
            hpb5.setProgress(hpb5.getProgress() + 1);
            cpb1.setProgress(cpb1.getProgress() + 1);
            cpb2.setProgress(cpb2.getProgress() + 1);

            handler.sendEmptyMessageDelayed(0,100);

            hpb1.setReachColor(Color.RED);
            hpb1.setProgress(40);
            hpb1.setMax(200);
            hpb1.setReachedHeight(60);
            hpb1.setUnreachedColor(Color.BLUE);
            hpb1.setUnreachedHeight(50);
            hpb1.setTextPosition(HorizontalProgressBar.CENTER);
            hpb1.setTextPadding(10);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hpb1 = (HorizontalProgressBar) findViewById(R.id.hpb_1);
        hpb2 = (HorizontalProgressBar) findViewById(R.id.hpb_2);
        hpb3 = (HorizontalProgressBar) findViewById(R.id.hpb_3);
        hpb4 = (HorizontalProgressBar) findViewById(R.id.hpb_4);
        hpb5 = (HorizontalProgressBar) findViewById(R.id.hpb_5);

        cpb1 = (CircleProgressBar) findViewById(R.id.cpb_1);
        cpb2 = (CircleProgressBar) findViewById(R.id.cpb_2);

        hpb2.setMax(80);
        handler.sendEmptyMessageDelayed(0,100);

        cpb1.setProcessTextAdapter(new ProgressBarBase.ProcessTextAdapter() {
            @Override
            public String getCustomProcessText(int process, int max) {
                return "Custom：" + process;
            }
        });
    }
}
