package org.zhong.chatgpt.wechat.bot.msgprocess;

import java.util.Scanner;

import org.zhong.chatgpt.wechat.bot.model.AutoConversationStorage;
import org.zhong.chatgpt.wechat.bot.model.BotMsg;
import org.zhong.chatgpt.wechat.bot.model.WehchatMsgQueue;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.utils.enums.MsgTypeEnum;

public class ACSAcceptor implements MsgAcceptor{

	@Override
	public void start() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("请讨论主题：");
		String str = "";
		if(scanner.hasNextLine()) {
			str = scanner.nextLine();
		}
		
		BaseMsg msg = new BaseMsg();
		msg.setGroupMsg(false);
		msg.setFromUserName("test");
		msg.setFromUserNickName("test");
		msg.setType(MsgTypeEnum.TEXT.getType());
		msg.setContent(str);
		AutoConversationStorage.getCurrentMsg().add(new BotMsg(msg));
		
		
		for(;;) {
			try {
				BotMsg botMsg = AutoConversationStorage.getCurrentMsg().blockPop();
				WehchatMsgQueue.pushPreMsg(botMsg);
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
