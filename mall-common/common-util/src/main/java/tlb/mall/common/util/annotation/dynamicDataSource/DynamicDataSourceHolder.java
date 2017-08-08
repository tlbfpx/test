package tlb.mall.common.util.annotation.dynamicDataSource;

/**
 * 参考 http://www.cnblogs.com/surge/p/3582248.html
 *
 */
public class DynamicDataSourceHolder {
	public static final ThreadLocal<String> holder = new ThreadLocal<String>();

	public static void putDataSource(String name) {
		holder.set(name);
	}

	public static String getDataSouce() {
		return holder.get();
	}
}