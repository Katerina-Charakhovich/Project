package by.epam.project.util;

import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * The type Hash password test.
 */
public class HashPasswordTest extends Assert {
    /**
     * Hash positive test.
     */
    @Test
    public static void hashPositiveTest() {
        String password = "123";
        String expected = "3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9" +
                "c36214dc9f14a42fd7a2fdb84856bca5c44c2";
        String actual = HashPassword.hash(password);
        assertEquals(actual,expected);
    }
}
