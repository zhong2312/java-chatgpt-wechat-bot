package org.zhong.chatgpt.wechat.bot.sensitive;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.io.file.FileReader;

/**
 * 简单实现敏感词，主要为了适配一下，可以换其它实现。
 * @author zhong
 *
 */
public class SensitiveWord {

	private static List<String> words = new ArrayList<String>();
	
	static {
        FileReader fileReader = new FileReader("dict.txt");
        words = fileReader.readLines();
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
