package com.akilgao.testalpha.sign;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.content.pm.VersionedPackage;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class SignatureHookTool {
    public static final String SIGN = "MIIC5DCCAcwCAQEwDQYJKoZIhvcNAQEFBQAwNzEWMBQGA1UEAwwNQW5kcm9pZCBEZWJ1ZzEQMA4G\n" +
            "    A1UECgwHQW5kcm9pZDELMAkGA1UEBhMCVVMwIBcNMjEwMjI0MDY1ODAzWhgPMjA1MTAyMTcwNjU4\n" +
            "    MDNaMDcxFjAUBgNVBAMMDUFuZHJvaWQgRGVidWcxEDAOBgNVBAoMB0FuZHJvaWQxCzAJBgNVBAYT\n" +
            "    AlVTMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAh2hKSIpfezIHfFyBYkyG0YVRLKES\n" +
            "    ffyglUXQd+ZbDmZdcJ+rZ+9cbJ0vMRvEmMdQqe8dAtXKyUv64E4Q0Ls6BiPm15yILPUezqT3K6wa\n" +
            "    EkX3pyjRehBDN3xQ9TLBS3baHveOygTUh6HacLzEUEuJEx2DFvyR7wJaXH8J6N1ZzvwK0X/HsJF0\n" +
            "    d6G2vwG4U6qZ75Gv9gzGUI/ennRrIU6QeAWnr5ENzai/6qeF6E8cUWJMM7SF9qEJerNfhMZyz5QR\n" +
            "    fupHkcVNda+cRguSCWUsRRXatvmGPFfmcTcHQ+HqEvfa8lobSF0BgiYreyancp2EhUsdbg6DQx2T\n" +
            "    MMtf9ZsBoQIDAQABMA0GCSqGSIb3DQEBBQUAA4IBAQAXycVgKa5ghtvRtfPVNMoyW3Fnl0UVK9qz\n" +
            "    JUAXl7Kftj7C7ytlUCJApK16wekDBycOKtkYKvl1S0GJ6hiiweVRVdxtSQLIbvgfc9nd2wqJfODj\n" +
            "    RdWz+KyOQvV1/pJZIL1kEsWzNTCO7vK8C+Jr/i4GKWef3pKwS503b6jsUYyCrN0JZ87K27647mQI\n" +
            "    NuUY4dozfcpde9FBVPjiTbEtYdQrVlVD9tHQgIIfHnOsYiRxnJKGB/jBurb34c4sHHg4Jeaepg2k\n" +
            "    +/oSDIMK63UyiP1EsPr5XMN/W2cydEZCKXRtueZqYDQJUem6Idt2qtg67xGjqvtztoDSg8/7zu55\n" +
            "    mUjv";

    public static void hook(Application application) {
        try {
            Class<?> clzClassActivityThread = Class.forName("android.app.ActivityThread");
            //拿到内部的真实pm的实例（不能直接反射sPackageManager，因为可能是第一次调用没有实例化）
            Method methodGetPackageManager = clzClassActivityThread.getDeclaredMethod("getPackageManager");
            methodGetPackageManager.setAccessible(true);
            Object sPackageManager = methodGetPackageManager.invoke(null);
            //构建pm动态代理实例，包裹在真实pm之外
            Class<?> clzIPackageManager = Class.forName("android.content.pm.IPackageManager");
            Object proxyPackageManager = Proxy.newProxyInstance(clzClassActivityThread.getClassLoader(),
                    new Class[]{clzIPackageManager},
                    new FakePackageManager(application, sPackageManager)
            );
            //将pm动态代理设置回原来的sPackageManager位置
            Field fieldIPackageManager = clzClassActivityThread.getDeclaredField("sPackageManager");
            fieldIPackageManager.setAccessible(true);
            fieldIPackageManager.set(null, proxyPackageManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class FakePackageManager implements InvocationHandler {
        private Application mApplication;
        private Object mRealPackageManager;
        //此处的数据就是模拟原有的apk签名。具体获取方法可以通过自行写demo，获得app的签名数据（别的app数据也能获取到）
        byte[] fakeSignature = Base64.decode(SIGN, Base64.DEFAULT);

        public FakePackageManager(Application application, Object realPackageManager) {
            mApplication = application;
            mRealPackageManager = realPackageManager;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.i("SignatureHookTool", "invoke:" + method.getName());
            if (method.getName().equals("getPackageInfo")) {
                String pkgName = "";
                if (args != null && args.length > 0) {
                    if (args[0] instanceof String) {
                        pkgName = (String) args[0];
                    }
                }
                if (mApplication.getPackageName().equals(pkgName)) {
                    Signature signature = new Signature(fakeSignature);
                    PackageInfo info = (PackageInfo) method.invoke(mRealPackageManager, args);
                    if (info.signatures == null) {
                        info.signatures = new Signature[1];
                    }
                    info.signatures[0] = signature;
                    Log.i("SignatureHookTool", "hook success");
                    return info;
                } else {
                    return method.invoke(mRealPackageManager, args);
                }
            } else {
                return method.invoke(mRealPackageManager, args);
            }
        }
    }
}
