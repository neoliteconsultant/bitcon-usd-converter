package v1.com.tonym.bitcoinusdconverter.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


public class FileUtil {

    /**
     * Read file from class path
     * @param fileName
     * @return file contents
     */
    public static String readFile(String fileName){
        String output = "";
        try {
            Resource resource = new ClassPathResource(fileName);
            InputStream inputStream = resource.getInputStream();
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            output = new String(bdata, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }

}
