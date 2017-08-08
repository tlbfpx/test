package tlb.mall.common.util.webservice;

public interface IHandler<T extends IRequest<T>, U extends IResponse<U>> {

    /**
     * 处理过程
     * 
     * @param request
     * @return
     */
    public U process(T request);

}
