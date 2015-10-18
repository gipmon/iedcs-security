package player;

import org.json.JSONObject;

public class Result{
    private final int status_code;
    private final Object result;

    public Result(int status_code, Object result) {
        this.status_code = status_code;
        this.result = result;
    }

    public int getStatusCode(){
        return this.status_code;
    }

    public Object getResult(){
        return this.result;
    }
}
