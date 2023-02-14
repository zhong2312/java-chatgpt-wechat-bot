package org.zhong.chatgpt.wechat.bot.chatgptwechatbot.test;

import java.time.Duration;
import java.util.List;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import org.zhong.chatgpt.wechat.bot.config.BotConfig;

import com.theokanning.openai.completion.CompletionRequest;

import cn.zhouyafeng.itchat4j.utils.MyHttpClient;

import com.theokanning.openai.OpenAiService;

public class TestOpenAI {
	
	private static CloseableHttpClient httpClient;

	private static MyHttpClient instance = null;

	private static CookieStore cookieStore;

	static {
		cookieStore = new BasicCookieStore();

		// 将CookieStore设置到httpClient中
		httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		
	}

	public static String getCookie(String name) {
		List<Cookie> cookies = cookieStore.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equalsIgnoreCase(name)) {
				return cookie.getValue();
			}
		}
		return null;

	}
	
	@Test
	public void test() {
		OpenAiService service = new OpenAiService(BotConfig.getAppKey(),"https://api.openai.com/", Duration.ofSeconds(300));
		CompletionRequest completionRequest = CompletionRequest.builder()
		        .prompt("你好")
		        .model("text-davinci-003")
		        .maxTokens(2000)
				.temperature(0.8)
				.topP(1.0)
				.frequencyPenalty(0.55)
				.presencePenalty(0.19)
				.echo(true)
				.user("1234213213")
				.build();
		
		String text = service.createCompletion(completionRequest).getChoices().get(0).getText();
		
		System.out.print(text);
	}
}
