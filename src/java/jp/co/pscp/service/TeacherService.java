/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.pscp.service;

import java.util.List;
import jp.co.pscp.entity.MTeacher;
import jp.co.pscp.util.CommitResult;

/**
 *
 * @author 雄
 */
public interface TeacherService {
    /**
     * find teachers
     * @return 
     */
    List<MTeacher> findTeachers();
    
    /**
     * check email has exsit
     * @param teaEmail
     * @return 
     */
    long checkExistsEmail(String teaEmail);
    
    /**
     * 先生新規
     * @param entity
     * @return 
     */
    CommitResult persistTeacher(MTeacher entity);
    
    /**
     * 先生情報変更
     * @param entity
     * @return 
     */
    CommitResult mergeTeacher(MTeacher entity);
    
    /**
     * 先生削除
     * @param entity
     * @return 
     */
    CommitResult removeTeacher(MTeacher entity);
    
}
