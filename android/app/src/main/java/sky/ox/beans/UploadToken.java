package sky.ox.beans;

import java.util.Locale;

/**
 * Created by sky on 6/23/16.
 */
public class UploadToken {

    public String token;

    public String domain;

    public UploadToken() {
    }

    public UploadToken(String token, String domain) {
        this.token = token;
        this.domain = domain;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "token %s, domain %s", token, domain);
    }
}
