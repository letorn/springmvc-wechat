package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpClient {

	public static HttpClientBuilder get(String url) {
		return new HttpClientBuilder(RequestBuilder.get(url));
	}

	public static HttpClientBuilder post(String url) {
		return new HttpClientBuilder(RequestBuilder.post(url));
	}

	public static class HttpClientBuilder {

		private static CloseableHttpClient httpClient = HttpClients.createDefault();
		private static JsonConfig jsonConfig = new JsonConfig();
		private RequestBuilder requestBuilder;

		static {
			jsonConfig.setIgnoreDefaultExcludes(true);
		}

		public HttpClientBuilder(RequestBuilder requestBuilder) {
			this.requestBuilder = requestBuilder;
		}

		public HttpClientBuilder data(String... keyvals) {
			if (keyvals != null && keyvals.length % 2 == 0) {
				for (int i = 0; i < keyvals.length; i += 2) {
					requestBuilder.addParameter(keyvals[i], keyvals[i + 1]);
				}
			}
			return this;
		}

		public HttpClientBuilder content(String content) {
			if (StringUtils.isNotBlank(content)) {
				requestBuilder.setEntity(new StringEntity(content, "utf-8"));
			}
			return this;
		}

		public HttpClientBuilder content(File file) {
			if (file.exists()) {
				requestBuilder.setEntity(new FileEntity(file));
			}
			return this;
		}

		public HttpClientBuilder header(String... keyvals) {
			if (keyvals.length % 2 == 0) {
				for (int i = 0; i < keyvals.length; i += 2) {
					requestBuilder.addHeader(keyvals[i], keyvals[i + 1]);
				}
			}
			return this;
		}

		public String toString() {
			HttpUriRequest request = requestBuilder.build();
			try {
				CloseableHttpResponse response = httpClient.execute(request);
				if (response.getStatusLine().getStatusCode() == 200) {
					HttpEntity responseEntity = response.getEntity();
					if (responseEntity != null) {
						BufferedReader contentReader = new BufferedReader(new InputStreamReader(responseEntity.getContent(), "utf-8"));
						StringBuffer contentBuffer = new StringBuffer();
						String line = null;
						while ((line = contentReader.readLine()) != null) {
							contentBuffer.append(line);
						}
						return contentBuffer.toString();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		public JSONObject toJSONObject() {
			String content = toString();
			if (content != null) {
				return JSONObject.fromObject(content, jsonConfig);
			}
			return null;
		}
	}
}
