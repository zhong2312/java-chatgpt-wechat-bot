package org.zhong.chatgpt.wechat.bot.model;

import java.io.File;

import cn.hutool.core.io.FileUtil;

public class AutoConversationStorage {

	private static BotMsgLinkedList currentMsg = new BotMsgLinkedList();
	
	private static BotMsgLinkedList histroyMsg = new BotMsgLinkedList();

	public static BotMsgLinkedList getCurrentMsg() {
		return currentMsg;
	}

	public static BotMsgLinkedList getHistroyMsg() {
		return histroyMsg;
	}

	static {
		/**
		 * 持久化
		 */
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					for(;;) {
						BotMsg botMsg = histroyMsg.blockPop();
						
						File file = FileUtil.file("D:\\autobot.txt");
						FileUtil.appendUtf8String("ask:"+botMsg.getBaseMsg().getContent()+"\n", file);
						FileUtil.appendUtf8String("reply:"+botMsg.getReplyMsg()+"\n", file);
						FileUtil.appendUtf8String("-------------------------------------------\n", file);
					}
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}).start();
	}

}
