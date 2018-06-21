package cn.ac.iie.kg_manager.service.impl;

import cn.ac.iie.kg_manager.bean.Relation;
import cn.ac.iie.kg_manager.mapper.RelationMapper;
import cn.ac.iie.kg_manager.service.RelationService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "relationService")
public class RelationServiceImpl implements RelationService {
    @Autowired
    RelationMapper relationMapper;

    @Override
    public Relation getRelationsBySubjctIdAndObject(String subjectId, String object) {
        return relationMapper.selectBySubjectIdAndObject(subjectId, object);
    }

    @Override
    public List<Relation> getPropertiesBySubjcetId(String subjectId) {
        return relationMapper.selectPropertiesBySubjectId(subjectId);
    }

    @Override
    public List<String> getAllRelations() {
        return relationMapper.selectDistinctRelation();
    }

    @Override
    public Page<Relation> getTriplesByRelation(String relation) {
        return relationMapper.selectByRelation(relation);
    }
}
