package com.shopme.admin.user;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {

    @Test
    public void testEncodePassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "123";
        String encodePassword = passwordEncoder.encode(rawPassword);

        System.out.println(encodePassword);

        boolean matches = passwordEncoder.matches(rawPassword, encodePassword);
        Assert.assertTrue(matches);
    }
}
