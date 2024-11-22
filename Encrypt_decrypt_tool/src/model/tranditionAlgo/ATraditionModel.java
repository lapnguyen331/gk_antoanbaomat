package model.tranditionAlgo;

import java.util.Map;

//đây là abtract class nhằm giúp việc lưu trữ dữ liệu vào model
public abstract class ATraditionModel {

    public abstract void saveData(Map<String, Object> data);

    public abstract Map<String, Object> loadData();
}
