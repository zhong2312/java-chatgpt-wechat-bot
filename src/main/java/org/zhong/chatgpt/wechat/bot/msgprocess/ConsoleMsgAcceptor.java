package org.zhong.chatgpt.wechat.bot.msgprocess;

import java.util.Scanner;

import org.zhong.chatgpt.wechat.bot.model.BotMsg;
import org.zhong.chatgpt.wechat.bot.model.WehchatMsgQueue;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.utils.enums.MsgTypeEnum;

public class ConsoleMsgAcceptor implements MsgAcceptor{

	@Override
	public void start() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入问题：");
		
		BaseMsg msg = new BaseMsg();
		msg.setGroupMsg(false);
		msg.setFromUserName("test");
		msg.setFromUserNickName("test");
		msg.setType(MsgTypeEnum.TEXT.getType());
		for(;scanner.hasNextLine();) {
			String str = scanner.nextLine();
			msg.setContent(str);
			System.out.println();
			WehchatMsgQueue.pushPreMsg(new BotMsg(msg));
		}
		
	}

}
