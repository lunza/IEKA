package com.ieka.service.impl;

import java.util.HashMap;
import java.util.Map;
//joda控件可以进行时间的操作,用于替代SimpleDateFormat
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ieka.common.utils.FtpUtil;
import com.ieka.common.utils.IDUtils;
import com.ieka.service.PictureService;

@Service
public class PictureServiceImpl implements PictureService {
	// 1.将固定的属性值配置到properties中
	// 2.通过spring容器读取properties文件
	// 3.通过@Value注解读取固定属性注入到对象中
	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;
	@Value("${FTP_PORT}")
	private Integer FTP_PORT;
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;
	@Value("${FTP_BASEPATH}")
	private String FTP_BASEPATH;

	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;

	@Override
	public Map uploadPicture(MultipartFile uploadFile) {
		Map resultMap = new HashMap();
		try {
			// 生成一个新的文件名
			// 取出源文件名
			String oldName = uploadFile.getOriginalFilename();
			// 使用时间+随机数(IDUtil工具类)生成新文件名(也可以使用UUID)
			String newName = IDUtils.genImageName();
			// 添加后缀名
			newName = newName + oldName.substring(oldName.lastIndexOf("."));
			// 图片上传(属性定义在properties文件中)
			String imagePath = new DateTime().toString("/yyyy/MM/dd");
			boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASEPATH,
					imagePath, newName, uploadFile.getInputStream());
			if (!result) {
				resultMap.put("error", 1);
				resultMap.put("message", "文件上传失败");
				return resultMap;
			}
			resultMap.put("error", 0);
			//图片url组成:图片服务器地址+图片地址+图片名称
			resultMap.put("url", IMAGE_BASE_URL + imagePath + "/" + newName);

			return resultMap;
		} catch (Exception e) {
			resultMap.put("error", 1);
			resultMap.put("message", "文件上传失败");
			return resultMap;
		}
	}

}
