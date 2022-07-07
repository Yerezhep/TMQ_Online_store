package kz.tmq.tmq_online_store.util;

import kz.tmq.tmq_online_store.domain.business.Product;
import kz.tmq.tmq_online_store.domain.business.ProductImage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Component
public class FileUploadUtil {

//    public final String UPLOAD_DIR = new ClassPathResource("static/image/").getFile().getAbsolutePath();
    public static final String UPLOAD_DIR = "C:\\Users\\Е\\Desktop\\pp\\storage\\product-images";

    public FileUploadUtil() throws IOException {
    }

    public String uploadFile(MultipartFile multipartFile) {
        String fileName = null;
        try {
            String originalFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            int index = originalFileName.lastIndexOf('.');

            String format = originalFileName.substring(index + 1);

            fileName = UUID.randomUUID().toString() + "." + format;
            Files.copy(multipartFile.getInputStream(), Paths.get(UPLOAD_DIR + File.separator + fileName),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }


    public void uploadImages(List<MultipartFile> files, Product creatingProduct, List<ProductImage> productImages) {
        for (MultipartFile file:
                files) {
            String fileName = this.uploadFile(file);
            String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(fileName).toUriString();
            ProductImage productImage = new ProductImage();
            productImage.setUrl(imageUrl);
            productImage.setProduct(creatingProduct);
            productImages.add(productImage);
        }
    }
}
