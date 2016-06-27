package sky.ox.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.FollowCallback;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import sky.ox.helper.Callback;

/**
 * Created by sky on 6/16/16.
 */
public class User implements UserOperation, Parcelable {
    public static final String AVATAR_KEY = "avatarUrl";

    public static final int FOLLOW_ERROR = -1;
    public static final int UN_FOLLOW_ERROR = -2;
    public static final int GET_FOLLOWEE_ERROR = -3;

    private AVUser user;

    public User() {
    }

    public User(AVUser user) {
        if (user == null) {
            throw new IllegalArgumentException("user is null!");
        }
        this.user = user;
    }

    public AVUser getAVUser() {
        return user;
    }

    @Override
    public String toString() {
        if (this.user != null) {
            return this.user.toString();
        }
        return super.toString();
    }

    /**
     * UserOperation
     */
    @Override
    public String getId() {
        return this.user.getObjectId();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public String getEmail() {
        return this.user.getEmail();
    }

    @Override
    public String getPhoneNumber() {
        return this.user.getMobilePhoneNumber();
    }

    @Override
    public String getAvatarUrl() {
        return (String) this.user.get(AVATAR_KEY);
    }

    @Override
    public String getStatusesCount() {
        // TODO: 6/25/16
        return null;
    }

    @Override
    public List<String> getInterestCategory() {
        // TODO: 6/21/16
        return null;
    }

    @Override
    public void hasFollowUser(User user, Callback callback) {
        try {
            AVQuery<AVUser> query = this.user.followeeQuery(AVUser.class);
            query.whereEqualTo("followee", user.getId());
            query.findInBackground(new FindCallback<AVUser>() {
                @Override
                public void done(List<AVUser> list, AVException e) {
                    if (e == null && list != null && list.size() == 1) {
                        callback.onSuccess(true);
                    } else {
                        callback.onSuccess(false);
                    }
                }
            });
        } catch (AVException e) {
            e.printStackTrace();
            callback.onSuccess(false);
        }
    }

    @Override
    public void followUser(User user, Callback callback) {
        this.user.followInBackground(user.getId(), new FollowCallback() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if (e == null) {
                    callback.onSuccess(null);
                } else {
                    callback.onFailed(FOLLOW_ERROR, null);
                }
            }
        });
    }

    @Override
    public void unFollowUser(User user, Callback callback) {
        this.user.unfollowInBackground(user.getId(), new FollowCallback() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if (e == null) {
                    callback.onSuccess(null);
                } else {
                    callback.onFailed(UN_FOLLOW_ERROR, null);
                }
            }
        });
    }

    @Override
    public void getFolloweeList(Callback callback) {
        try {
            AVQuery<AVUser> query = this.user.followeeQuery(AVUser.class);
            query.include("followee");
            query.setLimit(20);
            query.orderByDescending("updatedAt");
            query.findInBackground(new FindCallback<AVUser>() {
                @Override
                public void done(List<AVUser> list, AVException e) {
                    if (e == null && list != null) {
                        Observable.from(list)
                                .filter(new Func1<AVUser, Boolean>() {
                                    @Override
                                    public Boolean call(AVUser avUser) {
                                        return avUser != null;
                                    }
                                })
                                .map(new Func1<AVUser, User>() {
                                    @Override
                                    public User call(AVUser avUser) {
                                        return new User(avUser);
                                    }
                                })
                                .toList()
                                .subscribe(new Action1<List<User>>() {
                                    @Override
                                    public void call(List<User> users) {
                                        callback.onSuccess(users);
                                    }
                                });

                    } else {
                        callback.onFailed(GET_FOLLOWEE_ERROR, null);
                    }
                }
            });
        } catch (AVException e) {
            e.printStackTrace();
            callback.onFailed(GET_FOLLOWEE_ERROR, null);
        }
    }

    @Override
    public void sendStatus(Status status, Callback callback) {
        StatusHelper.sendStatus(status, callback);
    }

    @Override
    public void getInboxList(String inboxType, Callback callback) {
        StatusHelper.getInboxList(this, inboxType, callback);
    }

    @Override
    public void getStatusList(Callback callback) {
        StatusHelper.getStatusList(this, callback);
    }


    /**
     * parcel
     */
    protected User(Parcel in) {
        user = in.readParcelable(AVUser.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
    }

}
