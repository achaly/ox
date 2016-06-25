package sky.ox.beans;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by sky on 6/21/16.
 */
@SuppressLint("ParcelCreator")
public class MockStatus extends Status {

    public MockStatus() {
    }

    public static Status createMockStatus() {
        MockStatus status = new MockStatus();
        Random random = new Random();
//        int r = random.nextInt(3);
        int r = 3;
        switch (r) {
            case 0:
                status.statusType = StatusType.IMAGE;
                status.title = "title";
                status.message = "message";
                status.images = new ArrayList<String>() {{
                    add("");
                }};
                break;
            case 1:
                status.statusType = StatusType.VIDEO;
                status.title = "title";
                status.message = "message";
                status.video = "";
                break;
            case 2:
                status.statusType = StatusType.AUDIO;
                status.title = "title";
                status.message = "message";
                status.audio = "";
                break;
            case 3:
                status.statusType = StatusType.TEXT;
                status.title = "title";
                status.message = "message message message message message message message message message message.";
                break;
        }
        status.inboxType = InboxType.ALL;

        return status;

    }

    public static List<Status> createMockStatusList() {
        List<Status> statuses = new ArrayList<>(20);
        for (int i = 0; i < 20; ++i) {
            statuses.add(createMockStatus());
        }
        return statuses;
    }

}
