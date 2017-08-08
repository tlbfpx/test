package tlb.mall.common.util.enums;


import tlb.mall.common.util.enums.entity.BaseEntityEnum;

/**
 * 用户来源哪个系统，目前暂时就一个用户中心
 */
public enum FromSystemEnum implements BaseEntityEnum<FromSystemEnum> {

    DEFAULT_CURRENT_USER_CENTER(0, "用户中心系统");

    private FromSystemEnum(int code, String description) {
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
