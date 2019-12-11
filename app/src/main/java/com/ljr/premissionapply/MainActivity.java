package com.ljr.premissionapply;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ljr.premissionapply.annotation.Permission;
import com.ljr.premissionapply.annotation.PermissionCancel;
import com.ljr.premissionapply.annotation.PermissionDenied;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void photo(View view) {
        //拍照
        takePhoto();
    }

    public void wr(View view) {
        //读写文件权限
        wrPermission();
    }

    @Permission(value = Manifest.permission.CAMERA, requestCode = 200)
    public void takePhoto() {
        Toast.makeText(this, "权限申请成功...", Toast.LENGTH_SHORT).show();
    }
    @Permission(value = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode = 200)
    public void wrPermission() {
        Toast.makeText(this, "权限申请成功...", Toast.LENGTH_SHORT).show();
    }
    @PermissionCancel
    public void testCancel() {
        Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied
    public void testDenied() {
        Toast.makeText(this, "权限被拒绝（用户勾选了，不再提醒）", Toast.LENGTH_SHORT).show();
    }
}
