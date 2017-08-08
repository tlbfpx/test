package tlb.mall.common.util.webservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tlb.mall.common.util.util.ReflectionUtils;
import tlb.mall.common.util.util.StringUtil;


import java.text.ParseException;
import java.util.*;
import java.util.Map.Entry;

public abstract class AbstractRequest {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final long INTERVAL = 5 * 60 * 1000;

    protected String sign; // 签名
    protected String serviceCode; // 请求的接口代码
    protected String timestamp; // 时间戳

    protected Map<String, String> paramsValue;

    private static String[] paramNames = { "sign", "serviceCode", "timestamp" };

    /**
     * 获取所有参数名称
     * 
     * @return
     */
    public String[] getParamNames() {
        return paramNames;
    }

    /**
     * 设置请求参数值
     */
    public void setParamsValue(Map<String, String> map) {
        this.paramsValue = map;
        String[] paramsName = this.getParamNames();
        if (paramsName == null || paramsValue == null)
            return;
        for (int i = 0; i < paramsName.length; i++) {
            String value = paramsValue.get(paramsName[i]);
            if (value != null) {
                ReflectionUtils.setFieldValue(this, paramsName[i], paramsValue.get(paramsName[i]));
            }
        }
    }

    /**
     * 验证令牌
     * 
     * @return
     */
    public boolean checkSign() {
        List<String> params = new ArrayList<String>();
        for (Entry<String, String> entry : paramsValue.entrySet()) {
            if (entry.getKey().equals("sign")) {
                continue;
            }
            params.add(entry.getValue());
        }
        // 1. 参数进行字典序排序
        Collections.sort(params, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1 == null) {
                    o1 = "";
                }
                if (o2 == null) {
                    o2 = "";
                }
                return o1.compareTo(o2);
            }
        });
        /*
         * StringBuffer sb = new StringBuffer(); sb.append(apiSecret); for (int
         * i = 0; i < params.size(); i++) { sb.append(params.get(i)); }
         * sb.append(apiSecret); LOGGER.info("-----加密前字符串--------" +
         * sb.toString()); String valid_signature = SHA1.encode(sb.toString());
         * LOGGER.info("服务端加密后的token---------" + valid_signature);
         * LOGGER.info("客户端加密后的token---------" + signature); return
         * valid_signature.equals(signature);
         */
        return true;
    }

    /**
     * 
     * <p>
     * Description:校验接口连接时间是否有效<br />
     * </p>
     * 
     * @author Bill Chan
     * @date 2017年3月31日 下午6:24:19
     * @return boolean
     * @throws InterruptedException
     */
    public boolean checkVerifyTime() {
        Long timeInterval = new Date().getTime() - Long.parseLong(timestamp);
        return timeInterval < INTERVAL ? true : false;
    }

    /**
     * 校验通用参数
     * 
     * @return
     * @throws ParseException
     */
    public List<String> checkParams() {
        List<String> list = new ArrayList<String>();
        for (String paramName : paramNames) {
            if (paramName.equals("serviceCode")) {
                continue;
            }
            String currentParam = paramsValue.get(paramName);
            if (StringUtil.isEmpty(currentParam)) {
                list.add(String.format("%s 不能为空", paramName));
                return list;
            }
        }
        return list;
    }

    public Map<String, String> getParamsValue() {
        return paramsValue;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public static void setParamNames(String[] paramNames) {
        AbstractRequest.paramNames = paramNames;
    }

}
