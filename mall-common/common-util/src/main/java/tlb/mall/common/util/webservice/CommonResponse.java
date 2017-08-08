package tlb.mall.common.util.webservice;

public class CommonResponse extends AbstractResponse implements
        IResponse<CommonResponse> {

    private int resultCode;
    private String resultDesc;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

}
