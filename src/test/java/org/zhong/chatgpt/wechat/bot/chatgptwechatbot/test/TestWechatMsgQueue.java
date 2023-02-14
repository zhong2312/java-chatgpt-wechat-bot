package org.zhong.chatgpt.wechat.bot.chatgptwechatbot.test;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.zhong.chatgpt.wechat.bot.model.BotMsg;
import org.zhong.chatgpt.wechat.bot.model.WehchatMsgQueue;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;

public class TestWechatMsgQueue {


	@Test
	public void testQueue() {
		BotMsg botMsg = WehchatMsgQueue.popPreMsg();
		Assert.assertNull(botMsg);
		
		WehchatMsgQueue.pushPreMsg(new BotMsg(new BaseMsg()));
		botMsg = WehchatMsgQueue.popPreMsg();
		Assert.assertNotNull(botMsg);
		
	}
}
