package util;

import javax.servlet.http.HttpServletRequest;

public class WebContext {

	private static Boolean inited = false;
	private static String scheme;
	private static String serverName;
	private static Integer serverPort;
	private static String contextPath;
	private static String serverHost;
	private static String serverLink;

	public static void init(HttpServletRequest request) {
		if (!inited) {
			scheme = request.getScheme();
			serverName = request.getServerName();
			serverPort = request.getServerPort();
			contextPath = request.getContextPath();
			serverHost = serverPort == 80 ? serverName + contextPath : String.format("%s:%d%s", serverName, serverPort, contextPath);
			serverLink = String.format("%s://%s", scheme, serverHost);
		}
		inited = true;
	}

	public static String getScheme() {
		return scheme;
	}

	public static String getServerName() {
		return serverName;
	}

	public static Integer getServerPort() {
		return serverPort;
	}

	public static String getContextPath() {
		return contextPath;
	}

	public static String getServerHost() {
		return serverHost;
	}

	public static String getServerLink() {
		return serverLink;
	}

}
