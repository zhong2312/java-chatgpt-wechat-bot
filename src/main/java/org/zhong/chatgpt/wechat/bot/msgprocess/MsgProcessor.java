package org.zhong.chatgpt.wechat.bot.msgprocess;

import org.zhong.chatgpt.wechat.bot.model.BotMsg;

public interface MsgProcessor {

	public void process(BotMsg botMsg);
}
