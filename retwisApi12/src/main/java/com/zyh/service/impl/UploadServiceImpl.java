package com.zyh.service.impl;


import com.zyh.util.TimeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
//
//@Transactional
//@Service
//public class UploadServiceImpl implements UploadService {
//
//    final String path=System.getProperty("user.dir")+ "/src/main/resources/static/image/";
//    final String returnName="http://localhost:8888/retwisApi/image";
////
////    public UploadServiceImpl() throws FileNotFoundException {
////    }
//
//
//    @Override
//    public String uploadFile(MultipartFile file)  {
//        Date date=new Date();
//        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
//        String pathName =TimeUtil.stampToDay(date.getTime())+UUID.randomUUID().toString()+"."+suffix;
//        File tmpFile=new File(path+pathName);
//        try {
//            file.transferTo(tmpFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return returnName+pathName;
//    }
//}
