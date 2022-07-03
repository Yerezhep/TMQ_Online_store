package kz.tmq.tmq_online_store.business.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileUploadUtil {

    public final String UPLOAD_DIR = new ClassPathResource("static/image/").getFile().getAbsolutePath();

    public FileUploadUtil() throws IOException {
    }

    public boolean uploadFile(MultipartFile multipartFile) {
        boolean file = false;

        try {
            Files.copy(multipartFile.getInputStream(), Paths.get(UPLOAD_DIR + File.separator + multipartFile.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
            file = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
}
