/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.pscp.service;

import java.util.List;
import jp.co.pscp.entity.MCourse;
import jp.co.pscp.util.CommitResult;

/**
 * 課程データ取得
 *
 * @author 雄
 */
public interface CourseService {

    MCourse findCourse(int key);
    
    
    List<MCourse> findCourses();

    /**
     * コース名の重複チェック
     *
     * @param name
     * @return
     */
    long checkExistsCourseName(String cName);

    /**
     * 新規コース
     *
     * @param entity
     */
    CommitResult persistCourse(MCourse entity);

    /**
     *
     * @param entity
     * @return
     */
    CommitResult mergeCourse(MCourse entity);
    /*
    
   
     */

    CommitResult removeCourse(MCourse entity);

    /**
     *
     *
     */
    boolean checkEditingCourseSelected(MCourse cou);
}
