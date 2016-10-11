/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.pscp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import jp.co.pscp.entity.MCourse;
import jp.co.pscp.entity.MStudent;
import jp.co.pscp.entity.PCourseSelection;
import jp.co.pscp.entity.PStudentScore;
import jp.co.pscp.service.CourseService;
import jp.co.pscp.service.PStudentScoreService;
import jp.co.pscp.service.StudentService;
import jp.co.pscp.util.CommitResult;
import jp.co.pscp.util.ConstUtil;

/**
 *
 * @author 雄
 */
@Named("c_score")
@ViewScoped
public class ScoreController extends PageBaseController {

    private String jsonData = "{}";
    private MStudent editingStudent;
    private PStudentScore editingStuScore;
    private List<MStudent> students;
    private List<MCourse> allCourse;
    private int editingPcs;
    private short scoreInsertData;
    private Map<MStudent, List<PCourseSelection>> stuScores;

    @EJB
    private StudentService stuService;
    @EJB
    private CourseService couService;
    @EJB
    private PStudentScoreService scoreService;

    public MStudent getEditingStudent() {
        return editingStudent;
    }

    public void setEditingStudent(MStudent editingStudent) {
        this.editingStudent = editingStudent;
    }

    public List<MStudent> getStudents() {
        return students;
    }

    public void setStudents(List<MStudent> students) {
        this.students = students;
    }

    public List<MCourse> getAllCourse() {
        return allCourse;
    }

    public void setAllCourse(List<MCourse> allCourse) {
        this.allCourse = allCourse;
    }

    public int getEditingPcs() {
        return editingPcs;
    }

    public void setEditingPcs(int editingPcs) {
        this.editingPcs = editingPcs;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public short getScoreInsertData() {
        return scoreInsertData;
    }

    public void setScoreInsertData(short scoreInsertData) {
        this.scoreInsertData = scoreInsertData;
    }

    //map→list変換
    public List<Map.Entry<MStudent, List<PCourseSelection>>> getStuScores() {
        Set<Map.Entry<MStudent, List<PCourseSelection>>> result = this.stuScores.entrySet();
        return new ArrayList<>(result);
    }

    public short getLastScore(MStudent stu, MCourse cou) {

        for (PCourseSelection pcsEach : stu.getPcsList()) {
            if (cou.getCouId() == pcsEach.getCourse().getCouId()) {
                if (pcsEach.getCoursesSore() == null) {
                    break;
                } else {
                    return pcsEach.getCoursesSore().getScore();
                }
            }
        }
        return 0;
    }

    public boolean isBelowZero(MStudent stu, MCourse cou) {
        return this.getLastScore(stu, cou) > 0;
    }

    @PostConstruct
    public void init() {

        this.students = stuService.findStudents();

        this.allCourse = couService.findCourses();

        this.stuScores = new HashMap<>();
        //すべての学生を遍歴する。
        for (MStudent stuEach : students) {
            stuScores.put(stuEach, stuEach.getPcsList());
        }

        this.clearPage();

    }

    public void showProfile(MStudent stu) {
        List<MStudent> _students = new ArrayList<>();

        _students.add(stu);

        this.jsonData = stuService.createChartJsonData(_students, allCourse);
    }

    public void scoreInsert() {

        FacesMessage fm = null;

        CommitResult result = null;

        boolean isScoreExist;

        isScoreExist = scoreService.checkExistsScoreCourse(this.editingStudent.getStuId(), this.editingPcs) > 0 ? true : false;

        if (isScoreExist) {
            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, ConstUtil.MSG_SUMMARY_ERROR, "得点は既に設置されている");
            this.addMessage(fm);
            return;
        }

        if (this.scoreInsertData < 1 || this.scoreInsertData > 20) {

            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, ConstUtil.MSG_SUMMARY_ERROR, "得点は１から20までをお入力ください");
            this.addMessage(fm);
            return;
        }

        //対象初期化及びprimary key設置
        this.editingStuScore = new PStudentScore(this.editingStudent.getStuId(), this.editingPcs);

        this.editingStuScore.setScore(this.scoreInsertData);

        //设置更新年月
        this.editingStuScore.setUpdDate(new Date());

        this.editingStuScore.setDelFlg('0');

        this.editingStuScore.setVersion(0);

        result = scoreService.persistStudent(this.editingStuScore);

        if (result.isSuccess()) {

            this.students = stuService.findStudents();

            this.allCourse = couService.findCourses();

            this.stuScores = new HashMap<>();
            //すべての学生を遍歴する。
            for (MStudent stuEach : students) {
                stuScores.put(stuEach, stuEach.getPcsList());
            }

            this.clearPage();

        }

        fm = result.creatFacesMessage();

        if (fm != null) {
            this.addMessage(fm);
        }

    }

    private void clearPage() {

        this.editingStudent = new MStudent();

        this.editingPcs = 0;

        this.editingStuScore = new PStudentScore();

        this.scoreInsertData = 1;

    }
}
