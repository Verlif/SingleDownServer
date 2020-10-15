package sd.model.result;

import java.util.List;

public class SusResult extends Result {

    public SusResult() {
        this.code = Result.CODE_SUCCESS;
    }

    public SusResult(Object object) {
        super(Result.CODE_SUCCESS, object);
    }

    public <T> SusResult(String key, List<T> list) {
        super(Result.CODE_SUCCESS, key, list);
    }
}
