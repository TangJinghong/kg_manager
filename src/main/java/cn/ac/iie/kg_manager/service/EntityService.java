package cn.ac.iie.kg_manager.service;

import cn.ac.iie.kg_manager.bean.Entity;
import com.github.pagehelper.Page;

import java.util.List;

public interface EntityService {
    Page<Entity> getEntitiesLikeLable(String lable);
    Entity getEntityById(String id);
    List<Entity> getCorrelationEntitiesById(String id);
    List<Entity> getCorrelationEntitiesByIdAndType(String id, String type);
}
