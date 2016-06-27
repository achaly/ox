package sky.ox.beans;

import android.content.Context;
import android.content.Intent;

import com.apkfuns.logutils.LogUtils;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVStatus;
import com.avos.avoscloud.AVStatusQuery;
import com.avos.avoscloud.CountCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import sky.ox.helper.AccountHelper;
import sky.ox.helper.Callback;
import sky.ox.ui.activity.SendStatusActivity;

/**
 * Created by sky on 6/16/16.
 */
public final class StatusHelper {

    public static Status createStatus(String inboxType, String statusType,
                                          List<User> filterUsers, List<User> exceptUsers,
                                          String title, String message, String... uris) {
        Status status = new Status();
        status.source = AccountHelper.getCurrentUser();
        status.inboxType = inboxType;
        status.statusType = statusType;
        status.filterUsers = filterUsers;
        status.exceptUsers = exceptUsers;
        status.title = title;
        status.message = message;
        switch (statusType) {
            case StatusType.IMAGE:
                status.images = Arrays.asList(uris);
                break;
            case StatusType.VIDEO:
                if (uris.length > 0) {
                    status.video = uris[0];
                }
                break;
            case StatusType.AUDIO:
                if (uris.length > 0) {
                    status.audio = uris[0];
                }
                break;
        }

        return status;
    }

    public static Status createImagesStatus(String title, String message, String... uris) {
        return createStatus(InboxType.ALL, StatusType.IMAGE, null, null, title, message, uris);
    }

    public static Status createVideosStatus(String title, String message, String uri) {
        return createStatus(InboxType.ALL, StatusType.VIDEO, null, null, title, message, uri);
    }

    public static Status createAudiosStatus(String title, String message, String uri) {
        return createStatus(InboxType.ALL, StatusType.AUDIO, null, null, title, message, uri);
    }

    public static Status createTextStatus(String title, String message) {
        return createStatus(InboxType.ALL, StatusType.TEXT, null, null, title, message);
    }

    public static void sendStatus(Status status, Callback callback) {
        status.toAVObject().saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    callback.onSuccess(null);
                } else {
                    e.printStackTrace();
                    callback.onFailed(Status.SEND_STATUS_ERROR, null);
                }
            }
        });
    }

    public static void getInboxList(User user, String inboxType, Callback callback) {
        try {
            AVQuery<AVObject> query = AVQuery.getQuery(Status.CLASS_NAME);
            query.setLimit(50);
            query.orderByDescending("updatedAt");
            query.whereEqualTo(Status.INBOX_TYPE_KEY, inboxType);
            query.whereEqualTo(Status.SOURCE_KEY, user.getAVUser());
            query.include(Status.SOURCE_KEY);
            query.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    if (e == null) {
                        Observable.from(list)
                                .filter(new Func1<AVObject, Boolean>() {
                                    @Override
                                    public Boolean call(AVObject avStatus) {
                                        return avStatus != null;
                                    }
                                })
                                .map(new Func1<AVObject, Status>() {
                                    @Override
                                    public Status call(AVObject avStatus) {
                                        return new Status(avStatus);
                                    }
                                })
                                .toList()
                                .subscribe(new Action1<List<Status>>() {
                                    @Override
                                    public void call(List<Status> statuses) {
                                        callback.onSuccess(statuses);
                                    }
                                });

                    } else {
                        callback.onFailed(Status.GET_INBOX_ALL_LIST_ERROR, null);
                    }
                }
            });

        } catch (Throwable e) {
            e.printStackTrace();
            callback.onFailed(Status.GET_INBOX_ALL_LIST_ERROR, null);
        }

    }

    public static void getAllStatus(Callback callback) {
        try {
            AVQuery<AVObject> query = new AVQuery<>("Works");
            query.setLimit(50);
            query.orderByDescending("updatedAt");
            query.whereEqualTo(Status.INBOX_TYPE_KEY, InboxType.ALL);
            query.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    if (e == null) {
                        Observable.from(list)
                                .filter(new Func1<AVObject, Boolean>() {
                                    @Override
                                    public Boolean call(AVObject avStatus) {
                                        return avStatus != null;
                                    }
                                })
                                .map(new Func1<AVObject, Status>() {
                                    @Override
                                    public Status call(AVObject avStatus) {
                                        return new Status(avStatus);
                                    }
                                })
                                .toList()
                                .subscribe(new Action1<List<Status>>() {
                                    @Override
                                    public void call(List<Status> statuses) {
                                        callback.onSuccess(statuses);
                                    }
                                });

                    } else {
                        callback.onFailed(Status.GET_INBOX_ALL_LIST_ERROR, null);
                    }
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
            callback.onFailed(Status.GET_INBOX_ALL_LIST_ERROR, null);
        }
    }

    public static void getStatusList(User user, Callback callback) {
        try {
            AVQuery<AVObject> query = AVQuery.getQuery(Status.CLASS_NAME);
            query.setLimit(50);
            query.whereEqualTo(Status.SOURCE_KEY, user.getAVUser());
            query.orderByDescending("updatedAt");
            query.include(Status.SOURCE_KEY);
            query.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    if (e == null) {
                        Observable.from(list)
                                .filter(new Func1<AVObject, Boolean>() {
                                    @Override
                                    public Boolean call(AVObject avStatus) {
                                        return avStatus != null;
                                    }
                                })
                                .map(new Func1<AVObject, Status>() {
                                    @Override
                                    public Status call(AVObject avStatus) {
                                        return new Status(avStatus);
                                    }
                                })
                                .toList()
                                .subscribe(new Action1<List<Status>>() {
                                    @Override
                                    public void call(List<Status> statuses) {
                                        callback.onSuccess(statuses);
                                    }
                                });
                    } else {
                        callback.onFailed(Status.GET_STATUS_LIST_ERROR, null);
                    }
                }
            });

        } catch (Throwable e) {
            e.printStackTrace();
            callback.onFailed(Status.GET_STATUS_LIST_ERROR, null);
        }
    }

    public static void getStatusCount(User user, Callback callback) {
        try {
            AVQuery<AVObject> query = AVQuery.getQuery(Status.CLASS_NAME);
            query.whereEqualTo(Status.SOURCE_KEY, user.getAVUser());
            query.countInBackground(new CountCallback() {
                @Override
                public void done(int i, AVException e) {
                    if (e == null) {
                        callback.onSuccess(i);
                    } else {
                        callback.onFailed(Status.GET_STATUS_LIST_COUNT_ERROR, null);
                    }
                }
            });

        } catch (Throwable e) {
            e.printStackTrace();
            callback.onFailed(Status.GET_STATUS_LIST_COUNT_ERROR, null);
        }
    }

    public static Intent createSendImageIntent(Context context) {
        Intent intent = new Intent(context, SendStatusActivity.class);
        intent.putExtra("statusType", StatusType.IMAGE);
        return intent;
    }

    public static Intent createSendVideoIntent(Context context) {
        Intent intent = new Intent(context, SendStatusActivity.class);
        intent.putExtra("statusType", StatusType.VIDEO);
        return intent;
    }

    public static Intent createSendAudioIntent(Context context) {
        Intent intent = new Intent(context, SendStatusActivity.class);
        intent.putExtra("statusType", StatusType.AUDIO);
        return intent;
    }

    public static Intent createSendTextIntent(Context context) {
        Intent intent = new Intent(context, SendStatusActivity.class);
        intent.putExtra("statusType", StatusType.TEXT);
        return intent;
    }
}
