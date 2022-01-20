package it.fsal.webclient.api.util;

import java.io.IOException;
import java.io.Reader;

public class StringsUtil {

    private StringsUtil() {
        //utility class
    }

    public static String fromReader(Reader reader) throws IOException {
        char[] buffer = new char[4 * 1024]; //4KiB
        StringBuilder builder = new StringBuilder();
        int numChars;

        while ((numChars = reader.read(buffer)) >= 0) {
            builder.append(buffer, 0, numChars);
        }

        return builder.toString();
    }

    public static String fromReader(Reader reader, Integer charLimit) throws IOException, IllegalArgumentException {
        char[] buffer = new char[8192]; //8KiB
        StringBuilder builder = new StringBuilder();
        int numChars;

        while ((numChars = reader.read(buffer)) >= 0) {
            builder.append(buffer, 0, numChars);

            if (charLimit != null && builder.length() > charLimit) {
                throw new IllegalArgumentException(String.format("Input buffer too long for limit %d", charLimit));
            }
        }

        return builder.toString();
    }

    public static boolean isBlank(final CharSequence cs) {
        if (cs != null && cs.length() > 0) {
            for(int i = 0; i < cs.length(); ++i) {
                char c = cs.charAt(i);
                if (!Character.isWhitespace(c)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean isNumber(final CharSequence cs) {
        if (cs != null && cs.length() > 0) {
            for(int i = 0; i < cs.length(); ++i) {
                char c = cs.charAt(i);
                if (!Character.isDigit(c)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean isNotNumber(final CharSequence cs) {
        return !isNumber(cs);
    }

}
