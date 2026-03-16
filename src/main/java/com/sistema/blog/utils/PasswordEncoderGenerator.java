package com.sistema.blog.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Utility class used during development to generate BCrypt-hashed passwords.
 *
 * <p><b>Usage:</b> Run the {@code main} method to print a BCrypt hash
 * of the literal string {@code "password"} to the console, then use
 * that hash when seeding the database with user records.</p>
 *
 * <p>This class is not part of the application runtime and should not
 * be deployed in production.</p>
 */
public class PasswordEncoderGenerator {

    /**
     * Encodes the string {@code "password"} using BCrypt and prints the result.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("password"));
    }

}
