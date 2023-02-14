package org.zhong.chatgpt.wechat.bot.chatgptwechatbot.test;

import org.junit.Test;
import org.zhong.chatgpt.wechat.bot.config.BotConfig;

import com.alibaba.fastjson.JSON;

public class TestBotConfig {

	@Test
	public void test() {
		System.out.println(JSON.toJSONString(BotConfig.getGroupWhiteList()));
	}
}
