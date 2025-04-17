package com.sidn.metruyenchu.fileservice.utils;


import com.sidn.metruyenchu.fileservice.exception.ErrorCode;
import com.sidn.metruyenchu.fileservice.exception.AppException;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.query.sqm.produce.function.FunctionArgumentException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class FileUtils {
    public static final long MAX_FILE_SIZE = 10485760;

    public static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp|txt))$)";

    public static final String IMAGE_TYPE = "image";

    public static final String DATA_FORMAT = "yyyyMMddHHmmss";

    public static final String FILE_NAME_FORMAT = "%s_%s";

    public static boolean isAllowedExtension(final String fileName,final String pattern) {
        final Matcher matcher = Pattern.compile(pattern).matcher(fileName);

        return matcher.matches();
    }

    public static void assertAllowed(MultipartFile file, String pattern){
        final long fileSize = file.getSize();
        if (fileSize > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File size is too large");
        }
        final String fileName = file.getOriginalFilename();
        final String extension = FilenameUtils.getExtension(fileName);

        if (!isAllowedExtension(file.getOriginalFilename(), pattern)) {
            throw new FunctionArgumentException("File extension is not allowed");
        }

    }

    public static String getFileName(final String name){
        final DateFormat dateFormat = new SimpleDateFormat(DATA_FORMAT);
        final String date = dateFormat.format(System.currentTimeMillis());

        return String.format(FILE_NAME_FORMAT, name, date);
    }

    //Loai bo phan mo rong cua file
    public static String extractFilename(final String fileName){
        return FilenameUtils.getBaseName(fileName);
    }

    //Lay phan mo rong cua file
    public static String extractExtension(final String fileName){
        return FilenameUtils.getExtension(fileName);
    }
    //Loai bo phan mo rong cua file
    public static String removeExtension(final String fileName){
        return FilenameUtils.removeExtension(fileName);
    }

    public static void writeFileContentToDisk(Path absolutePath, byte[] content) {
        try {
            Files.write(absolutePath, content);
        } catch (IOException e) {
            throw new AppException(ErrorCode.FILE_UPDATE_FAILED);
        }
    }



}
