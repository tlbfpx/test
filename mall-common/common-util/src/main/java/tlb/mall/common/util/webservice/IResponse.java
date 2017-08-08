package tlb.mall.common.util.webservice;

public interface IResponse<U extends IResponse<U>> {

    /**
     * 处理结果
     * 
     * @return
     */
    public int getResultCode();

    /**
     * 描述信息
     * 
     * @return
     */
    public String getResultDesc();

}
