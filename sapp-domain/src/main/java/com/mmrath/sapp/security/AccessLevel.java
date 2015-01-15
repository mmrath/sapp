package com.mmrath.sapp.security;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Murali on 8/6/13.
 */
public enum AccessLevel {

  READ(1, "Read"),
  CREATE(2, "Create"),
  UPDATE(3, "Update"),
  DELETE(4, "Delete");


  private static final Map<Integer, AccessLevel> lookup = new HashMap<>();

  static {
    for (AccessLevel item : AccessLevel.values())
      lookup.put(item.getValue(), item);
  }

  private int value;

  private String display;

  private AccessLevel(int val, String name) {
    this.value = val;
    this.display = name;
  }

  public static AccessLevel getKey(Integer value) {
    return lookup.get(value);
  }

  public Integer getValue() {
    return value;
  }

  public String getDisplay() {
    return display;
  }

}
