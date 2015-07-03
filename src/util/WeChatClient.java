package util;

import java.io.File;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Chatter;
import model.Follower;
import model.Oauth;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;

public class WeChatClient {

	private static JsonConfig jsonConfig = new JsonConfig();

	private static String appId;
	private static String appSecret;
	private static String token;

	private static String accessToken = null;
	private static String ticket = null;

	/**
	 * 验证URL有效性
	 * @param signature 微信加密签名
	 * @param timestamp 时间戳
	 * @param nonce 随机数
	 * @return
	 */
	public static Boolean validate(String signature, Long timestamp, String nonce) {
		try {
			String[] arr = { token, String.valueOf(timestamp), nonce };
			Arrays.sort(arr);
			return signature.equals(toHex(MessageDigest.getInstance("SHA-1").digest((arr[0] + arr[1] + arr[2]).getBytes("utf-8"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 初始化accessToken
	 * @return
	 */
	public static Boolean initAccessToken() {
		JSONObject responseJSONObject = HttpClient.get("https://api.weixin.qq.com/cgi-bin/token").data("appid", appId, "secret", appSecret, "grant_type", "client_credential").toJSONObject();
		if (responseJSONObject != null && responseJSONObject.has("access_token")) {
			accessToken = responseJSONObject.getString("access_token");
			return true;
		}
		return false;
	}

	/**
	 * 初始化ticket
	 * @return
	 */
	public static Boolean initTicket() {
		JSONObject responseJSONObject = HttpClient.get("https://api.weixin.qq.com/cgi-bin/ticket/getticket").data("access_token", accessToken, "type", "jsapi").toJSONObject();
		if (responseJSONObject != null && responseJSONObject.has("ticket")) {
			ticket = responseJSONObject.getString("ticket");
			return true;
		}
		return false;
	}

	/**
	 * 初始化菜单
	 * @return
	 */
	public static Boolean initMenu() {
		File menuFile = new File(System.getProperty("webapp.root"), "public/wechat.menu");
		JSONObject responseJSONObject = HttpClient.post("https://api.weixin.qq.com/cgi-bin/menu/create").data("access_token", accessToken).content(menuFile).toJSONObject();
		if (responseJSONObject != null && responseJSONObject.has("errcode"))
			return responseJSONObject.getInt("errcode") == 0;
		return false;
	}

	/**
	 * 初始化菜单
	 * @param menu
	 * @return
	 */
	public static Boolean initMenu(Map<String, Object> menu) {
		JSONObject menuJSONObject = JSONObject.fromObject(menu, jsonConfig);
		JSONObject responseJSONObject = HttpClient.post("https://api.weixin.qq.com/cgi-bin/menu/create").data("access_token", accessToken).content(menuJSONObject.toString()).toJSONObject();
		if (responseJSONObject != null && responseJSONObject.has("errcode"))
			return responseJSONObject.getInt("errcode") == 0;
		return false;
	}

	public static Oauth getOauth(String code) {
		JSONObject responseJSONObject = HttpClient.post("https://api.weixin.qq.com/sns/oauth2/access_token").data("appid", appId, "secret", appSecret, "code", code, "grant_type", "authorization_code").toJSONObject();
		if (responseJSONObject != null && responseJSONObject.has("access_token")) {
			Oauth oauth = new Oauth();
			oauth.setAccessToken(responseJSONObject.getString("access_token"));
			oauth.setExpiresIn(responseJSONObject.getInt("expires_in"));
			oauth.setRefreshToken(responseJSONObject.getString("refresh_token"));
			oauth.setOpenId(responseJSONObject.getString("openid"));
			oauth.setScopes(responseJSONObject.getString("scope"));
			if (responseJSONObject.has("unionid"))
				oauth.setUnionId(responseJSONObject.getString("unionid"));
			return oauth;
		}
		return null;
	}

	public static Oauth refreshOauth(String refreshToken) {
		JSONObject responseJSONObject = HttpClient.post("https://api.weixin.qq.com/sns/oauth2/refresh_token").data("appid", appId, "refresh_token", refreshToken, "grant_type", "refresh_token").toJSONObject();
		if (responseJSONObject != null && responseJSONObject.has("access_token")) {
			Oauth oauth = new Oauth();
			oauth.setAccessToken(responseJSONObject.getString("access_token"));
			oauth.setExpiresIn(responseJSONObject.getInt("expires_in"));
			oauth.setRefreshToken(responseJSONObject.getString("refresh_token"));
			oauth.setOpenId(responseJSONObject.getString("openid"));
			oauth.setScopes(responseJSONObject.getString("scope"));
			return oauth;
		}
		return null;
	}

	public static Oauth refreshOauth(Oauth oauth) {
		return refreshOauth(oauth.getAccessToken());
	}

	public static Chatter getChatter(String openId) {
		JSONObject responseJSONObject = HttpClient.post("https://api.weixin.qq.com/cgi-bin/user/info").data("openid", openId, "access_token", accessToken, "lang", "zh_CN").toJSONObject();
		if (responseJSONObject != null && responseJSONObject.has("subscribe")) {
			Chatter chatter = new Chatter();
			chatter.setSubscribe(responseJSONObject.getInt("subscribe"));
			if (chatter.getSubscribe() == 1) {
				chatter.setOpenId(responseJSONObject.getString("openid"));
				chatter.setNickname(responseJSONObject.getString("nickname"));
				chatter.setSex(responseJSONObject.getInt("sex"));
				chatter.setLanguage(responseJSONObject.getString("language"));
				chatter.setCity(responseJSONObject.getString("city"));
				chatter.setProvince(responseJSONObject.getString("province"));
				chatter.setCountry(responseJSONObject.getString("country"));
				chatter.setHeadImgUrl(responseJSONObject.getString("headimgurl"));
				chatter.setSubscribeTime(responseJSONObject.getLong("subscribe_time"));
				if (responseJSONObject.has("unionid"))
					chatter.setUnionId(responseJSONObject.getString("unionid"));
				chatter.setRemark(responseJSONObject.getString("remark"));
				chatter.setGroupId(responseJSONObject.getInt("groupid"));
			}
			return chatter;
		}
		return null;
	}

	public static Chatter getChatter(String openId, String accessToken) {
		JSONObject responseJSONObject = HttpClient.post("https://api.weixin.qq.com/sns/userinfo").data("openid", openId, "access_token", accessToken, "lang", "zh_CN").toJSONObject();
		if (responseJSONObject != null && responseJSONObject.has("nickname")) {
			Chatter chatter = new Chatter();
			chatter.setOpenId(responseJSONObject.getString("openid"));
			chatter.setNickname(responseJSONObject.getString("nickname"));
			chatter.setSex(responseJSONObject.getInt("sex"));
			chatter.setCity(responseJSONObject.getString("city"));
			chatter.setProvince(responseJSONObject.getString("province"));
			chatter.setCountry(responseJSONObject.getString("country"));
			chatter.setHeadImgUrl(responseJSONObject.getString("headimgurl"));
			chatter.setPrivileges(StringUtils.join(responseJSONObject.getJSONArray("privilege").toArray(), ","));
			if (responseJSONObject.has("unionid"))
				chatter.setUnionId(responseJSONObject.getString("unionid"));
			return chatter;
		}
		return null;
	}

	public static Chatter getChatter(Oauth oauth) {
		Chatter chatter = getChatter(oauth.getOpenId(), oauth.getAccessToken());
		if (chatter != null) {
			chatter.setAccessToken(oauth.getAccessToken());
			chatter.setRefreshToken(oauth.getRefreshToken());
			chatter.setScopes(oauth.getScopes());
			chatter.setExpiresIn(oauth.getExpiresIn());
		}
		return chatter;
	}

	public static Follower getFollower(String nextOpenId) {
		JSONObject responseJSONObject = null;
		if (nextOpenId == null)
			responseJSONObject = HttpClient.post("https://api.weixin.qq.com/cgi-bin/user/get").data("access_token", accessToken).toJSONObject();
		else
			responseJSONObject = HttpClient.post("https://api.weixin.qq.com/cgi-bin/user/get").data("access_token", accessToken, "next_openid", nextOpenId).toJSONObject();
		if (responseJSONObject != null && responseJSONObject.has("data")) {
			Follower follower = new Follower();
			follower.setTotal(responseJSONObject.getInt("total"));
			follower.setCount(responseJSONObject.getInt("count"));
			follower.setOpenIds((String[]) responseJSONObject.getJSONObject("data").getJSONArray("openid").toArray());
			follower.setNextOptionId(responseJSONObject.getString("next_openid"));
			return follower;
		}
		return null;
	}

	public static String getOAuthUrl(String redirectRri, boolean authorize, String state) {
		String scope = authorize ? "snsapi_userinfo" : "snsapi_base";
		if (state == null)
			state = "";
		try {
			redirectRri = URLEncoder.encode(redirectRri, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect", appId, redirectRri, scope, state);
	}

	public static void on(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		String params = request.getQueryString();
		if (params != null)
			url = String.format("%s?%s", url, params);
		String nonce = createNonce();
		Long timestamp = createTimestamp();
		String signature = createSignature(nonce, timestamp, url);
		request.setAttribute("appId", appId);
		request.setAttribute("nonce", nonce);
		request.setAttribute("timestamp", timestamp);
		request.setAttribute("signature", signature);

		HttpSession session = request.getSession();
		session.setAttribute("appId", appId);
		session.setAttribute("nonce", nonce);
		session.setAttribute("timestamp", timestamp);
		session.setAttribute("signature", signature);
	}

	public static String getAppId() {
		return appId;
	}

	public static void setAppId(String appId) {
		WeChatClient.appId = appId;
	}

	public static String getAppSecret() {
		return appSecret;
	}

	public static void setAppSecret(String appSecret) {
		WeChatClient.appSecret = appSecret;
	}

	public static String getToken() {
		return token;
	}

	public static void setToken(String token) {
		WeChatClient.token = token;
	}

	public static String getAccessToken() {
		return accessToken;
	}

	public static String getTicket() {
		return ticket;
	}

	public static String createNonce() {
		return UUID.randomUUID().toString();
	}

	public static Long createTimestamp() {
		return System.currentTimeMillis() / 1000;
	}

	public static String createSignature(String nonce, Long timestamp, String url) {
		String str = String.format("jsapi_ticket=%s&noncestr=%s&timestamp=%d&url=%s", ticket, nonce, timestamp, url);
		try {
			return toHex(MessageDigest.getInstance("SHA-1").digest(str.getBytes("utf-8")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String toHex(byte[] bytes) {
		Formatter formatter = new Formatter();
		for (byte b : bytes) {
			formatter.format("%02x", b);
		}
		return formatter.toString();
	}

}
