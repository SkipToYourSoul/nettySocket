package utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by betty.bao on 2017/7/28.
 */
public class RandomStrGenarator {

    /**
     * 服务器端本地化存储文件时产生的随机的文件名
     * 命名：当前年月日-4个随机字符-4个随机字符-8个随机字符
     * example: 20170728-dXSy-fjHV-AbT1n4nY
     * @return
     */
    public static String createRandomFileName(){

        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        sb.append(simpleDateFormat.format(new Date()));
        sb.append("-");
        sb.append(RandomStringUtils.randomAlphanumeric(4));
        sb.append("-");
        sb.append(RandomStringUtils.randomAlphanumeric(4));
        sb.append("-");
        sb.append(RandomStringUtils.randomAlphanumeric(8));
        return sb.toString();
    }

    public static void main(String[] args) {
        createRandomFileName();
    }
}
