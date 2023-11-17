package com.opl.cbdc.config.utils;

import com.opl.cbdc.utils.common.*;
import org.apache.commons.io.*;
import org.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import java.io.*;
import java.nio.file.*;

@Component
public class ReadFileToString {
	
	private static final Logger logger = LoggerFactory.getLogger(OPLUtils.class);
	
	public  String readUsingApacheCommonsIO(String fileName) {
		logger.info("enter in reading file :-- "+fileName);
		try {
			File file = ResourceUtils.getFile("/apps/services/LoadTesting/"+fileName);
			logger.info("File Path :-- "+file.toString());
			fileName = file.toString();
			return FileUtils.readFileToString(new File(fileName));
		} catch (IOException e) {
			logger.warn("error while reading file :-- "+fileName);
			return null;
		}
	}
	
	public void writeFile(String data, String fileName) {
		Path path = Paths.get(fileName);
		byte[] bytes = data.getBytes();
		try {
		    Files.write(path, bytes);
		} catch (IOException ex) {
			// Handle exception
		}
	}
}
