package tlb.mall.user.rpc.api.sys;




import tlb.mall.entity.sys.SysMenu;
import tlb.mall.user.rpc.api.BaseService;

import java.util.List;

public interface MenuService extends BaseService<SysMenu> {

	/**
	 * 获取所有父菜单
	 * @return
	 */
	List<SysMenu> getAllParentList();

	/**
	 * 获取父菜单下面的子菜单
	 * @param id
	 * @return
	 */
	List<SysMenu> getSubMenuByParentId(String id);

}
