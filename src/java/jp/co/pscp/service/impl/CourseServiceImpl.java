/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.pscp.service.impl;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import jp.co.pscp.entity.MCourse;
import jp.co.pscp.service.CourseService;
import jp.co.pscp.util.CommitResult;
import jp.co.pscp.util.ConstUtil;

/**
 *
 * @author 雄
 */
//无状态
@Stateless
public class CourseServiceImpl extends BaseService implements CourseService {

    @Override
    public List<MCourse> findCourses() {
        Query q = em.createQuery("SELECT t FROM MCourse AS t ORDER BY t.couId");

        return q.getResultList();

    }

    @Override
    public long checkExistsCourseName(String cName) {
        //count后面随便给个数值就行
        Query q = em.createNativeQuery("select count(1) from m_course as t where t.cou_name = ?1");

        q.setParameter(1, cName);

        return (long) q.getSingleResult();

    }

    @Override
    public CommitResult persistCourse(MCourse entity) {

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
    public CommitResult mergeCourse(MCourse entity) {

        CommitResult result = new CommitResult(ConstUtil.PROCCESS_TYPE_UPDATE);

        try {
            em.merge(entity);

            result.setSuccess(true);

        } catch (Exception e) {
            result.setSuccess(false);
            result.anylizeException(e);
        }
        return result;
    }

    @Override
    public CommitResult removeCourse(MCourse entity) {

        CommitResult result = new CommitResult(ConstUtil.PROCCESS_TYPE_DELETE);

        boolean countSelectedCourse = this.checkEditingCourseSelected(entity);

        result.setCourseIsSelected(false);

        if (countSelectedCourse) {
            result.setSuccess(false);
            result.setCourseIsSelected(true);
        } else {
            try {

                entity = em.merge(entity);
                em.remove(entity);

                result.setSuccess(true);

            } catch (Exception e) {
                result.setSuccess(false);
                result.anylizeException(e);
            }
        }

        return result;
    }

    @Override
    public MCourse findCourse(int key) {

        return null;
    }

    @Override
    public boolean checkEditingCourseSelected(MCourse cou) {
        Query q = em.createNativeQuery("select count(1) from p_course_selection as t where t.tran_id = ?1");

        q.setParameter(1, cou.getCouId());

        return (long) q.getSingleResult() > 0;
    }

}
