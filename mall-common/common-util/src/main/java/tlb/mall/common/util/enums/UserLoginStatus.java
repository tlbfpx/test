package tlb.mall.common.util.enums;


import tlb.mall.common.util.enums.entity.BaseEntityEnum;

/**
 * 用户登录状态,是否允许登录
 * 
 * 
 * 创建日期：2016年8月4日 下午2:48:37
 * 操作用户：zhoubang
 *
 */
public enum UserLoginStatus implements BaseEntityEnum<UserLoginStatus> {

    YES(0, "允许"), NO(1, "禁止");

    private UserLoginStatus(int code, String description) {
        this.code = new Integer(code);
        this.description = description;
    }

    private int code;

    private String description;

    public int getCode() {

        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int getIntValue() {
        return this.code;
    }
}
