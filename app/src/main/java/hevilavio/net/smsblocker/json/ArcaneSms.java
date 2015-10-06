package hevilavio.net.smsblocker.json;

import hevilavio.net.smsblocker.pojo.Sms;

/**
 * Created by hevilavio on 10/6/15.
 */
public class ArcaneSms {

    private String userName; // usuario que recebeu a msg
    private String fromNumber; // numero que enviou
    private String body; // conteudo da mensagem

    public ArcaneSms() {
    }

    public static ArcaneSms buildFromSms(String userName, Sms sms) {

        ArcaneSms arcaneSms = new ArcaneSms();
        arcaneSms.userName = userName;
        arcaneSms.fromNumber = sms.getForm();
        arcaneSms.body = sms.getBody();

        return arcaneSms;
    }

    public String getUserName() {
        return userName;
    }

    public String getFromNumber() {
        return fromNumber;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Sms{");
        sb.append("userName='").append(userName).append('\'');
        sb.append(", fromNumber='").append(fromNumber).append('\'');
        sb.append(", body='").append(body).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
