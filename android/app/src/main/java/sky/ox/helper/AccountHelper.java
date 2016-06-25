package sky.ox.helper;

import android.text.TextUtils;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;

import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import sky.ox.beans.MockUser;
import sky.ox.beans.User;

/**
 * Created by sky on 6/15/16.
 */
public class AccountHelper {
    public static final int SIGNUP_ERROR = -1;
    public static final int LOGIN_ERROR = -2;
    public static final int LOGOUT_ERROR = -3;

    public static void signUp(String email, String pwd, String phone, String avatar, final Callback callback) {
        AVUser user = new AVUser();
        user.setUsername(email);
        user.setEmail(email);
        user.setPassword(pwd);
        if (!TextUtils.isEmpty(phone)) {
            user.setMobilePhoneNumber(phone);
        }
        if (!TextUtils.isEmpty(avatar)) {
            user.put(User.AVATAR_KEY, avatar);
        }
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    callback.onSuccess(null);
                    getLoginSubject().onNext(true);
                } else {
                    callback.onFailed(SIGNUP_ERROR, null);
                    getLoginSubject().onNext(false);
                }
            }
        });
    }

    public static boolean isLogin() {
        return AVUser.getCurrentUser() != null;
    }

    public static void login(String email, String pwd, final Callback callback) {
        AVUser.logInInBackground(email, pwd, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {
                    callback.onSuccess(null);
                    getLoginSubject().onNext(true);
                } else {
                    callback.onFailed(LOGIN_ERROR, null);
                    getLoginSubject().onNext(false);
                }
            }
        });
    }

    public static void logout(Callback callback) {
        AVUser.logOut();
        callback.onSuccess(null);

        getLoginSubject().onNext(false);
    }

    public static User getCurrentUser() {
        AVUser user = AVUser.getCurrentUser();
        if (user != null) {
            return new User(AVUser.getCurrentUser());
        } else {
            return null;
        }
    }

    /**
     * Login info.
     */
    private static SerializedSubject<Boolean, Boolean> mSubject = new SerializedSubject<>(PublishSubject.create());
    public static SerializedSubject<Boolean, Boolean> getLoginSubject() {
        return mSubject;
    }

}
