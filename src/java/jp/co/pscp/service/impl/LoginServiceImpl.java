/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.pscp.service.impl;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import jp.co.pscp.entity.LoginUser;
import jp.co.pscp.entity.MStudent;
import jp.co.pscp.entity.MTeacher;
import jp.co.pscp.service.LoginService;
import jp.co.pscp.util.ConstUtil;

/**
 *
 * @author 雄
 */
@Stateless
public class LoginServiceImpl extends BaseService implements LoginService{
      
    private MStudent findStudentByLoginId(String loginId, String password) {
        List<MStudent> result = null;
        
        Query q = em.createQuery("select t from MStudent as t where t.email = :email and t.password = :password");
        q.setParameter("email",loginId);
        q.setParameter("password",password);
        //不要用get result
        result = q.getResultList();       
        return result.isEmpty() ? null : result.get(0);
    } 
    private MTeacher findTeacherByLoginId(String loginId, String password) {
        List<MTeacher> result = null;
        
        Query q = em.createQuery("select t from MTeacher as t where t.email = :email and t.password = :password");
        q.setParameter("email",loginId);
        q.setParameter("password",password);
        
        result = q.getResultList();       
        return result.isEmpty() ? null : result.get(0);       
    }

  @Override
    public LoginUser findLoginUser(boolean isTeacher, String loginId, String password) {
        LoginUser result = null;
        MTeacher teacher = null;
        MStudent student = null;
        if (isTeacher) {
            teacher = this.findTeacherByLoginId(loginId, password);
            if (teacher != null) {
                result = new LoginUser();
                result.setId(teacher.getId());
                result.setName(teacher.getTeaName());
                result.setSex(teacher.getSex());
                result.setBirthday(teacher.getBirthday());
                result.setEmail(teacher.getEmail());
                result.setAddress(teacher.getAddress());
                result.setMobile(teacher.getMobile());
                result.setUpdDate(teacher.getUpdDate());
                result.setVersion(teacher.getVersion());
                result.setUserType(ConstUtil.LOGIN_TYPE_T);
            }
        } else {
            student = this.findStudentByLoginId(loginId, password);
            if (student != null) {
                result = new LoginUser();
                result.setId(student.getStuId());
                result.setName(student.getStuName());
                result.setSex(student.getSex());
                result.setBirthday(student.getBirthday());
                result.setEmail(student.getEmail());
                result.setAddress(student.getAddress());
                result.setMobile(student.getMobile());
                result.setUpdDate(student.getUpdDate());
                result.setVersion(student.getVersion());
                result.setUserType(ConstUtil.LOGIN_TYPE_S);
            }
        }
        return result;
    }
    
}
