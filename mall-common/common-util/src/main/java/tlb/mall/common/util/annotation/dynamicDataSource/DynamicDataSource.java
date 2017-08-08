package tlb.mall.common.util.annotation.dynamicDataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 参考 http://www.cnblogs.com/surge/p/3582248.html
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	/**
	 * determineCurrentLookupKey方法返回lookupKey,resolvedDataSources方法就是根据lookupKey从Map中获得数据源
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicDataSourceHolder.getDataSouce();
	}

}
