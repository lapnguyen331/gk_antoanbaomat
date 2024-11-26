package model.asymAlgo;

import java.util.Map;

public abstract class ASymAlgo {
    public abstract void saveData(Map<String, Object> data);

    public abstract Map<String, Object> loadData();
}
