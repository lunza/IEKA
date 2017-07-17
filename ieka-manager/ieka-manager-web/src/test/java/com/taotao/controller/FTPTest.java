package com.taotao.controller;


import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.ieka.common.utils.FtpUtil;

public class FTPTest {
	@Test
	public void testFtpClient() throws Exception{
		//创建一个FtpClient对象
		FTPClient ftpClient = new FTPClient();
		//创建一个FTP连接
		ftpClient.connect("192.168.133.128",21);
		
		//登录ftp服务器,使用用户名,密码
		ftpClient.login("ftpuser", "ftpuser");
		//上传文件包含两个参数:服务器端文档名,上传文件的流
		//读取一个本地文件,创建一个流
		FileInputStream inputStream = new FileInputStream(new File("src/main/webapp/WEB-INF/image/123.jpg"));
		//设置上传路径
		ftpClient.changeWorkingDirectory("/home/ftpuser/www/images");
		//修改上传路径格式
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.storeFile("123.jpg", inputStream);
		//关闭连接
		ftpClient.logout();
	}
	@Test
	public void testFtpUtil() throws Exception{
		FileInputStream inputStream = new FileInputStream(("src/main/webapp/WEB-INF/image/123.jpg"));
		FtpUtil.uploadFile("192.168.133.128", 21, "ftpuser", "ftpuser", "/home/ftpuser/www/images", "/2017/02/23", "123.jpg", inputStream);
	}

}
