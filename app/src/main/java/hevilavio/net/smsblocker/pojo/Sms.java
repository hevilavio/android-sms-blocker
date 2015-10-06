package hevilavio.net.smsblocker.pojo;

import java.util.Calendar;

/**
 *
 * Created by hevilavio on 10/5/15.
 */
public class Sms {

    private String form;
    private String body;

    public Sms(String form, String body) {
        this.form = form;
        this.body = body;
    }

    public String getForm() {
        return form;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Sms{" +
                "form='" + form + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
