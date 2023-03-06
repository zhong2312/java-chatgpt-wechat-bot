package org.zhong.chatgpt.wechat.bot.sensitive;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zhong.chatgpt.wechat.bot.config.BotConfig;

import cn.hutool.core.io.file.FileReader;

/**
 * 简单实现敏感词，主要为了适配一下，可以换其它实现。
 * @author zhong
 *
 */
public class SensitiveWord {
	
	private static Logger logger = LoggerFactory.getLogger(SensitiveWord.class);
	
	private static List<String> words = new ArrayList<String>();
	
	static {
		try {
			FileReader fileReader = new FileReader(BotConfig.getDictPath());
	        words = fileReader.readLines();
		} catch (Exception e) {
			logger.warn("读取敏感词配置失败");
		}
	}
	
	/**
	 * 判断文本是否包含敏感词
	 * @param word
	 * @return
	 */
	public static boolean contains(String text) {
		for(String word : words) {
			if(text.contains(word)) {
				return true;
			}
		}
		return false;
	}
}
