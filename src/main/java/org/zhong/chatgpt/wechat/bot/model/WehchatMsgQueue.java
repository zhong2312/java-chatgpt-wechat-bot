package org.zhong.chatgpt.wechat.bot.model;

public class WehchatMsgQueue {

	/**
	 * 等待预处理
	 */
	private static BotMsgLinkedList preMsgs = new BotMsgLinkedList();
	
	/**
	 * 等待回复
	 */
	private static BotMsgLinkedList waitReplyMsgs = new BotMsgLinkedList();
	
	/**
	 * 等待发送
	 */
	private static BotMsgLinkedList waitSendMsgs = new BotMsgLinkedList();
	
	public static BotMsg popPreMsg() {
		return preMsgs.nullPop();
	}
	
	public static void pushPreMsg(BotMsg botMsg) {
		preMsgs.push(botMsg);
	}
	
	public static long countGroupUserPreMsg(String groupUserName) {
		return preMsgs.countGroupUserMsg(groupUserName);
	}
	
	public static long countUserPreMsg(String userName) {
		return preMsgs.countUserMsg(userName);
	}
	public static BotMsg popReplyMsg() {
		return waitReplyMsgs.nullPop();
	}
	
	public static void pushReplyMsg(BotMsg botMsg) {
		waitReplyMsgs.push(botMsg);
	}
	
	public static BotMsg popSendMsg() {
		return waitSendMsgs.nullPop();
	}
	
	public static void pushSendMsg(BotMsg botMsg) {
		waitSendMsgs.push(botMsg);
	}
	
}
