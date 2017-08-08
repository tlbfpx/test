package tlb.mall.common.util.enums;

public enum SignInterceptStatus {

    NO(0, "不进行sign验签"), YES(1, "进行sign验签");

    private int type;
    private String description;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private SignInterceptStatus(int type, String description) {
        this.type = type;
        this.description = description;
    }
}