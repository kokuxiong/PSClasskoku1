/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.pscp.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.persistence.Query;
import jp.co.pscp.entity.MCourse;
import jp.co.pscp.entity.MStudent;
import jp.co.pscp.entity.PCourseSelection;
import jp.co.pscp.service.StudentService;
import jp.co.pscp.util.CommitResult;
import jp.co.pscp.util.ConstUtil;

/**
 *
 * @author 雄
 */
@Stateless
public class StudentServiceImpl extends BaseService implements StudentService {

    @Override
    public List<MStudent> findStudents() {

        Query q = em.createQuery("SELECT t FROM MStudent as t ORDER BY t.stuId");

        return q.getResultList();
    }

    @Override
    public long checkExistsEmail(String stuEmail) {
        
        Query q=em.createNativeQuery("select count(1) from m_student as t where t.email = ?1");
        
        q.setParameter(1, stuEmail);
        
        return (long)q.getSingleResult();
    }

    @Override
    public CommitResult persistStudent(MStudent entity) {
        
        CommitResult result= new CommitResult(ConstUtil.PROCCESS_TYPE_INSERT);
        
        try{
            em.persist(entity);
            
            result.setSuccess(true);
        
        }catch(Exception e){
            result.setSuccess(false);
            result.anylizeException(e);
        }
        return result;
    }

    @Override
    public CommitResult mergeStudent(MStudent entity) {
        CommitResult result= new CommitResult(ConstUtil.PROCCESS_TYPE_UPDATE);
        
        try{
            em.merge(entity);
            
            result.setSuccess(true);
        
        }catch(Exception e){
            result.setSuccess(false);
            result.anylizeException(e);
        }
        return result;
    }

    @Override
    public CommitResult removeStudent(MStudent entity) {
        return null;
    }

    @Override
    public List<PCourseSelection> findSelectCourse(MStudent stu) {
        
        Query q = em.createQuery("SELECT t FROM PCourseSelection t where t.pCourseSelectionPK.stuId = ?1");
        q.setParameter(1, stu.getStuId());
        return q.getResultList();
    }

    @Override
    public CommitResult mergeSelectedCourses(MStudent stu,List<PCourseSelection> pcsList) {
        
        CommitResult result= new CommitResult(ConstUtil.PROCCESS_TYPE_UPDATE);
        try{
            this.removeAllSelectedCourse(stu.getStuId());
            
            for(PCourseSelection pcsEach:pcsList){
                em.persist(pcsEach);
            }
            
            result.setSuccess(true);
            
        }catch(Exception e){
            result.setSuccess(false);
            result.anylizeException(e);
        }
        return result;
        
        
        
    }
    
    private void removeAllSelectedCourse(String stuId){
        Query q = em.createNativeQuery("delete from p_course_selection where stu_id = ?1");
        q.setParameter(1, stuId);
        q.executeUpdate();
    }

    @Override
    public String createChartJsonData(List<MStudent> stus, List<MCourse> cous) {
        JsonObject jobj = null;
        JsonObjectBuilder summaryData = Json.createObjectBuilder();

        if (stus.isEmpty()) {
            summaryData = Json.createObjectBuilder()
                    .add("hasData", JsonValue.FALSE)
                    .add("message", "Has no data");
            jobj = summaryData.build();
            return jobj.toString();
        }

        JsonArrayBuilder categories = Json.createArrayBuilder();

        for (MCourse couEach : cous) {
            //[.net,java,English ....]
            categories.add(couEach.getCouName());
        }

        JsonArrayBuilder series = Json.createArrayBuilder();

        for (MStudent stuEach : stus) {

            List<PCourseSelection> pcs = stuEach.getPcsList();
            Map<Integer, Short> scoreMap = new HashMap<>();
            for (PCourseSelection pcsEach : pcs) {
                if(pcsEach.getCoursesSore()!=null){
                    scoreMap.put(pcsEach.getPCourseSelectionPK().getTranId(), pcsEach.getCoursesSore().getScore());
                }
                
            }

            JsonArrayBuilder dataArray = Json.createArrayBuilder();

            for (MCourse couEach : cous) {
                Short score = scoreMap.get(couEach.getCouId());
                dataArray.add(score == null ? 0: score.intValue());
            }

            JsonObject currentStu = Json.createObjectBuilder()
                    .add("name", stuEach.getStuName())
                    .add("data",dataArray)
                    .add("pointPlacement", "on")
                    .build();
            
            series.add(currentStu);
        }
        
        
        summaryData = Json.createObjectBuilder()
                .add("hasData", JsonValue.TRUE)
                .add("categories", categories)
                .add("message", "")
                .add("series",series);
        jobj = summaryData.build();

        return jobj.toString();
    }

}
