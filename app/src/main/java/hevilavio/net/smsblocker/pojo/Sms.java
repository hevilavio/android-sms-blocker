package hevilavio.net.smsblocker.pojo;

/**
 *
 * Created by hevilavio on 10/5/15.
 */
public class Sms {

    private final int id;
    private final String form;
    private final String body;


    public Sms(int id, String form, String body) {
        this.id = id;
        this.form = form;
        this.body = body;
    }

    public int getId() {
        return id;
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
