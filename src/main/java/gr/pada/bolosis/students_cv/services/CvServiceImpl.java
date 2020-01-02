package gr.pada.bolosis.students_cv.services;

import gr.pada.bolosis.students_cv.dto.CvDto;
import gr.pada.bolosis.students_cv.exceptions.files.MyFileNotFoundException;
import gr.pada.bolosis.students_cv.utils.MyFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
@PropertySource({ "classpath:application.properties" })
public class CvServiceImpl implements CvService {

    @Value("${cv.path}")
    private String cvPath;

    @Override
    public CvDto uploadCv(MultipartFile file, String username) {

        log.info("Upload cv {} process begins", file.getOriginalFilename());

        MyFileUtils.emptyDirectory(new File(cvPath + username));

        String fileName = MyFileUtils.storeFile(file, cvPath, username);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        log.info("Upload cv process completed");

        return CvDto.builder()
                .fileName(fileName)
                .fileDownloadUri(fileDownloadUri)
                .fileType(file.getContentType())
                .size(file.getSize())
                .build();
    }

    @Override
    public Resource loadCvAsResource(String username, String fileName) {
        log.info("Load file {} as Resource process begins.", fileName);

        try {
            Path filePath = Paths.get(cvPath + username)
                    .toAbsolutePath().normalize().resolve(fileName).normalize();

            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists()) {

                log.info("Load file as Resource process completed.");

                return resource;
            }

            else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        }

        catch (MalformedURLException ex) {

            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

}
