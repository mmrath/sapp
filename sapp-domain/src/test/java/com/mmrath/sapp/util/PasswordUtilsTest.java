package com.mmrath.sapp.util;

import org.junit.Test;

public class PasswordUtilsTest {

  @Test
  public void testIsPasswordValidForEncodedPassword() throws Exception {
    String salt = PasswordUtils.generateSalt();
    String password = "Secret@123";

    String encodedPassword = PasswordUtils.encodePassword(password, salt);
    //System.out.print(encodedPassword);
    Assert.isTrue(PasswordUtils.isPasswordValid(encodedPassword, password, salt));
  }

}