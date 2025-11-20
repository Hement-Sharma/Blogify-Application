package com.codeWithhHemant.blog.services.impl;


import com.codeWithhHemant.blog.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {

        //file Name
        String fileName = file.getOriginalFilename();

        //generating random image/file name, just to avoid duplicasy of file names
        String randomID = UUID.randomUUID().toString();
        String newFileName = randomID.concat(fileName.substring(fileName.lastIndexOf("."))); //taking extension of image and concating in random name.

        //Full Path
        String filePath = path + File.separator + newFileName;

        //create folder if not created
        File f = new File(path);
        if(!f.exists()){
            f.mkdir();
        }

        Files.copy(file.getInputStream(), Paths.get(filePath));
        return newFileName;
    }

    @Override
    public InputStream getFile(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;     //full image path.
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fullPath);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("the image file : "+fileName+" is not present on server please input right image name ");
        }
        return inputStream;
    }
}
