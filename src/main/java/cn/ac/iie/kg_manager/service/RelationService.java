package cn.ac.iie.kg_manager.service;

import cn.ac.iie.kg_manager.bean.Relation;
import com.github.pagehelper.Page;

import java.util.List;

public interface RelationService {
    Relation getRelationsBySubjctIdAndObject(String subjectId, String object);
    List<Relation> getPropertiesBySubjcetId(String subjectId);
    List<String> getAllRelations();
    Page<Relation> getTriplesByRelation(String relation);
}
