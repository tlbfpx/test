package tlb.mall.user.rpc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tlb.mall.user.rpc.api.BaseService;

import java.util.List;

@Service("baseServiceImpl")
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    private Mapper<T> mapper;

    public Mapper<T> getMapper() {
        return mapper;
    }

    public void setMapper(Mapper<T> mapper) {
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public T get(Object o) {
        return mapper.selectByPrimaryKey(o);
    }

    @Transactional
    public int save(T o) {
        return mapper.insert(o);
    }

    @Transactional
    public int update(T o) {
        return mapper.updateByPrimaryKey(o);
    }

    @Transactional
    public int delete(T o) {
        return mapper.delete(o);
    }
    
    @Transactional
    public int deleteByPrimaryKey(Object id) {
        return mapper.deleteByPrimaryKey(id);
    }
    
    @Transactional
    public List<T> getAllList() {
        return mapper.selectAll();
    }

    @Transactional
    public List<T> getByIds(Class<T> object, String property, List<?> ids) {
        Example example = new Example(object);
        example.createCriteria().andIn(property, ids);
        return mapper.selectByExample(example);
    }
}
