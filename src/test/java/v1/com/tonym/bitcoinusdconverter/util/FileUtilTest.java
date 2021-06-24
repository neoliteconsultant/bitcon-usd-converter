package v1.com.tonym.bitcoinusdconverter.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileUtilTest {
    @Test
    public void testReadFile(){
        final String output = FileUtil.readFile("test.txt");
        assertEquals("Hello World", output);
    }
}
