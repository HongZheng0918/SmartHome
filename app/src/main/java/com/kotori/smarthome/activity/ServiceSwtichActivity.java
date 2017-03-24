package com.kotori.smarthome.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kotori.smarthome.R;
import com.kotori.smarthome.service.ControlService;
import com.kotori.smarthome.service.LinkService;
import com.kotori.smarthome.util.ToastUtil;

public class ServiceSwtichActivity extends Activity implements View.OnClickListener{

    private Button OpenHomeButton;
    private Button OpenControlButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_swtich);
        initView();
    }

    private void initView() {
        OpenHomeButton = (Button) findViewById(R.id.btn_open_home_service);
        OpenHomeButton.setOnClickListener(this);
        OpenControlButton = (Button) findViewById(R.id.btn_open_control_service);
        OpenControlButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_open_home_service:       // 点击开启家庭状态监控
                Intent serviceIntent = new Intent(this, LinkService.class);
                serviceIntent.putExtra("ip","192.168.1.254");
                serviceIntent.putExtra("port","8080");
                ToastUtil.showToast(ServiceSwtichActivity.this,"开启状态监测服务成功！");
                startService(serviceIntent);
                break;
            case R.id.btn_open_control_service:
                Intent controlIntent = new Intent(this, ControlService.class);
                controlIntent.putExtra("ip","192.168.1.253");
                controlIntent.putExtra("port","8888");
                ToastUtil.showToast(ServiceSwtichActivity.this,"开启控制平台服务成功！");
                startService(controlIntent);
                break;
        }
    }
}
