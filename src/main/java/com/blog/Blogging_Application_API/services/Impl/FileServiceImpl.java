package com.blog.Blogging_Application_API.services.Impl;

import com.blog.Blogging_Application_API.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {


    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        // file name
        String name = file.getOriginalFilename();
        // abc.png

        // random name generate file
        String randomID = UUID.randomUUID().toString();
        String fileName = randomID.concat(name.substring(name.lastIndexOf(".")));

        // fullpath
        String filePath = path + File.separator + fileName;


        // create folder if not created
        File f = new File(path);
        if (!f.exists()){
            f.mkdir();
        }


        // file copy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName;

    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {

        String fullPath = path + File.separator + fileName;

        // db logic to return inputstream
        InputStream is = new FileInputStream(fullPath);

        return is;
    }
}
