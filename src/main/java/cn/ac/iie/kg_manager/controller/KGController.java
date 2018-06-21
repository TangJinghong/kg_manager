package cn.ac.iie.kg_manager.controller;

import cn.ac.iie.kg_manager.bean.Entity;
import cn.ac.iie.kg_manager.bean.Relation;
import cn.ac.iie.kg_manager.service.EntityService;
import cn.ac.iie.kg_manager.service.RelationService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class KGController extends BaseController{
    @Autowired
    private EntityService entityService;
    @Autowired
    private RelationService relationService;

    /**
     *
     * @param lable
     * @return
     */
    @RequestMapping(value = "/getEntitiesLikeLable/{lable}", method = RequestMethod.GET)
    @ResponseBody
    public String getEntitiesLikeLable(@PathVariable("lable") String lable, @RequestParam int pageNum, @RequestParam int pageSize) {
        JSONObject result = new JSONObject();
        String msg = "success";

        if(StringUtils.isBlank(lable)) {
            msg = "field lable can not be null";
        } else {
            if(pageSize == 0){
                pageSize = 10;
            }
            PageHelper.startPage(pageNum, pageSize);
            Page<Entity> entities = entityService.getEntitiesLikeLable(lable);
            result.put("entities", entities);
            result.put("total", entities.getTotal());
            result.put("pages", entities.getPages());
            result.put("pageNum", entities.getPageNum());
        }

        result.put("message", msg);


        return result.toJSONString();
    }


    @RequestMapping(value = "/getCorrelationTypeById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getCorrelationTypeById(@PathVariable("id") String id) {
        JSONObject result = new JSONObject();
        String msg = "success";
        if(StringUtils.isBlank(id)) {
            msg = "field id can not be null";
        } else {
            Entity entity = entityService.getEntityById(id);
            if(entity == null) {
                msg = "The ID："+id+" is not corresponding to the entity";
            } else {
                result.put("source", entity);

                JSONObject types = new JSONObject();
                List<Entity> correlationEntities = entityService.getCorrelationEntitiesById(id);

                for (Entity correlationEntity :
                        correlationEntities) {
                    String type = correlationEntity.getType();
                    if(types.containsKey(type)) {
                        types.put(type, types.getInteger(type) + 1);
                    } else {
                        types.put(type, 1);
                    }
                }
                result.put("types", types);
            }
        }

        result.put("message", msg);

        return result.toJSONString();
    }

    @RequestMapping(value = "/getCorrelationEntitiesByIdAndType/{id}/{type}", method = RequestMethod.GET)
    @ResponseBody
    public String getCorrelationTypeById(@PathVariable("id") String id, @PathVariable("type") String type) {
        JSONObject result = new JSONObject();
        String msg = "success";

        if(StringUtils.isBlank(id)) {
            msg = "field id can not be null";
        } else if (StringUtils.isBlank(type)){
            msg = "field type can not be null";
        } else {
            Entity source = entityService.getEntityById(id);
            if(source == null) {
                msg = "The ID："+id+" is not corresponding to the entity";
            } else {
                result.put("source", source);

                List<Entity> correlationEntities = entityService.getCorrelationEntitiesByIdAndType(id, type);

                JSONArray datas = new JSONArray();
                JSONObject sourceData = new JSONObject();
                sourceData.put("id", source.getId());
                sourceData.put("name", source.getLable());
                sourceData.put("des", "");
                sourceData.put("symbolSize", 300);
                sourceData.put("type", source.getType());

                datas.add(sourceData);
                JSONArray links = new JSONArray();
                for (Entity correlationEntity : correlationEntities) {
                    JSONObject data = new JSONObject();
                    data.put("id", correlationEntity.getId());
                    data.put("name", correlationEntity.getType()+"---"+correlationEntity.getLable());
                    data.put("des", "");
                    List<Entity> entities = entityService.getCorrelationEntitiesById(correlationEntity.getId());
                    data.put("symbolSize", entities.size() % 300 < 10 ? 10 : entities.size() % 300);
                    data.put("type", correlationEntity.getType());

                    datas.add(data);

                    JSONObject link = new JSONObject();
                    link.put("source", source.getLable());
                    link.put("target", correlationEntity.getLable());
                    Relation relation = relationService.getRelationsBySubjctIdAndObject(source.getId(), correlationEntity.getId());
                    link.put("name", relation.getRelation());

                    links.add(link);
                }

                result.put("data", datas);
                result.put("links", links);
            }



        }

        result.put("message", msg);
        return result.toJSONString();
    }

    @RequestMapping(value = "/getPropertiesById/{id}", method = RequestMethod.GET)
    public String getPropertiesById(@PathVariable("id") String id){
        JSONObject result = new JSONObject();
        String msg = "success";

        if(StringUtils.isBlank(id)) {
            msg = "field id can not be null";
        } else {
            List<Relation> relations = relationService.getPropertiesBySubjcetId(id);
            JSONObject properties = new JSONObject();
            for (Relation relation : relations) {
                properties.put(relation.getRelation(), relation.getObject().replaceFirst("value:", ""));
            }

            result.put("properties", properties);
        }

        result.put("message", msg);

        return result.toJSONString();
    }

    @RequestMapping(value = "/getAllRelation", method = RequestMethod.GET)
    @ResponseBody
    public String getAllRelation() {
        JSONObject result = new JSONObject();
        String msg = "success";

        List<String> allRelations = relationService.getAllRelations();
        if(allRelations.size() <= 0) {
            msg = "System has no relations";
        } else {
            result.put("relations", allRelations);
        }
        result.put("message", msg);
        return result.toJSONString();
    }

    @RequestMapping(value = "/getTripleByRelation/{relation}", method = RequestMethod.GET)
    @ResponseBody
    public String getTripleByRelation(@PathVariable("relation") String relation, @RequestParam int pageNum, @RequestParam int pageSize){
        JSONObject result = new JSONObject();
        String msg = "success";

        if(StringUtils.isBlank(relation)) {
            msg = "field relation can not be null";
        } else {
            if(pageSize == 0){
                pageSize = 10;
            }
            PageHelper.startPage(pageNum, pageSize);
            Page<Relation> triples = relationService.getTriplesByRelation(relation);
            System.out.println(triples);
            if(triples.size() <= 0) {
                msg = "relation no corresponding triples";
            } else {
                JSONArray datas = new JSONArray();
                for (Relation triple : triples) {
                    JSONObject data = new JSONObject();

                    Entity source = entityService.getEntityById(triple.getSubjectId());
                    Entity target = entityService.getEntityById(triple.getObject());

                    data.put("source_id", source.getId());
                    data.put("source_name", source.getLable());
                    data.put("source_type", source.getType());
                    data.put("relation", relation);
                    data.put("target_id", target.getId());
                    data.put("target_name", target.getLable());
                    data.put("target_type", target.getType());

                    datas.add(data);
                }

                result.put("datas", datas);
                result.put("total", triples.getTotal());
                result.put("pages", triples.getPages());
                result.put("pageNum", triples.getPageNum());
            }
        }

        result.put("message", msg);

        return result.toJSONString();
    }
}
