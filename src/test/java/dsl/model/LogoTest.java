package test.java.dsl.model;

import dsl.antlr.model.Logo;
import org.junit.Before;
import org.junit.Test;
import test.java.support.base.TestBase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static test.java.support.utils.Strings.TEST_JAVA_FILE_NAME;

/**
 * testing Logo class
 */
public class LogoTest extends TestBase{
    private Logo logo;

    @Before
    public void setUp() {
        logo = new Logo(TEST_JAVA_FILE_NAME);
    }

    @Test
    public void testParametrizedConstructor(){
        assertNotNull(logo);
        assertEquals(logo.getFileName(),TEST_JAVA_FILE_NAME);
    }
}
