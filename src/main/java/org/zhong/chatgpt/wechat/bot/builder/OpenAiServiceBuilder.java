package org.zhong.chatgpt.wechat.bot.builder;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.X509TrustManager;

import org.zhong.chatgpt.wechat.bot.config.BotConfig;
import org.zhong.chatgpt.wechat.bot.util.SSLSocketClientUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.OpenAiApi;
import com.theokanning.openai.service.OpenAiService;

import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

public class OpenAiServiceBuilder {

	
	public static OpenAiService build(String token, Duration timeout) {

        ObjectMapper mapper = OpenAiService.defaultObjectMapper();
        OkHttpClient client = httpClient(token, timeout);
        Retrofit retrofit = OpenAiService.defaultRetrofit(client, mapper);
        OpenAiApi openAiApi = retrofit.create(OpenAiApi.class);
        
        return new OpenAiService(openAiApi);
	}
	
	private static OkHttpClient httpClient(String token, Duration timeout) {
		
		X509TrustManager manager = SSLSocketClientUtil.getX509TrustManager();
		Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new AuthenticationInterceptor(token))
                .connectionPool(new ConnectionPool(5, 1, TimeUnit.SECONDS))
                .readTimeout(timeout.toMillis(), TimeUnit.MILLISECONDS)
                .sslSocketFactory(SSLSocketClientUtil.getSocketFactory(manager), manager)// 忽略校验
                .hostnameVerifier(SSLSocketClientUtil.getHostnameVerifier());//忽略校验
        
        if(BotConfig.getProxyEnable()) {
        	Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(BotConfig.getProxyHost(), BotConfig.getProxyPort()));
        	builder.proxy(proxy);
        }
        
        OkHttpClient client = builder.build();
        return client;
	}
	
	static class AuthenticationInterceptor implements Interceptor {

	    private final String token;

	    AuthenticationInterceptor(String token) {
	        this.token = token;
	    }

	    @Override
	    public Response intercept(Chain chain) throws IOException {
	        Request request = chain.request()
	                .newBuilder()
	                .header("Authorization", "Bearer " + token)
	                .build();
	        return chain.proceed(request);
	    }
	}
}
