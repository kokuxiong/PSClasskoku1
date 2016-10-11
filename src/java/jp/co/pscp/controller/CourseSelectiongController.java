/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.pscp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import jp.co.pscp.entity.MCourse;
import jp.co.pscp.entity.MStudent;
import jp.co.pscp.entity.PCourseSelection;
import jp.co.pscp.entity.PCourseSelectionPK;
import jp.co.pscp.service.CourseService;
import jp.co.pscp.service.StudentService;
import jp.co.pscp.util.CommitResult;
import jp.co.pscp.util.ConstUtil;

/**
 *
 * @author 雄
 */
@Named("c_course_selectiong")
@ViewScoped
public class CourseSelectiongController extends PageBaseController {

    private List<MStudent> students;
    private MStudent editingStudent;
    private List<MCourse> courses;
    private String[] selectedCourses;

    @EJB
    private StudentService stuService;
    @EJB
    private CourseService couService;

    public List<MCourse> getCourses() {
        return courses;
    }

    public void setCourses(List<MCourse> courses) {
        this.courses = courses;
    }

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

    public String[] getSelectedCourses() {
        return selectedCourses;
    }

    public void setSelectedCourses(String[] selectedCourses) {
        this.selectedCourses = selectedCourses;
    }

    @PostConstruct
    public void init() {
        this.students = stuService.findStudents();
        this.courses = couService.findCourses();
    }

    public void startEdit() {
        this.pageStatus = ConstUtil.PAGE_STATUS.EDITING;
        List<PCourseSelection> pcs = stuService.findSelectCourse(this.editingStudent);

        if (pcs.isEmpty()) {
            this.addMessage(new FacesMessage(FacesMessage.SEVERITY_WARN, ConstUtil.MSG_SUMMARY_WARNING, "まだ何もえらんでいません"));

        }

        this.selectedCourses = new String[pcs.size()];
        int index = 0;
        for (PCourseSelection pcsEach : pcs) {
            selectedCourses[index++]
                    = String.valueOf(pcsEach.getPCourseSelectionPK().getTranId());
        }
    }

    public void cancel() {
        this.pageStatus = ConstUtil.PAGE_STATUS.INIT;
        this.editingStudent = null;
    }

    public void commit() {
        CommitResult result = null;
        FacesMessage fm = null;
        List<PCourseSelection> pcsList = new ArrayList<>();

        Date updDate = new Date();

        for (String s : this.selectedCourses) {
            PCourseSelectionPK pk = new PCourseSelectionPK(this.editingStudent.getStuId(), Integer.valueOf(s));
            PCourseSelection pcs = new PCourseSelection(pk);
            pcs.setDelFlg(ConstUtil.DEL_FLG_NO);
            pcs.setUpdDate(updDate);
            pcs.setVersion(0);
            pcsList.add(pcs);

        }

        result = stuService.mergeSelectedCourses(editingStudent, pcsList);
        if (result.isSuccess()) {
            this.editingStudent = null;
            this.pageStatus = ConstUtil.PAGE_STATUS.INIT;
        }
        fm = result.creatFacesMessage();
        if (fm != null) {
            this.addMessage(fm);
        }
    }

}
