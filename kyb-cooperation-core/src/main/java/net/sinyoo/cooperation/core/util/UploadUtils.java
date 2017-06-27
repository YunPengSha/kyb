package net.sinyoo.cooperation.core.util;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.PutObjectResult;

import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by yunpengsha on 2017/3/20.
 */
public class UploadUtils {

    private String bucketName = "sharing-test";

    private static final OSSClientPool pool = OSSClientPool.getPool();

    public String uploadPicture(File file, String filename) {

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //从链接池中取得一个对象
        OSSClient ossClient = pool.getOSSClient();
        PutObjectResult p = ossClient.putObject(bucketName, filename, inputStream);

//        // 设置URL过期时间设置为2000年
//        Date expiration = new Date(new Date().getTime() + 3600 * 1000 * 24 * 365 * 2000);
//        // 生成URL
//        URL url = ossClient.generatePresignedUrl(bucketName, filename, expiration);

        Date expiration = null;
        try {
            expiration = MyDateFormat.parseMill(System.currentTimeMillis() + 3600 * 1000 * 24 * 365 * 20L);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, filename, HttpMethod.GET);
        //设置过期时间
        request.setExpiration(expiration);
        // 生成URL签名(HTTP GET请求)
        URL signedUrl = ossClient.generatePresignedUrl(request);

        // ossclient对象返回链接池中
        pool.addOSSClient(ossClient);

        return signedUrl.toString();

    }

    public String uploadPicture(ByteArrayInputStream inputStream, String filename) {

        OSSClient ossClient = pool.getOSSClient();
        PutObjectResult p = ossClient.putObject(bucketName, filename, inputStream);
        // 设置URL过期时间设置为2000年
        Date expiration = new Date(new Date().getTime() + 3600 * 1000 * 24 * 365 * 20);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, filename, expiration);

        pool.addOSSClient(ossClient);

        return url.toString();
    }

    //删除文件
    public void deletePictureByKey(String filename) {
        OSSClient ossClient = pool.getOSSClient();
        ossClient.deleteObject(bucketName, filename);
        pool.addOSSClient(ossClient);
    }

//    public static void main(String[] args){
//    //    new Upload().deletePictureByKey("nice.png");
//
//        String s = new UploadUtils().uploadPicture(new File("D:/c.png"), "yaoqasdi3.png");
//        System.out.println(s);
//    }

//    public static void main(String[] args){
//        System.out.println((2147483647/3600/1000)+"");
//    }
}
