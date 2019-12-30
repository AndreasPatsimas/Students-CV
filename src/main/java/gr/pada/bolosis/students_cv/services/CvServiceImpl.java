package gr.pada.bolosis.students_cv.services;

import gr.pada.bolosis.students_cv.dto.CvDto;
import gr.pada.bolosis.students_cv.exceptions.files.FileStorageException;
import gr.pada.bolosis.students_cv.exceptions.files.MyFileNotFoundException;
import gr.pada.bolosis.students_cv.utils.MyFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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

//    private String storeFile(MultipartFile file, String username) {
//
//        Path fileStorageLocation = Paths.get(cvPath + username)
//                .toAbsolutePath().normalize();
//
//        try {
//            Files.createDirectories(fileStorageLocation);
//        } catch (Exception ex) {
//            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
//        }
//
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//
//        try {
//
//            if(fileName.contains("..")) {
//                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
//            }
//
//            Path targetLocation = fileStorageLocation.resolve(fileName);
//            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//
//            return fileName;
//        } catch (IOException ex) {
//            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
//        }
//    }
}
