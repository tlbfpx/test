package tlb.mall.user.rpc.service.wechat;

import java.text.MessageFormat;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tlb.mall.common.util.AjaxResult;
import tlb.mall.common.util.activemq.order.OrderSync;
import tlb.mall.common.util.util.HttpSend;
import tlb.mall.common.util.util.JsonUtil;
import tlb.mall.entity.order.OrderInfo;
import tlb.mall.user.rpc.api.activemq.QueueMessageService;
import tlb.mall.user.rpc.api.wechat.TemplateMessageService;


import java.text.MessageFormat;
import java.util.Map;

@Service("templateMessageServiceImpl")
public class TemplateMessageServiceImpl implements TemplateMessageService {

//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//
//    @Value("#{configProperties['wx.template.notice.url']}")
//    public  String templateNoticeUrl; // 微信模板消息请求接口url
//
//    @Value("#{configProperties['wx.order.sync.templateMessageId']}")
//    private String WX_ORDER_SYNC_TEMPLATEMESSAGEID;
//
//    /**
//     * ptp消息生产者。
//     */
//    @Autowired
//    private QueueMessageService queueMessageService;
//
//    @Override
//    public void sendMessage(String openId, OrderInfo orderInfo, AjaxResult<String> result) throws Exception {
//        logger.debug("订单同步信息，openId：" + openId);
//        logger.debug("订单同步信息，orderInfo：" + orderInfo);
//        logger.debug("订单同步信息，result：" + result);
//        logger.debug("订单同步信息，queueMessageService：" + queueMessageService);
//        if (StringUtils.isBlank(openId)) {
//            result.setCode(10001);
//            result.setMsg("openId为空");
//            return;
//        }
//
//        OrderSync orderSync = new OrderSync();
//        orderSync.setOpenId(openId);
//        orderSync.setOrderInfoJson(JsonUtil.toJson(orderInfo));
//        logger.debug("订单同步信息，orderSync：" + orderSync);
//        //发送模板消息
//        queueMessageService.sendOrderSyncTemplateMessage(orderSync);
//    }
//
//    /**
//     * 发送模板消息
//     *
//     * 创建日期：2016年6月1日 下午1:50:49 操作用户：zhoubang
//     *
//     * @param jsonData
//     *            组装好的json数据
//     * @return
//     * @throws Exception
//     */
//    public String sendTemplateMsg(String jsonData) throws Exception {
//        logger.debug(MessageFormat.format("微信模板消息通知内容：{0}", jsonData));
//
//        // 获取token
//        Token accessToken = TokenUtil.getAccessToken();
//        logger.debug(MessageFormat.format("微信模板消息通知token：{0}", JsonUtils.toJson(accessToken)));
//
//        String requestUrl = templateNoticeUrl.replace("ACCESS_TOKEN", accessToken.getAccessToken());
//
//        String result = HttpSend.postObject(requestUrl, jsonData);
//        logger.debug(MessageFormat.format("微信模板消息通知结果：{0}", result));
//
//        // 如果出现token过期，则刷新token
//        Map<String, Object> map = JsonUtils.fromJson(result);
//        if (map.containsKey("errcode") && !map.get("errmsg").equals("ok")) {// 40001
//            logger.debug("发送模板消息，发现token过期。" + JsonUtils.toJson(result));
//            Token token = TokenUtil.refershAPIAccessToken();
//            logger.debug("发送模板消息，发现token过期，刷新token，最新token如下：" + JsonUtils.toJson(token));
//            // 获取token后重新发送一次模板消息
//            requestUrl = templateNoticeUrl.replace("ACCESS_TOKEN", accessToken.getAccessToken());
//            result = HttpSend.postObject(requestUrl, jsonData);
//            logger.debug(MessageFormat.format("微信模板消息通知结果：{0}", result));
//        }
//        return result;
//    }
}
