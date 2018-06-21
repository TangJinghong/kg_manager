package cn.ac.iie.kg_manager.service.impl;

import cn.ac.iie.kg_manager.bean.Entity;
import cn.ac.iie.kg_manager.mapper.EntityMapper;
import cn.ac.iie.kg_manager.service.EntityService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "entityService")
public class EntityServiceImpl implements EntityService {
    @Autowired
    private EntityMapper entityMapper;
    @Override
    public Page<Entity> getEntitiesLikeLable(String lable) {
        return entityMapper.selectLikeLable(lable);
    }

    @Override
    public Entity getEntityById(String id) {
        return entityMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Entity> getCorrelationEntitiesById(String id) {
        return entityMapper.selectCorrelationEntitiesById(id);
    }

    @Override
    public List<Entity> getCorrelationEntitiesByIdAndType(String id, String type) {
        return entityMapper.selectCorrelationEntitiesByIdAndType(id, type);
    }
}
