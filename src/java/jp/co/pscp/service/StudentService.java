/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.pscp.service;

import java.util.List;
import jp.co.pscp.entity.MCourse;
import jp.co.pscp.entity.MStudent;
import jp.co.pscp.entity.PCourseSelection;
import jp.co.pscp.util.CommitResult;

/**
 *
 * @author 雄
 */
public interface StudentService {
    
    
    /**
     * 
     * @return 
     */
    List<MStudent> findStudents();

    
    long checkExistsEmail(String stuEmail);

    /**
     * 新規コース
     *
     * @param entity
     */
    CommitResult persistStudent(MStudent entity);

    /**
     *
     * @param entity
     * @return
     */
    CommitResult mergeStudent(MStudent entity);
    /*
    
   
     */

    CommitResult removeStudent(MStudent entity);

    /**
     *
     *
     */
    List<PCourseSelection> findSelectCourse(MStudent stu);
        
    CommitResult mergeSelectedCourses(MStudent stu,List<PCourseSelection> pcsList);

    /**
     * 
     * @param stus
     * @param cous
     * @return 
     */
    String createChartJsonData(List<MStudent> stu,List<MCourse> cous);
}
