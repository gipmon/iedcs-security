package player;

import org.json.JSONObject;

public class Result{
    private final int status_code;
    private final JSONObject result;

    public Result(int status_code, JSONObject result) {
        this.status_code = status_code;
        this.result = result;
    }

    public int getStatusCode(){
        return this.status_code;
    }

    public JSONObject getResult(){
        return this.result;
    }
}
