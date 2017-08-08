package tlb.mall.common.util.enums;

/**
 * 接口请求返回代码
 * 
 */
public enum ResultCode {

    SIGN_ERROR(10001, "sign签名错误"),

    NONCE_STR_EMPTY(10002, "nonce_str为空"),

    OPENID_EMPTY(10003, "openid为空"),

    LOAD_USER_ERROR(10004, "获取用户信息错误"),

    USER_EMPTY(10005, "用户不存在"),

    SYSTEM_ERROR(10006, "系统异常"),

    CONVERT_JSON_ERROR(10007, "JSON转换异常"),

    PAY_PARAMS_NULL_ERROR(10008, "支付请求参数为空"),

    UNIURL_REQUEST_ERROR(10009, "请求统一下单接口异常"),

    PREPAYID_EMPTY(10010, "预支付单号为空"),

    PAY_AJAX_CONVERTJSON_ERROR(10011, "前端支付参数JSON转换异常"),

    ORDER_ALREADY_PAY_ERROR(10012, "该订单已支付"),

    TRANSATION_ID_ERROR(10013,"微信支付订单号获取失败"),
    
    CERT_ERROR(10014,"证书加载失败"),
	
	REFUND_CONVERTJSON_ERROR(10015, "退款请求参数JSON转换异常"),
	
	MONEY_CONVERTJSON_ERROR(10016, "金额转换异常"),
	
	OUT_TRADE_NO_EMPTY(10017,"订单号为空"),
	
	REFUND_FEE_EMPTY(10018,"refund_fee为空"),
	
	SIGN_EMPTY(10019,"sign为空"),
	
	TOTAL_FEE_EMPTY(10020,"total_fee为空"),
	
    QRCODE_GET_ERROR(10021,"获取二维码失败"),
    
    QRCODE_TOKEN_ERROR(10021,"二维码token获取失败"),
	
	QRCODE_PARAM_FORMAT_ERROR(10021,"二维码消息解析失败");
	
	
	
	
    private int code;
    private String msg;

    private ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    /**
     * 根据code获取信息
     * 
     * @param code
     * @return
     */
    public static String getMsg(int code) {
        for (ResultCode rc : ResultCode.values()) {
            if (rc.getCode() == code) {
                return rc.getMsg();
            }
        }
        return "";
    }

}
