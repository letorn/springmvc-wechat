package controller;

import init.WebInterceptor;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;

import model.Msg;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import util.WeChatClient;
import wchandler.ClickEventHandler;
import wchandler.ImageHandler;
import wchandler.LinkHandler;
import wchandler.LocationEventHandler;
import wchandler.LocationHandler;
import wchandler.LocationSelectEventHandler;
import wchandler.PicPhotoOrAlbumEventHandler;
import wchandler.PicSysPhotoEventHandler;
import wchandler.PicWeiXinEventHandler;
import wchandler.ScanCodePushEventHandler;
import wchandler.ScanCodeWaitMsgEventHandler;
import wchandler.ScanEventHandler;
import wchandler.ShortVideoHandler;
import wchandler.SubscribeEventHandler;
import wchandler.TextHandler;
import wchandler.UnsubscribeEventHandler;
import wchandler.VideoHandler;
import wchandler.ViewEventHandler;
import wchandler.VoiceHandler;

@Controller
public class ServiceController {

	private static Logger logger = Logger.getLogger(ServiceController.class);
	
	@Resource
	public TextHandler textHandler;
	@Resource
	public ImageHandler imageHandler;
	@Resource
	public VoiceHandler voiceHandler;
	@Resource
	public VideoHandler videoHandler;
	@Resource
	public ShortVideoHandler shortVideoHandler;
	@Resource
	public LocationHandler locationHandler;
	@Resource
	public LinkHandler linkHandler;
	@Resource
	public SubscribeEventHandler subscribeEventHandler;
	@Resource
	public UnsubscribeEventHandler unsubscribeEventHandler;
	@Resource
	public ScanEventHandler scanEventHandler;
	@Resource
	public LocationEventHandler locationEventHandler;
	@Resource
	public ClickEventHandler clickEventHandler;
	@Resource
	public ViewEventHandler viewEventHandler;
	@Resource
	public ScanCodePushEventHandler scanCodePushEventHandler;
	@Resource
	public ScanCodeWaitMsgEventHandler scanCodeWaitMsgEventHandler;
	@Resource
	public PicSysPhotoEventHandler picSysPhotoEventHandler;
	@Resource
	public PicPhotoOrAlbumEventHandler picPhotoOrAlbumEventHandler;
	@Resource
	public PicWeiXinEventHandler picWeiXinEventHandler;
	@Resource
	public LocationSelectEventHandler locationSelectEventHandler;

	@RequestMapping(value = "service.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public void validate(PrintWriter out, String signature, Long timestamp, String nonce, String echostr) {
		if (StringUtils.isNotBlank(signature) && timestamp != null && StringUtils.isNotBlank(nonce) && StringUtils.isNotBlank(echostr)) {
			if (WeChatClient.validate(signature, timestamp, nonce)) {
				out.write(echostr);
			}
		}
	}

