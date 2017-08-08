package tlb.mall.common.util.enums;

public enum DataSourceEnum {

    //MYSQL("mysql"), SQLSERVER("sqlserver");

    Master("masterDS"), Slave("slaveDS");

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private DataSourceEnum(String type) {
        this.type = type;
    }
}