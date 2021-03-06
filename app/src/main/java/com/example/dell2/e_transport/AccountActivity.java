package com.example.dell2.e_transport;

import android.graphics.Picture;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Intent;

import org.w3c.dom.Text;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.URLEncoder;

import application.E_Trans_Application;
import collector.BaseActivity;
import collector.CommonRequest;
import collector.CommonResponse;
import collector.Constant;
import collector.HttpPostTask;
import collector.LoadingDialogUtil;
import collector.PictureUtils;
import collector.ResponseHandler;
import entity.User;

/**
 * Created by wangyan on 2017/5/23.
 */

public class AccountActivity extends BaseActivity implements View.OnClickListener {
    private ImageView header_front_1;
    private ImageView header_back_1;
    private TextView title_name;
    private RelativeLayout ll_avatar;
    private LinearLayout setUserName;
    private TextView userName;
    private LinearLayout setUserGender;
    private TextView userGender;
    private LinearLayout setUserTel;
    private TextView userTel;
    private LinearLayout setUserAddress;
    private TextView userAddress;
    private LinearLayout setPwLogin;
    private TextView pwLogin;
    private LinearLayout setPwCover;
    private TextView pwCover;
    private LinearLayout setIsAvoidPw;
    private E_Trans_Application app;
    private User user;//Test
    private Bitmap bmp = null;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_account);
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        initContent();
    }

    public void init() {
        /*实例化*/
        app = (E_Trans_Application) getApplication();
        user = app.getUser();
        header_front_1 = (ImageView) findViewById(R.id.header_front_1);
        header_back_1 = (ImageView) findViewById(R.id.header_back_1);
        title_name = (TextView) findViewById(R.id.title_name);
        setUserName = (LinearLayout) findViewById(R.id.setUserName);
        setUserAddress = (LinearLayout) findViewById(R.id.setUserAddress);
        setUserTel = (LinearLayout) findViewById(R.id.setTel);
        setUserGender = (LinearLayout) findViewById(R.id.setUserGender);
        setPwCover = (LinearLayout) findViewById(R.id.set_PwCover);
        setPwLogin = (LinearLayout) findViewById(R.id.set_PwLogin);
        userName = (TextView) findViewById(R.id.userName);
        userGender = (TextView) findViewById(R.id.userGender);
        userTel = (TextView) findViewById(R.id.tel);
        userAddress = (TextView) findViewById(R.id.userAddress);
        pwLogin = (TextView) findViewById(R.id.pwLogin);
        pwCover = (TextView) findViewById(R.id.pwCover);
        setIsAvoidPw = (LinearLayout) findViewById(R.id.setIsAvoidPw);
        ll_avatar = (RelativeLayout) findViewById(R.id.ll_avatar);
          /*响应事件设定*/
        ll_avatar.setOnClickListener(this);
        setIsAvoidPw.setOnClickListener(this);
        header_front_1.setOnClickListener(this);
        setUserName.setOnClickListener(this);
        setUserGender.setOnClickListener(this);
        setUserTel.setOnClickListener(this);
        setUserAddress.setOnClickListener(this);
        setPwCover.setOnClickListener(this);
        setPwLogin.setOnClickListener(this);
        setPwCover.setOnClickListener(this);
        /*标题初始化*/
        header_front_1.setImageResource(R.drawable.last_white);
        header_back_1.setVisibility(View.GONE);
        title_name.setText("账户信息");
        /*内容初始化*/
        imageView=(ImageView)findViewById(R.id.TestView);

        initContent();
    }

    /*内容初始化函数*/
    public void initContent() {
        imageView.setImageBitmap(PictureUtils.getBitmap(getExternalFilesDir("avatar").getPath() + "/avatar.jpg",66,66));
        if (user.getUserName() != null && !user.getUserName().equals(""))
            userName.setText(user.getUserName());
        if (user.getUserGenderString() != null && !user.getUserGenderString().equals(""))
            userGender.setText(user.getUserGenderString());
        if (user.getUserTel() != null && !user.getUserTel().equals(""))
            userTel.setText(user.getUserTel());
        if (user.getUserAddress() != null && !user.getUserAddress().equals("")) {
            userAddress.setText(user.getUserAddress());
        }
        if (user.getUserPwCover() == null || user.getUserPwCover().equals(""))
        {
            pwCover.setText("未设置");
        }
        else
        {
            pwCover.setText("已设置");
        }
        if (user.getUserPwLogin() == null || user.getUserPwLogin().equals(""))
        {
            pwLogin.setText("未设置");
        }
        else
        {
            pwLogin.setText("已设置");
        }
    }

    /*响应事件函数*/
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.header_front_1:
                finish();
                break;
            case R.id.setUserName:
                intent = new Intent(AccountActivity.this, UserNameActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userName", user.getUserEmail());
                bundle.putString("phoneNumber", user.getUserTel());
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.setUserGender:
                intent = new Intent(AccountActivity.this, UserSexActivity.class);
                Bundle genderBundle = new Bundle();
                genderBundle.putString("userName", user.getUserEmail());
                genderBundle.putString("phoneNumber", user.getUserTel());
                intent.putExtras(genderBundle);
                startActivity(intent);
                break;
            case R.id.setTel:
                if (user.getUserTel() != null && !user.getUserTel().equals("")) {
                    intent = new Intent(AccountActivity.this, UserPhoneActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(AccountActivity.this, UserNewPhoneActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.setUserAddress:
                intent = new Intent(AccountActivity.this, UserAreaAvtivity.class);
                Log.d("a", "CHANGE");
                intent.putExtra("kind","setting");
                startActivityForResult(intent,0);
                break;
            case R.id.set_PwLogin:
                if (user.getUserPwLogin() != null && !user.getUserPwLogin().equals("")) {
                    intent = new Intent(AccountActivity.this, UserLoginPwChangeActivity.class);
                } else {
                    intent = new Intent(AccountActivity.this, UserLoginPwActivity.class);
                }
                startActivity(intent);
                break;
            case R.id.set_PwCover:
                if (user.getUserPwCover() != null && !user.getUserPwCover().equals(""))
                    intent = new Intent(AccountActivity.this, UserPayPwChangeActivity.class);
                else
                    intent = new Intent(AccountActivity.this, UserPayPwActivity.class);
                startActivity(intent);
                break;
            case R.id.setIsAvoidPw:
                intent = new Intent(AccountActivity.this, UserAvoidPwActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_avatar:
                intent = new Intent(AccountActivity.this, UserAvatarActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0 && resultCode==RESULT_OK){
            Bundle bundle = data.getExtras();
            if(bundle!=null) {
                final String address = bundle.getString("address");
                Log.d("text", address);
                if (address != null && !address.equals("")) {
                    CommonRequest request = new CommonRequest();
                    request.setRequestCode("location");
                    try {
                        String strUTF8 = URLEncoder.encode(address, "UTF-8");
                        Log.e("Ts", strUTF8);
                        request.addRequestParam("param", strUTF8);
                    } catch (UnsupportedEncodingException e) {
                        //Log.d("TAG",e.getMessage());
                        e.printStackTrace();
                    }
                    final User user = app.getUser();
                    String userName = user.getUserEmail();
                    String phoneNumber = user.getUserTel();
                    request.addRequestParam("userName", userName);
                    request.addRequestParam("phoneNumber", phoneNumber);
                    HttpPostTask myTask = sendHttpPostRequest(Constant.SETTING_URL, request, new ResponseHandler() {
                        @Override
                        public CommonResponse success(CommonResponse response) {
                            Log.e("SETTING", "Suuu");
                            LoadingDialogUtil.cancelLoading();
                            Toast.makeText(AccountActivity.this, "地址设置成功", Toast.LENGTH_SHORT).show();
                            userAddress.setText(address);
                            return response;
                        }

                        @Override
                        public CommonResponse fail(String failCode, String failMsg) {
                            Log.e("mSETTING", "fffffail");
                            LoadingDialogUtil.cancelLoading();
                            Toast.makeText(AccountActivity.this, "地址设置失败", Toast.LENGTH_SHORT).show();
                            return null;
                        }
                    }, true);
                } else {
                    userAddress.setText(user.getUserAddress());
                }
            }

        }
    }
}
