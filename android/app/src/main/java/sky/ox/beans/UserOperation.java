package sky.ox.beans;

import com.avos.avoscloud.AVUser;

import java.util.List;

import sky.ox.helper.Callback;

/**
 * Created by sky on 6/16/16.
 */
public interface UserOperation {
    /**
     * 用户唯一标识的ID
     * @return
     */
    String getId();

    /**
     * 取得用户名
     * @return
     */
    String getUsername();

    /**
     * 取得用户注册邮件
     * @return
     */
    String getEmail();

    /**
     * 取得用户手机号
     * @return
     */
    String getPhoneNumber();

    /**
     * 取得用户头像
     * @return
     */
    String getAvatarUrl();

    /**
     * 获得用户作品数目
     * @return
     */
    String getStatusesCount();

    /**
     * 获得用户标签
     */
    List getInterestCategory();

    /**
     * 是否关注某人
     * @param user
     * @param callback
     */
    void hasFollowUser(User user, Callback callback);
    /**
     * 关注
     * @param user
     * @param callback
     */
    void followUser(User user, Callback callback);

    /**
     * 取消关注
     * @param user
     * @param callback
     */
    void unFollowUser(User user, Callback callback);

    /**
     * 获取关注列表
     * @param callback
     */
    void getFolloweeList(Callback callback);

    /**
     * 发布作品
     * @param status
     * @param callback
     */
    void sendStatus(Status status, Callback callback);

    /**
     * 获取他人作品列表
     * @param inboxType
     * @param callback
     */
    void getInboxList(String inboxType, Callback callback);

    /**
     * 获取自己发布的作品列表
     * @param callback
     */
    void getStatusList(Callback callback);

}
