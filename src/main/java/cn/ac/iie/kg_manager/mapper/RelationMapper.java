package cn.ac.iie.kg_manager.mapper;

import cn.ac.iie.kg_manager.bean.Relation;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Relation record);

    int insertSelective(Relation record);

    Relation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Relation record);

    int updateByPrimaryKeyWithBLOBs(Relation record);

    int updateByPrimaryKey(Relation record);

    Relation selectBySubjectIdAndObject(String subjectId, String object);

    List<Relation> selectPropertiesBySubjectId(String subjectId);

    List<String> selectDistinctRelation();

    Page<Relation> selectByRelation(String relation);
}