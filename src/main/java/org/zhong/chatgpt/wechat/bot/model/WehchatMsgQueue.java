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
		try {
			return preMsgs.blockPop();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Thread.currentThread().interrupt();
			return popPreMsg();
		}
	}
	
	public static void pushPreMsg(BotMsg botMsg) {
		try {
			preMsgs.blockPush(botMsg);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
	
	public static BotMsg popReplyMsg() {
		try {
			return waitReplyMsgs.blockPop();
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
			return popReplyMsg();
		}
	}
	
	public static void pushReplyMsg(BotMsg botMsg) {
		try {
			waitReplyMsgs.blockPush(botMsg);
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
	
	public static BotMsg popSendMsg() {
		try {
			return waitSendMsgs.blockPop();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Thread.currentThread().interrupt();
			return popSendMsg();
		}
	}
	
	public static void pushSendMsg(BotMsg botMsg) {
		try {
			waitSendMsgs.blockPush(botMsg);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
	
	public static long countGroupUserPreMsg(String groupUserName) {
		return preMsgs.countGroupUserMsg(groupUserName);
	}
	
	public static long countUserPreMsg(String userName) {
		return preMsgs.countUserMsg(userName);
	}
	
}
