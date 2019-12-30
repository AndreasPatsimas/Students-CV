package gr.pada.bolosis.students_cv.utils;

import gr.pada.bolosis.students_cv.exceptions.files.FileStorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
public class MyFileUtils {

    public static void createDirectoryFile(String pathname){

        File file = new File(pathname);

        if (!file.exists()) {

            if (file.mkdir()) {

                log.info("Directory is created!");
            } else {

                log.info("Failed to create directory!");
            }
        }
    }

    public static void createMultipleDirectories(String pathname){

        File files = new File(pathname);

        if (!files.exists()) {

            if (files.mkdirs()) {

                log.info("Multiple directories are created!");
            } else {

                log.info("Failed to create multiple directories!");
            }
        }
    }

    public static void deleteDirectory(File file)
            throws IOException {

        if (file.isDirectory()) {

            if (file.list().length == 0) {

                deleteFile(file);

            } else {

                String files[] = file.list();

                for (String temp : files) {

                    deleteDirectory(new File(file, temp));
                }

                if (file.list().length == 0) {
                    deleteFile(file);
                }
            }

        } else {
            deleteFile(file);
        }
    }

    public static void deleteFile(File file){

        try{

            if(file.delete()){

                log.info(file.getName() + " is deleted!");
            }else{

                log.info("Delete operation is failed.");
            }

        }
        catch(Exception e){

            e.printStackTrace();
        }
    }

    public static void emptyDirectory(File file){
        if (file.isDirectory()) {

            if (file.list().length != 0) {

                for (String temp : file.list()) {

                    deleteFile(new File(file.getAbsolutePath() + "/" + temp));
                }
            }
        }
    }

    public static String storeFile(MultipartFile file, String dir, String username) {

        Path fileStorageLocation = Paths.get(dir + username)
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {

            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
}
