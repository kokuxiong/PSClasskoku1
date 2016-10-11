/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.pscp.service.impl;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import jp.co.pscp.entity.MTeacher;
import jp.co.pscp.service.TeacherService;
import jp.co.pscp.util.CommitResult;
import jp.co.pscp.util.ConstUtil;

/**
 *
 * @author 雄
 */
@Stateless
public class TeacherServiceImpl extends BaseService implements TeacherService{

    @Override
    public List<MTeacher> findTeachers() {
        
        Query q = em.createQuery("SELECT t FROM MTeacher as t ORDER BY t.id");
        
        return q.getResultList();
    }

    @Override
    public long checkExistsEmail(String teaEmail) {
       
        Query q=em.createNativeQuery("select count(1) from m_teacher as t where t.email= ?1");
        q.setParameter(1, teaEmail);
        return (long)q.getSingleResult();
    }

    @Override
    public CommitResult persistTeacher(MTeacher entity) {
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
    public CommitResult mergeTeacher(MTeacher entity) {
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
    public CommitResult removeTeacher(MTeacher entity) {
        return null;
    }
    
}
