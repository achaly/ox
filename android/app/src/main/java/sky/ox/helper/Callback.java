package sky.ox.helper;

/**
 * Created by sky on 6/15/16.
 */
public interface Callback {

    void onSuccess(Object object);

    void onFailed(int code, String message);

}