	@RequestMapping(value = "service.do", method = RequestMethod.POST, produces = "application/xml;charset=UTF-8")
	public void service(PrintWriter out, @RequestBody String xml) {
		if (StringUtils.isNotBlank(xml)) {
			logger.info(xml);
			try {
				Document document = DocumentHelper.parseText(xml);
				Element xmlElement = document.getRootElement();
				String toUserName = xmlElement.elementTextTrim("ToUserName");
				String fromUserName = xmlElement.elementTextTrim("FromUserName");
				String createTimeString = xmlElement.elementTextTrim("CreateTime");
				Long createTime = Long.parseLong(createTimeString);
				String msgType = xmlElement.elementTextTrim("MsgType");
				Msg msg = null;
				if ("text".equals(msgType)) {
					Class clazz = textHandler.getClass();
					String content = xmlElement.elementTextTrim("Content");
					String msgId = xmlElement.elementTextTrim("MsgId");
					Method method = ReflectionUtils.findMethod(clazz, content, String.class, String.class, Long.class, String.class, String.class, String.class);
					if (method != null) {
						msg = (Msg) ReflectionUtils.invokeMethod(method, textHandler, toUserName, fromUserName, createTime, msgType, content, msgId);
					} else {
						method = ReflectionUtils.findMethod(clazz, "service", String.class, String.class, Long.class, String.class, String.class, String.class);
						msg = (Msg) ReflectionUtils.invokeMethod(method, textHandler, toUserName, fromUserName, createTime, msgType, content, msgId);
					}
				} else if ("image".equals(msgType)) {
					Class clazz = imageHandler.getClass();
					String picUrl = xmlElement.elementTextTrim("PicUrl");
					String mediaId = xmlElement.elementTextTrim("MediaId");
					String msgId = xmlElement.elementTextTrim("MsgId");
					Method method = ReflectionUtils.findMethod(clazz, "service", String.class, String.class, Long.class, String.class, String.class, String.class, String.class);
					msg = (Msg) ReflectionUtils.invokeMethod(method, imageHandler, toUserName, fromUserName, createTime, msgType, picUrl, mediaId, msgId);
				} else if ("voice".equals(msgType)) {
					Class clazz = voiceHandler.getClass();
					String mediaId = xmlElement.elementTextTrim("MediaId");
					String format = xmlElement.elementTextTrim("Format");
					String msgId = xmlElement.elementTextTrim("MsgId");
					Method method = ReflectionUtils.findMethod(clazz, "service", String.class, String.class, Long.class, String.class, String.class, String.class, String.class);
					msg = (Msg) ReflectionUtils.invokeMethod(method, voiceHandler, toUserName, fromUserName, createTime, msgType, mediaId, format, msgId);
				} else if ("video".equals(msgType)) {
					Class clazz = videoHandler.getClass();
					String mediaId = xmlElement.elementTextTrim("MediaId");
					String thumbMediaId = xmlElement.elementTextTrim("ThumbMediaId");
					String msgId = xmlElement.elementTextTrim("MsgId");
					Method method = ReflectionUtils.findMethod(clazz, "service", String.class, String.class, Long.class, String.class, String.class, String.class, String.class);
					msg = (Msg) ReflectionUtils.invokeMethod(method, videoHandler, toUserName, fromUserName, createTime, msgType, mediaId, thumbMediaId, msgId);
				} else if ("shortvideo".equals(msgType)) {
					Class clazz = shortVideoHandler.getClass();
					String mediaId = xmlElement.elementTextTrim("MediaId");
					String thumbMediaId = xmlElement.elementTextTrim("ThumbMediaId");
					String msgId = xmlElement.elementTextTrim("MsgId");
					Method method = ReflectionUtils.findMethod(clazz, "service", String.class, String.class, Long.class, String.class, String.class, String.class, String.class);
					msg = (Msg) ReflectionUtils.invokeMethod(method, shortVideoHandler, toUserName, fromUserName, createTime, msgType, mediaId, thumbMediaId, msgId);
				} else if ("location".equals(msgType)) {
					Class clazz = locationHandler.getClass();
					Double locationX = Double.parseDouble(xmlElement.elementTextTrim("Location_X"));
					Double locationY = Double.parseDouble(xmlElement.elementTextTrim("Location_Y"));
					Integer scale = Integer.parseInt(xmlElement.elementTextTrim("Scale"));
					String label = xmlElement.elementTextTrim("Label");
					String msgId = xmlElement.elementTextTrim("MsgId");
					Method method = ReflectionUtils.findMethod(clazz, "service", String.class, String.class, Long.class, String.class, Double.class, Double.class, Integer.class, String.class, String.class);
					msg = (Msg) ReflectionUtils.invokeMethod(method, locationHandler, toUserName, fromUserName, createTime, msgType, locationX, locationY, scale, label, msgId);
				} else if ("link".equals(msgType)) {
					Class clazz = linkHandler.getClass();
					String title = xmlElement.elementTextTrim("Title");
					String description = xmlElement.elementTextTrim("Description");
					String url = xmlElement.elementTextTrim("Url");
					String msgId = xmlElement.elementTextTrim("MsgId");
					Method method = ReflectionUtils.findMethod(clazz, "service", String.class, String.class, Long.class, String.class, String.class, String.class, String.class, String.class);
					msg = (Msg) ReflectionUtils.invokeMethod(method, linkHandler, toUserName, fromUserName, createTime, msgType, title, description, url, msgId);
				} else if ("event".endsWith(msgType)) {
					String event = xmlElement.elementTextTrim("Event");
					String eventKey = xmlElement.elementTextTrim("EventKey");
					if ("subscribe".equals(event)) {
						Class clazz = subscribeEventHandler.getClass();
						if (eventKey == null) {
							Method method = ReflectionUtils.findMethod(clazz, "subscribe", String.class, String.class, Long.class, String.class, String.class);
							msg = (Msg) ReflectionUtils.invokeMethod(method, subscribeEventHandler, toUserName, fromUserName, createTime, msgType, event);
						} else if (eventKey.startsWith("qrscene_")) {
							String ticket = xmlElement.elementTextTrim("Ticket");
							Method method = ReflectionUtils.findMethod(clazz, "qrscene", String.class, String.class, Long.class, String.class, String.class, String.class, String.class);
							msg = (Msg) ReflectionUtils.invokeMethod(method, subscribeEventHandler, toUserName, fromUserName, createTime, msgType, event, eventKey, ticket);
						} else {
							Method method = ReflectionUtils.findMethod(clazz, "service", String.class, String.class, Long.class, String.class, String.class, String.class);
							msg = (Msg) ReflectionUtils.invokeMethod(method, subscribeEventHandler, toUserName, fromUserName, createTime, msgType, event, eventKey);
						}
					} else if ("unsubscribe".equals(event)) {
						Class clazz = unsubscribeEventHandler.getClass();
						if (eventKey == null) {
							Method method = ReflectionUtils.findMethod(clazz, "unsubscribe", String.class, String.class, Long.class, String.class, String.class);
							msg = (Msg) ReflectionUtils.invokeMethod(method, unsubscribeEventHandler, toUserName, fromUserName, createTime, msgType, event);
						} else {
							Method method = ReflectionUtils.findMethod(clazz, "service", String.class, String.class, Long.class, String.class, String.class, String.class);
							msg = (Msg) ReflectionUtils.invokeMethod(method, unsubscribeEventHandler, toUserName, fromUserName, createTime, msgType, event, eventKey);
						}
					} else if ("SCAN".equals(event)) {
						Class clazz = scanEventHandler.getClass();
						String ticket = xmlElement.elementTextTrim("Ticket");
						Method method = ReflectionUtils.findMethod(clazz, eventKey, String.class, String.class, Long.class, String.class, String.class, String.class, String.class);
						if (method != null) {
							msg = (Msg) ReflectionUtils.invokeMethod(method, scanEventHandler, toUserName, fromUserName, createTime, msgType, event, eventKey, ticket);
						} else {
							method = ReflectionUtils.findMethod(clazz, "service", String.class, String.class, Long.class, String.class, String.class, String.class, String.class);
							msg = (Msg) ReflectionUtils.invokeMethod(method, scanEventHandler, toUserName, fromUserName, createTime, msgType, event, eventKey, ticket);
						}
					} else if ("LOCATION".equals(event)) {
						Class clazz = locationEventHandler.getClass();
						Double latitude = Double.parseDouble(xmlElement.elementTextTrim("Latitude"));
						Double longitude = Double.parseDouble(xmlElement.elementTextTrim("Longitude"));
						Double precision = Double.parseDouble(xmlElement.elementTextTrim("Precision"));
						Method method = ReflectionUtils.findMethod(clazz, "service", String.class, String.class, Long.class, String.class, String.class, Double.class, Double.class, Double.class);
						msg = (Msg) ReflectionUtils.invokeMethod(method, locationEventHandler, toUserName, fromUserName, createTime, msgType, event, latitude, longitude, precision);
					} else if ("CLICK".equals(event)) {
						Class clazz = clickEventHandler.getClass();
						Method method = ReflectionUtils.findMethod(clazz, eventKey, String.class, String.class, Long.class, String.class, String.class, String.class);
						if (method != null) {
							msg = (Msg) ReflectionUtils.invokeMethod(method, clickEventHandler, toUserName, fromUserName, createTime, msgType, event, eventKey);
						} else {
							method = ReflectionUtils.findMethod(clazz, "service", String.class, String.class, Long.class, String.class, String.class, String.class);
							msg = (Msg) ReflectionUtils.invokeMethod(method, clickEventHandler, toUserName, fromUserName, createTime, msgType, event, eventKey);
						}
					} else if ("VIEW".equals(event)) {
						Class clazz = viewEventHandler.getClass();
						Method method = ReflectionUtils.findMethod(clazz, eventKey, String.class, String.class, Long.class, String.class, String.class, String.class);
						if (method != null) {
							msg = (Msg) ReflectionUtils.invokeMethod(method, viewEventHandler, toUserName, fromUserName, createTime, msgType, event, eventKey);
						} else {
							method = ReflectionUtils.findMethod(clazz, "service", String.class, String.class, Long.class, String.class, String.class, String.class);
							msg = (Msg) ReflectionUtils.invokeMethod(method, viewEventHandler, toUserName, fromUserName, createTime, msgType, event, eventKey);
						}
					} else if ("scancode_push".equals(event)) {
						Class clazz = scanCodePushEventHandler.getClass();
						Element scanCodeInfoElement = xmlElement.element("ScanCodeInfo");
						String scanType = scanCodeInfoElement.elementTextTrim("ScanType");
						String scanResult = scanCodeInfoElement.elementTextTrim("ScanResult");
						Method method = ReflectionUtils.findMethod(clazz, eventKey, String.class, String.class, Long.class, String.class, String.class, String.class, String.class, String.class);
						if (method != null) {
							msg = (Msg) ReflectionUtils.invokeMethod(method, scanCodePushEventHandler, toUserName, fromUserName, createTime, msgType, event, eventKey, scanType, scanResult);
						} else {
							method = ReflectionUtils.findMethod(clazz, "service", String.class, String.class, Long.class, String.class, String.class, String.class, String.class, String.class);
							msg = (Msg) ReflectionUtils.invokeMethod(method, scanCodePushEventHandler, toUserName, fromUserName, createTime, msgType, event, eventKey, scanType, scanResult);
						}
					} else if ("scancode_waitmsg".equals(event)) {
						Class clazz = scanCodeWaitMsgEventHandler.getClass();
						Element scanCodeInfoElement = xmlElement.element("ScanCodeInfo");
						String scanType = scanCodeInfoElement.elementTextTrim("ScanType");
						String scanResult = scanCodeInfoElement.elementTextTrim("ScanResult");
						Method method = ReflectionUtils.findMethod(clazz, eventKey, String.class, String.class, Long.class, String.class, String.class, String.class, String.class, String.class);
						if (method != null) {
							msg = (Msg) ReflectionUtils.invokeMethod(method, scanCodeWaitMsgEventHandler, toUserName, fromUserName, createTime, msgType, event, eventKey, scanType, scanResult);
						} else {
							method = ReflectionUtils.findMethod(clazz, "service", String.class, String.class, Long.class, String.class, String.class, String.class, String.class, String.class);
							msg = (Msg) ReflectionUtils.invokeMethod(method, scanCodeWaitMsgEventHandler, toUserName, fromUserName, createTime, msgType, event, eventKey, scanType, scanResult);
						}
					} else if ("pic_sysphoto".equals(event)) {
						Class clazz = picSysPhotoEventHandler.getClass();
						Element sendPicsInfoElement = xmlElement.element("SendPicsInfo");
						String countString = sendPicsInfoElement.elementTextTrim("Count");
						Integer count = Integer.valueOf(countString);
						Element picListElement = sendPicsInfoElement.element("PicList");
						List<Element> items = picListElement.elements("item");
						String[] picMd5Sums = new String[items.size()];
						for (int i = 0; i < picMd5Sums.length; i++) {
							picMd5Sums[i] = items.get(i).elementTextTrim("PicMd5Sum");
						}
						Method method = ReflectionUtils.findMethod(clazz, eventKey, String.class, String.class, Long.class, String.class, String.class, String.class, Integer.class, String[].class);
						if (method != null) {
							msg = (Msg) ReflectionUtils.invokeMethod(method, picSysPhotoEventHandler, toUserName, fromUserName, createTime, msgType, event, eventKey, count, picMd5Sums);
						} else {
							method = ReflectionUtils.findMethod(clazz, "service", String.class, String.class, Long.class, String.class, String.class, String.class, Integer.class, String[].class);
							msg = (Msg) ReflectionUtils.invokeMethod(method, picSysPhotoEventHandler, toUserName, fromUserName, createTime, msgType, event, eventKey, count, picMd5Sums);
						}
					} else if ("pic_photo_or_album".equals(event)) {
						Class clazz = picPhotoOrAlbumEventHandler.getClass();
						Element sendPicsInfoElement = xmlElement.element("SendPicsInfo");
						String countString = sendPicsInfoElement.elementTextTrim("Count");
						Integer count = Integer.valueOf(countString);
						Element picListElement = sendPicsInfoElement.element("PicList");
						List<Element> items = picListElement.elements("item");
						String[] picMd5Sums = new String[items.size()];
						for (int i = 0; i < picMd5Sums.length; i++) {
							picMd5Sums[i] = items.get(i).elementTextTrim("PicMd5Sum");
						}
						Method method = ReflectionUtils.findMethod(clazz, eventKey, String.class, String.class, Long.class, String.class, String.class, String.class, Integer.class, String[].class);
						if (method != null) {
							msg = (Msg) ReflectionUtils.invokeMethod(method, picPhotoOrAlbumEventHandler, toUserName, fromUserName, createTime, msgType, event, eventKey, count, picMd5Sums);
						} else {
							method = ReflectionUtils.findMethod(clazz, "service", String.class, String.class, Long.class, String.class, String.class, String.class, Integer.class, String[].class);
							msg = (Msg) ReflectionUtils.invokeMethod(method, picPhotoOrAlbumEventHandler, toUserName, fromUserName, createTime, msgType, event, eventKey, count, picMd5Sums);
						}
					} else if ("pic_weixin".equals(event)) {
						Class clazz = picWeiXinEventHandler.getClass();
						Element sendPicsInfoElement = xmlElement.element("SendPicsInfo");
						Integer count = Integer.valueOf(sendPicsInfoElement.elementTextTrim("Count"));
						Element picListElement = sendPicsInfoElement.element("PicList");
						List<Element> items = picListElement.elements("item");
						String[] picMd5Sums = new String[items.size()];
						for (int i = 0; i < picMd5Sums.length; i++) {
							picMd5Sums[i] = items.get(i).elementTextTrim("PicMd5Sum");
						}
						Method method = ReflectionUtils.findMethod(clazz, eventKey, String.class, String.class, Long.class, String.class, String.class, String.class, Integer.class, String[].class);
						if (method != null) {
							msg = (Msg) ReflectionUtils.invokeMethod(method, picWeiXinEventHandler, toUserName, fromUserName, createTime, msgType, event, eventKey, count, picMd5Sums);
						} else {
							method = ReflectionUtils.findMethod(clazz, "service", String.class, String.class, Long.class, String.class, String.class, String.class, Integer.class, String[].class);
							msg = (Msg) ReflectionUtils.invokeMethod(method, picWeiXinEventHandler, toUserName, fromUserName, createTime, msgType, event, eventKey, count, picMd5Sums);
						}
					} else if ("location_select".equals(msgType)) {
						Class clazz = locationSelectEventHandler.getClass();
						Element sendLocationInfoElement = xmlElement.element("SendLocationInfo");
						Double locationX = Double.parseDouble(sendLocationInfoElement.elementTextTrim("Location_X"));
						Double locationY = Double.parseDouble(sendLocationInfoElement.elementTextTrim("Location_Y"));
						Integer scale = Integer.parseInt(sendLocationInfoElement.elementTextTrim("Scale"));
						String label = sendLocationInfoElement.elementTextTrim("Label");
						String poiname = sendLocationInfoElement.elementTextTrim("Poiname");
						Method method = ReflectionUtils.findMethod(clazz, "service", String.class, String.class, Long.class, String.class, Double.class, Double.class, Integer.class, String.class, String.class);
						msg = (Msg) ReflectionUtils.invokeMethod(method, locationSelectEventHandler, toUserName, fromUserName, createTime, msgType, locationX, locationY, scale, label, poiname);
					}
				}
				out.write(msg == null ? "" : msg.toXml());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping("initAccessToken.do")
	public void initAccessToken(PrintWriter out) {
		if (WeChatClient.initAccessToken()) {
			out.println("initAccessToken: success");
			out.println("accessToken: " + WeChatClient.getAccessToken());
		} else {
			out.println("initAccessToken: failure");
		}
	}

	@RequestMapping("initTicket.do")
	public void initTicket(PrintWriter out) {
		if (WeChatClient.initTicket()) {
			out.println("initTicket: success");
			out.println("ticket: " + WeChatClient.getTicket());
		} else {
			out.println("initTicket: failure");
		}
	}

	@RequestMapping("initMenu.do")
	public void initMenu(PrintWriter out) {
		if (WeChatClient.initMenu()) {
			out.write("initMenu: success");
		} else {
			out.write("initMenu: failure");
		}
	}

}
