package com.grayherring.databinding.data;

/**
 * Created by davidmedina on 4/22/16.
 */
public class SwagDataCenter  {

  private static DataCenter instance;

  public static DataCenter init(DataCenter dataCenter) {
    if (instance == null) {
      synchronized (SwagDataCenter.class) {
        if (instance == null) {
          instance = dataCenter;
        }
      }
    }
    return instance;
  }

  public static DataCenter init() {
    if (instance == null) {
      synchronized (SwagDataCenter.class) {
        if (instance == null) {
          instance = new DefaultDataCenter();
        }
      }
    }
    return instance;
  }

  public static DataCenter getInstance() {
    if (instance == null) {
      throw new InstantiationError(
          "DataManager was never set up. Call DataManager.init(dataProviders)");
    }
    return instance;
  }


}
