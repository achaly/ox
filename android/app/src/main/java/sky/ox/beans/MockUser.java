package sky.ox.beans;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by sky on 6/21/16.
 */
@SuppressLint("ParcelCreator")
public class MockUser extends User {

    String email = "testuser";
    String pwd;
    String phone;
    String avatar = "http://78re52.com1.z0.glb.clouddn.com/resource/gogopher.jpg?imageView2/1/w/200/h/200";

    public String getPwd() {
        return pwd;
    }

    public MockUser() {
    }

    public MockUser(String email, String pwd, String phone) {
        this.email = email;
        this.pwd = pwd;
        this.phone = phone;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getAvatarUrl() {
        return avatar;
    }

    @Override
    public String getPhoneNumber() {
        return phone;
    }

    @Override
    public String getStatusesCount() {
        Random random = new Random();
        return String.valueOf(random.nextInt(100));
    }

    @Override
    public List<String> getInterestCategory() {
        return Collections.EMPTY_LIST;
    }

    public static List<User> createMockUserList() {
        return new ArrayList<User>() {{
            add(new MockUser());
            add(new MockUser());
            add(new MockUser());
            add(new MockUser());
            add(new MockUser());
            add(new MockUser());
            add(new MockUser());
            add(new MockUser());
            add(new MockUser());
            add(new MockUser());
            add(new MockUser());
            add(new MockUser());
            add(new MockUser());
            add(new MockUser());
            add(new MockUser());
            add(new MockUser());
        }};
    }

    public static List<MockUser> createSignUpUserList() {
        return new ArrayList<MockUser>() {{
            add(new MockUser("test1@163.com", "test1", "13711112222"));
            add(new MockUser("test2@163.com", "test2", "13711122222"));
            add(new MockUser("test3@163.com", "test3", "13711132222"));
            add(new MockUser("test4@163.com", "test4", "13711142222"));
            add(new MockUser("test5@163.com", "test5", "13711152222"));
            add(new MockUser("test6@163.com", "test6", "13711162222"));
            add(new MockUser("test7@163.com", "test7", "13711172222"));
            add(new MockUser("test8@163.com", "test8", "13711182222"));
            add(new MockUser("test9@163.com", "test9", "13711192222"));
            add(new MockUser("test10@163.com", "test10", "13711102222"));
        }};
    }


}
