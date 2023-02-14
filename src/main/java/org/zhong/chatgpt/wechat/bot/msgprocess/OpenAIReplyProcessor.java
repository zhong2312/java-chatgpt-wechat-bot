package org.zhong.chatgpt.wechat.bot.msgprocess;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;
import org.zhong.chatgpt.wechat.bot.config.BotConfig;
import org.zhong.chatgpt.wechat.bot.consts.BotConst;
import org.zhong.chatgpt.wechat.bot.model.BotMsg;
import org.zhong.chatgpt.wechat.bot.model.WehchatMsgQueue;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;

public class OpenAIReplyProcessor implements MsgProcessor{

	OpenAiService service = new OpenAiService(BotConfig.getAppKey(), Duration.ofSeconds(300));
	
	private static String model = "text-davinci-003";
	private static Double temperature = 0.9;
	private static Integer maxTokens = 2000;
	private static Double topP = 1d;
	private static Double frequencyPenalty = 0.2;
	private static Double presencePenalty = 0.6;
	private static List<String> stops = new ArrayList<String>();
	
	static {
		
		
		final Yaml yaml = new Yaml();
		Map<String, Object> yamlMap = yaml.load(BotConfig.class.getResourceAsStream("/application.yml"));
		
		String stopsStr = (String) yamlMap.get("bot.openai.completio.stop");
		model = yamlMap.get("bot.openai.completio.model").toString();
		temperature = Double.valueOf( yamlMap.get("bot.openai.completio.temperature").toString());
		maxTokens = Integer.valueOf( yamlMap.get("bot.openai.completio.max_tokens").toString());
		topP = Double.valueOf( yamlMap.get("bot.openai.completio.top_p").toString());
		frequencyPenalty = Double.valueOf( yamlMap.get("bot.openai.completio.frequency_penalty").toString());
		presencePenalty = Double.valueOf( yamlMap.get("bot.openai.completio.presence_penalty").toString());
		if(StringUtils.isNotEmpty(stopsStr)) {
			stops = Arrays.asList(stopsStr.split(","));
		}
	}
	
	@Override
	public void process(BotMsg botMsg) {
		BaseMsg baseMsg = botMsg.getBaseMsg();
		String user = "";
		if(baseMsg.isGroupMsg()) {
			user = baseMsg.getGroupUserName();
		}else {
			user = baseMsg.getFromUserName();
		}
		
		CompletionRequest completionRequest = CompletionRequest.builder()
		        .prompt(baseMsg.getContent())
		        .model(model)
		        .maxTokens(maxTokens)
				.temperature(temperature)
				.topP(topP)
				.frequencyPenalty(frequencyPenalty)
				.presencePenalty(presencePenalty)
				.echo(true)
				.user(user)
				.build();
		
		try {
	
			String text = service.createCompletion(completionRequest).getChoices().get(0).getText();
			if(baseMsg.isGroupMsg()) {
				botMsg.setReplyMsg(BotConst.AT + baseMsg.getGroupUserNickName()+ " " + text);
			}else {
				botMsg.setReplyMsg(text);
			}
			
			WehchatMsgQueue.pushSendMsg(botMsg);
		}catch (Exception e) {
			e.printStackTrace();
			WehchatMsgQueue.pushReplyMsg(botMsg);
		}
		
	}

}