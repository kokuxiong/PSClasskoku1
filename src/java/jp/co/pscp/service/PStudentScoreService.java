/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.pscp.service;

import jp.co.pscp.entity.PStudentScore;
import jp.co.pscp.util.CommitResult;

/**
 *
 * @author 雄
 */
public interface PStudentScoreService {
    CommitResult persistStudent(PStudentScore entity);
    
    
    long checkExistsScoreCourse(String stuId,int couId);
}


