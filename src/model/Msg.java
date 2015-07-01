package model;

import java.util.ArrayList;
import java.util.List;

public class Msg {

	private String toUserName;
	private String fromUserName;
	private Long createTime;
	private String msgType;
	private String content;
	private String mediaId;
	private String title;
	private String description;
	private String musicUrl;
	private String hqMusicUrl;
	private String thumbMediaId;
	private List<Article> articles;

	public Msg() {

	}

	public Msg(String toUserName, String fromUserName, Long createTime, String msgType, String content) {
		this.toUserName = toUserName;
		this.fromUserName = fromUserName;
		this.createTime = createTime;
		this.msgType = msgType;
		this.content = content;
	}

	public Msg(String toUserName, String fromUserName, Long createTime, String msgType, Article article) {
		this.toUserName = toUserName;
		this.fromUserName = fromUserName;
		this.createTime = createTime;
		this.msgType = msgType;
		this.articles = new ArrayList<Article>();
		this.articles.add(article);
	}

	public String toXml() {
		if (createTime == null)
			createTime = System.currentTimeMillis() / 1000;
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("<xml>");
		stringBuffer.append(String.format("<ToUserName><![CDATA[%s]]></ToUserName>", toUserName));
		stringBuffer.append(String.format("<FromUserName><![CDATA[%s]]></FromUserName>", fromUserName));
		stringBuffer.append(String.format("<CreateTime>%d</CreateTime>", createTime));
		stringBuffer.append(String.format("<MsgType><![CDATA[%s]]></MsgType>", msgType));
		if ("text".equals(msgType)) {
			if (content == null)
				content = "";
			stringBuffer.append(String.format("<Content><![CDATA[%s]]></Content>", content));
		} else if ("image".endsWith(msgType)) {
			if (mediaId == null)
				mediaId = "";
			stringBuffer.append("<Image>");
			stringBuffer.append(String.format("<MediaId><![CDATA[%s]]></MediaId>", mediaId));
			stringBuffer.append("</Image>");
		} else if ("voice".equals(msgType)) {
			if (mediaId == null)
				mediaId = "";
			stringBuffer.append("<Voice>");
			stringBuffer.append(String.format("<MediaId><![CDATA[%s]]></MediaId>", mediaId));
			stringBuffer.append("</Voice>");
		} else if ("video".equals(msgType)) {
			if (mediaId == null)
				mediaId = "";
			if (title == null)
				title = "";
			if (description == null)
				description = "";
			stringBuffer.append("<Video>");
			stringBuffer.append(String.format("<MediaId><![CDATA[%s]]></MediaId>", mediaId));
			stringBuffer.append(String.format("<Title><![CDATA[%s]]></Title>", title));
			stringBuffer.append(String.format("<Description><![CDATA[%s]]></Description>", description));
			stringBuffer.append("</Video>");
		} else if ("music".equals(msgType)) {
			if (title == null)
				title = "";
			if (description == null)
				description = "";
			if (musicUrl == null)
				musicUrl = "";
			if (hqMusicUrl == null)
				hqMusicUrl = "";
			if (thumbMediaId == null)
				thumbMediaId = "";
			stringBuffer.append("<Music>");
			stringBuffer.append(String.format("<Title><![CDATA[%s]]></Title>", title));
			stringBuffer.append(String.format("<Description><![CDATA[%s]]></Description>", description));
			stringBuffer.append(String.format("<MusicUrl><![CDATA[%s]]></MusicUrl>", musicUrl));
			stringBuffer.append(String.format("<HQMusicUrl><![CDATA[%s]]></HQMusicUrl>", hqMusicUrl));
			stringBuffer.append(String.format("<ThumbMediaId><![CDATA[%s]]></ThumbMediaId>", thumbMediaId));
			stringBuffer.append("</Music>");
		} else if ("news".equals(msgType)) {
			stringBuffer.append(String.format("<ArticleCount>%d</ArticleCount>", articles == null ? 0 : articles.size()));
			stringBuffer.append("<Articles>");
			if (articles != null)
				for (Article article : articles) {
					if (article.getTitle() == null)
						article.setTitle("");
					if (article.getDescription() == null)
						article.setDescription("");
					if (article.getPicUrl() == null)
						article.setPicUrl("");
					if (article.getUrl() == null)
						article.setUrl("");
					stringBuffer.append("<item>");
					stringBuffer.append(String.format("<Title><![CDATA[%s]]></Title>", article.getTitle()));
					stringBuffer.append(String.format("<Description><![CDATA[%s]]></Description>", article.getDescription()));
					stringBuffer.append(String.format("<PicUrl><![CDATA[%s]]></PicUrl>", article.getPicUrl()));
					stringBuffer.append(String.format("<Url><![CDATA[%s]]></Url>", article.getUrl()));
					stringBuffer.append("</item>");
				}
			stringBuffer.append("</Articles>");
		}

		stringBuffer.append("</xml>");
		return stringBuffer.toString();
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMusicUrl() {
		return musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	public String getHqMusicUrl() {
		return hqMusicUrl;
	}

	public void setHqMusicUrl(String hqMusicUrl) {
		this.hqMusicUrl = hqMusicUrl;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public static class Article {

		private String title;
		private String description;
		private String picUrl;
		private String url;

		public Article() {
		}

		public Article(String title, String description, String picUrl, String url) {
			this.title = title;
			this.description = description;
			this.picUrl = picUrl;
			this.url = url;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getPicUrl() {
			return picUrl;
		}

		public void setPicUrl(String picUrl) {
			this.picUrl = picUrl;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

	}

}
