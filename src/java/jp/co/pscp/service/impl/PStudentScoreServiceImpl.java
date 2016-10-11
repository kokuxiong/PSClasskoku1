/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.pscp.service.impl;

import javax.ejb.Stateless;
import javax.persistence.Query;
import jp.co.pscp.entity.PStudentScore;
import jp.co.pscp.service.PStudentScoreService;
import jp.co.pscp.util.CommitResult;
import jp.co.pscp.util.ConstUtil;

/**
 *
 * @author 雄
 */
@Stateless
public class PStudentScoreServiceImpl extends BaseService implements PStudentScoreService {

    @Override
    public CommitResult persistStudent(PStudentScore entity) {

        CommitResult result = new CommitResult(ConstUtil.PROCCESS_TYPE_INSERT);

        try {
            em.persist(entity);

            result.setSuccess(true);

        } catch (Exception e) {
            result.setSuccess(false);
            result.anylizeException(e);
        }
        return result;
    }

    @Override
    public long checkExistsScoreCourse(String stuId, int couId) {
        
        Query q=em.createNativeQuery("select count(1) from p_student_score as t where t.stu_id = ?1 and t.cou_id = ?2");
        
        q.setParameter(1, stuId);
        
        q.setParameter(2, couId);
        
        return (long)q.getSingleResult();
    }

}
