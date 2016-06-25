package sky.ox.helper;

import android.app.Application;
import android.util.Log;

import com.apkfuns.logutils.LogUtils;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FollowCallback;
import com.avos.avoscloud.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import sky.ox.beans.MockStatus;
import sky.ox.beans.MockUser;
import sky.ox.beans.Status;
import sky.ox.beans.StatusHelper;
import sky.ox.beans.User;

/**
 * Created by sky on 6/16/16.
 */
public class AVOSHelper {

    private static final String APP_ID = "FMw18mLUGS3JADnib79Yg253-gzGzoHsz";
    private static final String APP_KEY = "dacqXHaWiSSi18irj7yf99vo";

    public static void init(Application application) {
        AVOSCloud.initialize(application, APP_ID, APP_KEY);
    }

    public static void test() {
        AVObject testObject = new AVObject("TestObject");
        testObject.put("words", "Hello World!");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    Log.d("TEST", "success!");
                } else {
                    Log.d("TEST", "error!");
                    e.printStackTrace();
                }
            }
        });
    }

    public static void signUpUsers() {
        String avatar = "http://78re52.com1.z0.glb.clouddn.com/resource/gogopher.jpg?imageView2/1/w/200/h/200";
        List<MockUser> userList = MockUser.createSignUpUserList();
        for (MockUser user : userList) {
            AccountHelper.signUp(user.getEmail(), user.getPwd(), user.getPhoneNumber(), avatar, new Callback() {
                @Override
                public void onSuccess(Object object) {
                    LogUtils.d("sign up on success.");
                }

                @Override
                public void onFailed(int code, String message) {
                    LogUtils.d("sign up on onFailed.");
                }
            });
        }

    }

    public static void followTest1Users() {
        AVUser user = AVUser.getCurrentUser();
        if (user == null) {
            return;
        }
        if (user.getEmail().equals("test1@163.com")) {
            user.followInBackground("576916e5207703006b330aaa", new FollowCallback() {
                @Override
                public void done(AVObject avObject, AVException e) {
                    if (e == null) {
                        LogUtils.d("follow success");
                    } else {
                        LogUtils.d("follow failed");
                    }
                }
            });
            user.followInBackground("576916e5df0eea00620b0053", new FollowCallback() {
                @Override
                public void done(AVObject avObject, AVException e) {
                    if (e == null) {
                        LogUtils.d("follow success");
                    } else {
                        LogUtils.d("follow failed");
                    }
                }
            });

        }
    }

    public static void sendstatus() {
        List<Status> statuses = new ArrayList<>();
        User user = AccountHelper.getCurrentUser();
        for (int i = 0; i < 5; ++i) {

            Status status = StatusHelper.createImagesStatus(user.getEmail() + "desc" + i, "http://78re52.com1.z0.glb.clouddn.com/resource/gogopher.jpg?imageView2/1/w/200/h/200");
            StatusHelper.sendStatus(status, new Callback() {
                @Override
                public void onSuccess(Object object) {
                    LogUtils.d("send status success");
                }

                @Override
                public void onFailed(int code, String message) {
                    LogUtils.d("send status failed");
                }
            });
        }

        for (int i = 0; i < 5; ++i) {
            Status status = MockStatus.createMockStatus();
            status.title = user.getUsername().substring(6);
            StatusHelper.sendStatus(status, new Callback() {
                @Override
                public void onSuccess(Object object) {
                    LogUtils.d("send status success");
                }

                @Override
                public void onFailed(int code, String message) {
                    LogUtils.d("send status failed");
                }
            });
        }


    }
}
