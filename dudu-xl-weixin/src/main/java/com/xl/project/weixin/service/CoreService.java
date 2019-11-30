package com.xl.project.weixin.service;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xl.po.User;
import com.xl.po.Weiuser;
import com.xl.project.weixin.api.accessToken.AccessTokenRedis;
import com.xl.project.weixin.api.accessToken.AccessTokenThread;
import com.xl.project.weixin.api.hitokoto.HitokotoUtil;
import com.xl.project.weixin.api.tuling.TuLingUtil;
import com.xl.project.weixin.api.tuling.bean.TuLingBean;
import com.xl.project.weixin.api.userInfo.UserInfoService;
import com.xl.project.weixin.bean.resp.*;
import com.xl.service.UserService;
import com.xl.service.WeiuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xl.project.weixin.main.MenuManager;
import com.xl.project.weixin.pojo.AccessToken;
import com.xl.project.weixin.util.MessageUtil;
import com.xl.project.weixin.util.WeixinUtil;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestParam;


@Service
public class CoreService {

	@Autowired
	private TuLingUtil tuLingUtil;//图灵机器人APi
	@Autowired
	private HitokotoUtil hitokotoUtil;//一言API
	@Autowired
	private AccessTokenRedis accessTokenRedis;//从获取redis中获取accesstoken
	@Autowired
	private UserInfoService userInfoService;//收集微信个人信息的对象
	@Autowired
	private UserService userService;
	@Autowired
	private WeiuserService weiuserService;


	//获取图灵机器人id
	@Autowired
	private TuLingApiKey tuLingApiKey;
	private int line = 1;
	private Map<String,String> map;

	/**
	 *
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";

			// xml请求解析 调用消息工具类MessageUtil解析微信发来的xml格式的消息，解析的结果放在HashMap里；
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id） 下面三行代码是： 从HashMap中取出消息中的字段；
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");

			// 回复文本消息 组装要返回的文本消息对象；
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			// 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义
			// textMessage.setContent("欢迎访问<a
			// href=\"http://www.baidu.com/index.php?tn=site888_pg\">百度</a>!");
			// 将文本消息对象转换成xml字符串
			respMessage = MessageUtil.textMessageToXml(textMessage);
			/**
			 * 演示了如何接收微信发送的各类型的消息，根据MsgType判断属于哪种类型的消息；
			 */

			// 接收用户发送的文本消息内容
			String content = requestMap.get("Content");

			// 创建图文消息
			NewsMessage newsMessage = new NewsMessage();
			newsMessage.setToUserName(fromUserName);
			newsMessage.setFromUserName(toUserName);
			newsMessage.setCreateTime(new Date().getTime());
			newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			newsMessage.setFuncFlag(0);

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				System.out.println();
				if(map==null){//判断map集合是否存在  不存在  再去获取
					map = tuLingApiKey.obtApiKey();
				}
				String apiKey = map.get("key"+line);
				String text = tuLingUtil.sendMessage(content,apiKey);
					while ("请求次数超限制!".equals(text)){
						line++;
						apiKey = map.get("key"+line);
						text = tuLingUtil.sendMessage(content,apiKey);
					}

				//String st =  accessTokenRedis.obtRedisAccessToken();
				//System.out.println("redis数据库accessToken:"st);
				System.out.println("线程内存accessToken:"+AccessTokenThread.accessTokenVal);;
				respContent = text;

				//respContent = "您发送的是文本消息！";
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！";
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是音频消息！";
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {

					System.out.println(fromUserName);
					userInfoService.userInfo(fromUserName);

					respContent = "欢迎关注微信公众号";
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// 事件KEY值，与创建自定义菜单时指定的KEY值对应
					String eventKey = requestMap.get("EventKey");

					if (eventKey.equals("10")) {
						List<Article> articleList = new ArrayList<Article>();
						// 单图文消息
						Article article = new Article();
						User user = userService.selectByOpenid(fromUserName);
						if (user==null){
							Weiuser weiuser = weiuserService.selectByOpenid(fromUserName);
							//标题
							article.setTitle("您还未登录");
							//描述
							article.setDescription("该功能需要登录才能使用,请点此链接登录.");
							//图文显示地址
							article.setPicUrl(
									"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575037778660&di=e251131acd6b075e697d3c8ae68baf33&imgtype=0&src=http%3A%2F%2Fk.zol-img.com.cn%2Fideapad%2F6103%2Fa6102130_s.jpg");
							//跳转地址
							article.setUrl(MenuManager.REAL_URL+"user/toLogin?wid="+weiuser.getId());


						}else {
							if (user.getRid()==1){//发单组
								//标题
								article.setTitle(user.getName()+"您好,您是发单组,暂无权限使用此功能");
								//描述
								article.setDescription("该功能只有抢单组才能使用");
								//图文显示地址
								article.setPicUrl(
										"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=490157971,2458011687&fm=26&gp=0.jpg");
								//跳转地址
								article.setUrl(MenuManager.REAL_URL+"user/toUnauth");
							}else {//抢单组
								//标题
								article.setTitle(user.getName()+"您好,欢迎使用抢单功能");
								//描述
								article.setDescription("抢单教程-->");
								//图文显示地址
								article.setPicUrl(
										"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575039216490&di=c7e205ec49f7c9bc8207d58193fbc042&imgtype=0&src=http%3A%2F%2Fimg.11665.com%2Fimg06_p%2Fi6%2FT1t6jEXkhXXXa2cgnb_093824.jpg");
								//跳转地址
								article.setUrl(MenuManager.REAL_URL+"user/toMeetingGrad?uid="+user.getId());
							}
						}

						articleList.add(article);
						// 设置图文消息个数
						newsMessage.setArticleCount(articleList.size());
						// 设置图文消息
						newsMessage.setArticles(articleList);
						// 将图文消息对象转换为XML字符串
						respMessage = MessageUtil.newsMessageToXml(newsMessage);
						return respMessage;


						//respContent = "会议抢单！";
					}else if(eventKey.equals("30")){
						String str = hitokotoUtil.sendMessage();
						respContent = str;
					}else if(eventKey.equals("33")){

						respContent = "联系我们";
					}

					else if (eventKey.equals("10")) {

						List<Article> articleList = new ArrayList<Article>();
						
						// 单图文消息
						Article article = new Article();
						//标题
						article.setTitle("青青壁纸");
						//描述
						article.setDescription("随机壁纸");
						//图文显示地址
						article.setPicUrl("https://www.baidu.com/img/superlogo_c4d7df0a003d3db9b65e9ef0fe6da1ec.png?where=super");
						//跳转地址
						article.setUrl("https://wallpaper.wispx.cn/random");

						
						articleList.add(article);						
						// 设置图文消息个数
						newsMessage.setArticleCount(articleList.size());
						// 设置图文消息
						newsMessage.setArticles(articleList);
						// 将图文消息对象转换为XML字符串
						respMessage = MessageUtil.newsMessageToXml(newsMessage);
						return respMessage;
					}
					
				}

			}

			// 组装要返回的文本消息对象；
			textMessage.setContent(respContent);
			// 调用消息工具类MessageUtil将要返回的文本消息对象TextMessage转化成xml格式的字符串；
			respMessage = MessageUtil.textMessageToXml(textMessage);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}

}
