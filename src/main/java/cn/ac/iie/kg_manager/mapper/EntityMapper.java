package cn.ac.iie.kg_manager.mapper;

import cn.ac.iie.kg_manager.bean.Entity;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EntityMapper {
    int deleteByPrimaryKey(String id);

    int insert(Entity record);

    int insertSelective(Entity record);

    Entity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Entity record);

    int updateByPrimaryKey(Entity record);

    Page<Entity> selectLikeLable(String lable);

    List<Entity> selectCorrelationEntitiesById(String id);

    List<Entity> selectCorrelationEntitiesByIdAndType(String id, String type);
}