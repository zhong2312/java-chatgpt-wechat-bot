package org.zhong.chatgpt.wechat.bot.msgprocess;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.zhong.chatgpt.wechat.bot.builder.OpenAiServiceBuilder;
import org.zhong.chatgpt.wechat.bot.config.BotConfig;
import org.zhong.chatgpt.wechat.bot.model.BotMsg;
import org.zhong.chatgpt.wechat.bot.model.FifoLinkedList;
import org.zhong.chatgpt.wechat.bot.model.WehchatMsgQueue;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;

/**
 * 使用ChatGPT接口进行回复
 * @author zhong
 *
 */
public class ChatGPTReplyProcessor implements MsgProcessor{

	private static OpenAiService service = OpenAiServiceBuilder.build(BotConfig.getAppKey(), Duration.ofSeconds(300));
	/**
	 * 无竞争
	 */
	private static Map<String, FifoLinkedList<ChatMessage>> mgsMap = new TreeMap<>();

	private static ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), "你是一个非常强大、全面的人工智能助手，可以准确地回答我的问题。");
	
	@Override
	public void process(BotMsg botMsg) {
		
		BaseMsg baseMsg = botMsg.getBaseMsg();
		String userName = botMsg.getUserName();
        final FifoLinkedList<ChatMessage> messages = mgsMap.getOrDefault(userName, new FifoLinkedList<ChatMessage>(30));
        messages.add(systemMessage);
        
        ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), baseMsg.getContent());
        messages.add(userMessage);
        
		try {
	        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
	                .builder()
	                .model("gpt-3.5-turbo")
	                .messages(messages)
	                .n(1)
	                .maxTokens(2000)
	                .logitBias(new HashMap<>())
	                .build();

	        List<ChatCompletionChoice> choices = service.createChatCompletion(chatCompletionRequest).getChoices();
	        String text = choices.get(0).getMessage().getContent();
	        
	        ChatMessage assistantMessage = new ChatMessage(ChatMessageRole.ASSISTANT.value(), text);
	        messages.add(assistantMessage);
	        mgsMap.put(userName, messages);
	        
			botMsg.setReplyMsg(text);
			WehchatMsgQueue.pushSendMsg(botMsg);
		}catch (Exception e) {
			e.printStackTrace();
			
			botMsg.setRetries(botMsg.getRetries() + 1);
			if(botMsg.getRetries() < 5) {
				WehchatMsgQueue.pushReplyMsg(botMsg);
			}else {
				String recontent = baseMsg.getContent();
				if(recontent.length() > 20) {
					recontent = recontent.substring(0, 17) + "...\n";
				}
				botMsg.setReplyMsg(recontent+ "该提问已失效，请重新提问");
				WehchatMsgQueue.pushSendMsg(botMsg);
			}
			
		}
		
	}

}
