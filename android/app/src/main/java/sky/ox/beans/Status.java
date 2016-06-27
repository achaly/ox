package sky.ox.beans;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.apkfuns.logutils.LogUtils;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVStatus;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.AVUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by sky on 6/16/16.
 */
public class Status implements Parcelable {
    public static final String CLASS_NAME = "Works";

    public static final int GET_INBOX_ALL_LIST_ERROR = -1;
    public static final int GET_STATUS_LIST_ERROR = -2;
    public static final int SEND_STATUS_ERROR = -3;
    public static final int GET_STATUS_LIST_COUNT_ERROR = -4;

    public static final String SOURCE_KEY = "source";
    public static final String INBOX_TYPE_KEY = "inboxType";
    public static final String STATUS_TYPE_KEY = "statusType";
    public static final String TITLE_KEY = "title";
    public static final String MESSAGE_KEY = "message";
    public static final String IMAGE_KEY = "image";
    public static final String VIDEO_KEY = "video";
    public static final String AUDIO_KEY = "audio";

    public String inboxType;
    public String statusType;
    public List<User> filterUsers;  // not implement.
    public List<User> exceptUsers;  // not implement.
    public User source;

    public String title;
    public String message;
    public List<String> images;
    public String video;
    public String audio;

    public Status() {
    }

    public Status(AVObject object) {
        parseFromAVObject(object);
    }

    private void parseFromAVObject(AVObject object) {
        String inbox = (String) object.get(INBOX_TYPE_KEY);
        if (inbox != null) {
            switch (inbox) {
                case InboxType.ALL:
                    this.inboxType = InboxType.ALL;
                    break;
                case InboxType.PRIVATE:
                    this.inboxType = InboxType.PRIVATE;
                    break;
                default:
                    this.inboxType = InboxType.UNKNOWN;
            }
        }

        String sType = (String) object.get(STATUS_TYPE_KEY);
        switch (sType) {
            case StatusType.IMAGE:
                this.statusType = StatusType.IMAGE;

                JSONArray array = object.getJSONArray(IMAGE_KEY);
                if (this.images == null) {
                    this.images = new ArrayList<>(array.length());
                }
                for (int i = 0; i < array.length(); ++i) {
                    String img = array.optString(i);
                    if (img != null) {
                        this.images.add(img);
                    }
                }
                break;

            case StatusType.VIDEO:
                this.statusType = StatusType.VIDEO;
                this.video = (String) object.get(VIDEO_KEY);
                break;

            case StatusType.AUDIO:
                this.statusType = StatusType.AUDIO;
                this.audio = (String) object.get(AUDIO_KEY);
                break;

            case StatusType.TEXT:
                this.statusType = StatusType.TEXT;
                break;
            default:
                this.statusType = StatusType.UNKNOWN;
        }

        this.title = (String) object.get(TITLE_KEY);
        this.message = (String) object.get(MESSAGE_KEY);
        try {
            this.source = new User(object.getAVObject(SOURCE_KEY, AVUser.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AVObject toAVObject() {
        AVObject object = new AVObject(CLASS_NAME);
        object.put(INBOX_TYPE_KEY, this.inboxType);
        object.put(STATUS_TYPE_KEY, this.statusType);

        switch (this.statusType) {
            case StatusType.IMAGE:
                Observable.from(this.images)
                        .map(new Func1<String, Object>() {
                            @Override
                            public Object call(String s) {
                                object.add(IMAGE_KEY, s);
                                return null;
                            }
                        })
                        .subscribe();
                break;

            case StatusType.VIDEO:
                object.put(VIDEO_KEY, this.video);
                break;

            case StatusType.AUDIO:
                object.put(AUDIO_KEY, this.audio);
                break;
        }

        if (!TextUtils.isEmpty(this.title)) {
            object.put(TITLE_KEY, this.title);
        } else {
            object.put(TITLE_KEY, "");
        }

        if (!TextUtils.isEmpty(this.message)) {
            object.put(MESSAGE_KEY, this.message);
        } else {
            object.put(MESSAGE_KEY, "");
        }

        if (this.source != null) {
            object.put(SOURCE_KEY, this.source.getAVUser());
        }

        return object;
    }

    protected Status(Parcel in) {
        inboxType = in.readString();
        statusType = in.readString();
        filterUsers = in.createTypedArrayList(User.CREATOR);
        exceptUsers = in.createTypedArrayList(User.CREATOR);
        source = in.readParcelable(User.class.getClassLoader());
        title = in.readString();
        message = in.readString();
        images = in.createStringArrayList();
        video = in.readString();
        audio = in.readString();
    }

    public static final Creator<Status> CREATOR = new Creator<Status>() {
        @Override
        public Status createFromParcel(Parcel in) {
            return new Status(in);
        }

        @Override
        public Status[] newArray(int size) {
            return new Status[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(inboxType);
        dest.writeString(statusType);
        dest.writeTypedList(filterUsers);
        dest.writeTypedList(exceptUsers);
        dest.writeParcelable(source, flags);
        dest.writeString(title);
        dest.writeString(message);
        dest.writeStringList(images);
        dest.writeString(video);
        dest.writeString(audio);
    }
}
