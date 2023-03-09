package org.zhong.chatgpt.wechat.bot.msgprocess;

import org.zhong.chatgpt.wechat.bot.model.AutoConversationStorage;
import org.zhong.chatgpt.wechat.bot.model.BotMsg;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.utils.enums.MsgTypeEnum;

/**
 * 自动对话存储
 * @author zhong
 *
 */
public class ACSSendProcessor implements MsgProcessor{

	@Override
	public void process(BotMsg botMsg) {
		try {
			BaseMsg msg = new BaseMsg();
			msg.setGroupMsg(false);
			msg.setFromUserName("AI");
			msg.setFromUserNickName("AI");
			msg.setType(MsgTypeEnum.TEXT.getType());
			msg.setContent(botMsg.getReplyMsg());
			BotMsg botMsg2 = new BotMsg(msg);
			AutoConversationStorage.getCurrentMsg().blockPush(botMsg2);
			AutoConversationStorage.getHistroyMsg().add(botMsg);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
