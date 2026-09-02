package com.svlogic.opoppr.util;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.inject.Named;

/**
 *
 * @author David
 */
@Named("randomPasswordGenerator")
public class RandomPasswordGenerator implements Serializable {

    private static final String UPPERCASE = "ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijkmnopqrstuvwxyz";
    private static final String NUMBERS = "2345679";
    private static final String SPECIAL_CHARS = "!@";
    private static final String ALL_CHARS = UPPERCASE + LOWERCASE + NUMBERS + SPECIAL_CHARS;

    private static final int PASSWORD_LENGTH = 12; // Desired password length
    private String generatedPassword;
    private final static SecureRandom random = new SecureRandom();

  public void generateSecurePassword() {


        char[] password = new char[PASSWORD_LENGTH];

        // Ensure at least one of each required character type is included
        password[0] = UPPERCASE.charAt(random.nextInt(UPPERCASE.length()));
        password[1] = LOWERCASE.charAt(random.nextInt(LOWERCASE.length()));
        password[2] = NUMBERS.charAt(random.nextInt(NUMBERS.length()));
        password[3] = SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length()));

        // Fill the rest of the password length with random characters from the combined set
        for (int i = 4; i < PASSWORD_LENGTH; i++) {
            password[i] = ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length()));
        }

        // Shuffle the characters to ensure random placement
        List<Character> charList = new String(password).chars()
                                                      .mapToObj(c -> (char) c)
                                                      .collect(Collectors.toList());
        Collections.shuffle(charList);

        // Convert the shuffled list back to a String
        this.generatedPassword =  charList.stream().map(String::valueOf).collect(Collectors.joining());
    }



    public String getGeneratedPassword() {

        if (generatedPassword == null) {
             generateSecurePassword(); // Generate on first access if needed
        }
        return generatedPassword;
    }

}
