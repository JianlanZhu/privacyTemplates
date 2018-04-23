package edu.cmu.core.util;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;


public class SecureTokenGeneratorTest {

    @Test
    public void nextToken() {
        String token = SecureTokenGenerator.nextToken();

        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(token);

        assertThat(m.find()).isFalse();
    }
}