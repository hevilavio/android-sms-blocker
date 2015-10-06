package hevilavio.net.smsblocker.json;

/**
 * Created by hevilavio on 10/6/15.
 */
public class ArcaneApiResult {

    private String status;
    private String message;

    private ArcaneApiResult() {
    }

    private ArcaneApiResult(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ArcaneApiResult buildSuccess(String message){
        return new ArcaneApiResult("OK", message);
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ArcaneApiResult{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
