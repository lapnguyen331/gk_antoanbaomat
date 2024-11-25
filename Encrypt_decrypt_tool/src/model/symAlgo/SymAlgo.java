package model.symAlgo;

import java.util.Map;

public abstract class SymAlgo {
    public abstract void saveData(Map<String, Object> data);

    public abstract Map<String, Object> loadData();
}
