package com.mmrath.sapp.security;

import java.util.HashMap;
import java.util.Map;

public enum Module {

  USER(1, "User"),
  ROLE(2, "Role"),
  USER_GROUP(3, "User Group");

  private static final Map<Integer, Module> lookup = new HashMap<>();

  static {
    for (Module item : Module.values())
      lookup.put(item.getValue(), item);
  }

  private int value;

  private String display;

  private Module(int val, String display) {
    this.value = val;
    this.display = display;
  }

  public static Module getKey(Integer value) {
    return lookup.get(value);
  }

  public Integer getValue() {
    return value;
  }


}
