package org.zhong.chatgpt.wechat.bot.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;
import org.zhong.chatgpt.wechat.bot.consts.BotConst;

import cn.hutool.core.io.file.FileReader;

public class BotConfig {

	private static String botName = "狂追风龙少";
	
	private static String atBotName = BotConst.AT+botName;

	private static String appKey = "";
	
	private static String qrcodePath = "";
	
	private static String proxyHost = "";
	
	private static int proxyPort = 0;
	
	private static Boolean proxyEnable = false;
	
	private static String dictPath = "classpath:dict.txt";
	
	private static List<String> groupWhiteList = new ArrayList<String>(); 
	
	private static List<String> userWhiteList = new ArrayList<String>(); 
	
	static {
		String rootConfigPath = System.getProperty("rootConfigPath");
		String groupWhiteListPath = "classpath:groupWhiteList.txt";
		String userWhiteListPath = "classpath:userWhiteList.txt";
		InputStream applicationStream = BotConfig.class.getResourceAsStream("/application.yml");
		
		if(StringUtils.isNotEmpty(rootConfigPath)) {
			groupWhiteListPath = rootConfigPath + "/groupWhiteList.txt";
			userWhiteListPath = rootConfigPath + "/userWhiteList.txt";
			dictPath = rootConfigPath + "/dict.txt";
			try {
				applicationStream = new FileInputStream(new File(rootConfigPath + "/application.yml"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		FileReader groupFileReader = new FileReader(groupWhiteListPath);
		groupWhiteList = groupFileReader.readLines();
		
		FileReader userFileReader = new FileReader(userWhiteListPath);
		userWhiteList = userFileReader.readLines();
		
		final Yaml yaml = new Yaml();
		Map<String, Object> yamlMap = yaml.load(applicationStream);

		botName = yamlMap.get("bot.botName").toString();
		qrcodePath = yamlMap.get("bot.wechat.qrcode.path").toString();
		Object objAppKey = yamlMap.get("bot.appKey");
		
		Object enable = yamlMap.get("proxy.enable");
		if(enable != null) {
			proxyEnable = Boolean.valueOf(enable.toString());
		}
		
		if(proxyEnable) {
			proxyHost = yamlMap.get("proxy.host").toString();
			proxyPort = Integer.valueOf(yamlMap.get("proxy.port").toString());
		}
		
		if(objAppKey == null ) {
			appKey = System.getProperty("bot.appKey");
		}else {
			appKey = objAppKey.toString();
		}
	}

	public static String getBotName() {
		return botName;
	}

	public static void setBotName(String botName) {
		BotConfig.botName = botName;
	}

	public static List<String> getGroupWhiteList() {
		return groupWhiteList;
	}

	public static void setGroupWhiteList(List<String> groupWhiteList) {
		BotConfig.groupWhiteList = groupWhiteList;
	}

	public static String getAtBotName() {
		return atBotName;
	}

	public static void setAtBotName(String atBotName) {
		BotConfig.atBotName = atBotName;
	}

	public static String getAppKey() {
		return appKey;
	}

	public static void setAppKey(String appKey) {
		BotConfig.appKey = appKey;
	}

	public static List<String> getUserWhiteList() {
		return userWhiteList;
	}

	public static void setUserWhiteList(List<String> userWhiteList) {
		BotConfig.userWhiteList = userWhiteList;
	}

	public static String getQrcodePath() {
		return qrcodePath;
	}

	public static void setQrcodePath(String qrcodePath) {
		BotConfig.qrcodePath = qrcodePath;
	}

	public static String getProxyHost() {
		return proxyHost;
	}

	public static void setProxyHost(String proxyHost) {
		BotConfig.proxyHost = proxyHost;
	}

	public static int getProxyPort() {
		return proxyPort;
	}

	public static void setProxyPort(int proxyPost) {
		BotConfig.proxyPort = proxyPost;
	}

	public static Boolean getProxyEnable() {
		return proxyEnable;
	}

	public static void setProxyEnable(Boolean proxyEnable) {
		BotConfig.proxyEnable = proxyEnable;
	}

	public static String getDictPath() {
		return dictPath;
	}

	public static void setDictPath(String dictPath) {
		BotConfig.dictPath = dictPath;
	}
	
	


	
	
	
	
	
	
	
}
