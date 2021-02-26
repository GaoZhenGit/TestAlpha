package com.akilgao.testalpha.sign;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.akilgao.testalpha.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class SignActivity extends AppCompatActivity {
    private EditText mEtPkgName;
    private TextView mTvTextViewBase64;
    private TextView mTvTextViewSha1;
    private TextView mTvTextViewMd5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        mEtPkgName = findViewById(R.id.et_pkg_name);
        mEtPkgName.setText(getPackageName());
        mTvTextViewBase64 = findViewById(R.id.tv_signature_base64);
        mTvTextViewSha1 = findViewById(R.id.tv_signature_sha1);
        mTvTextViewMd5 = findViewById(R.id.tv_signature_md5);
    }

    public void showSign(View view) {
        String base64 = getSignatureBase64(this);
        Log.i("SignActivity", "base64:" + base64);
        mTvTextViewBase64.setText("base64:" + base64);

        String sha1= getSignatureSha1(this);
        Log.i("SignActivity", "sha1:" + sha1);
        mTvTextViewSha1.setText("sha1:" + sha1);

        String md5= getSignatureMd5(this);
        Log.i("SignActivity", "md5:" + md5);
        mTvTextViewMd5.setText("md5:" + md5);
    }

    public String getSignatureBase64(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(mEtPkgName.getText().toString(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            return Base64.encodeToString(cert, Base64.DEFAULT);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getSignatureSha1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(mEtPkgName.getText().toString(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            return byteArrayToHex(publicKey);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getSignatureMd5(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(mEtPkgName.getText().toString(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] publicKey = md.digest(cert);
            return byteArrayToHex(publicKey);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String byteArrayToHex(byte[] byteArray) {
        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray =new char[byteArray.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b& 0xf];
        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }
}