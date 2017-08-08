package tlb.mall.user.rpc.service.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tlb.mall.entity.sys.SysMenu;
import tlb.mall.user.dao.sys.MenuMapper;
import tlb.mall.user.rpc.api.sys.MenuService;
import tlb.mall.user.rpc.service.BaseServiceImpl;

import java.util.List;

@Service("menuServiceImpl")
public class MenuServiceImpl extends BaseServiceImpl<SysMenu> implements MenuService {

	@Autowired
	private MenuMapper menuMapper;

	@Override
	public List<SysMenu> getAllParentList() {
		Example example = new Example(SysMenu.class);
		example.createCriteria().andEqualTo("parentId", 0);
		return menuMapper.selectByExample(example);
	}

	@Override
	public List<SysMenu> getSubMenuByParentId(String id) {
		Example example = new Example(SysMenu.class);
		example.createCriteria().andEqualTo("parentId", id);
		example.setOrderByClause("sort ASC");//排序值升序排序
		return menuMapper.selectByExample(example);
	}
}
