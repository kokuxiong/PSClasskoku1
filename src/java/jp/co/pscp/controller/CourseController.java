/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.pscp.controller;

import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import jp.co.pscp.entity.MCourse;
import jp.co.pscp.service.CourseService;
import jp.co.pscp.util.CommitResult;
import jp.co.pscp.util.ConstUtil;

/**
 *
 * @author 雄
 */
@Named("c_course")
@ViewScoped
public class CourseController extends PageBaseController {

    private List<MCourse> courses;
    private MCourse editingCourse;

    private String _lastCourseName;

    @EJB
    private CourseService conService;

    public List<MCourse> getCourses() {
        return courses;
    }

    public void setCourses(List<MCourse> courses) {
        this.courses = courses;
    }

    public MCourse getEditingCourse() {
        return editingCourse;
    }

    public void setEditingCourse(MCourse editingCourse) {
        this.editingCourse = editingCourse;
    }

    @PostConstruct
    public void init() {
        //課程データ取得

        this.courses = conService.findCourses();
        this.clearPage();

    }

    public void startInsert() {
        this.editingCourse = new MCourse();

        this.pageStatus = ConstUtil.PAGE_STATUS.EDITING;

        this.proccessType = ConstUtil.PROCCESS_TYPE_INSERT;
    }

    /**
     *
     * MCourse名存在チェック
     */
    public void courseNameChanged() {
        FacesMessage fm = null;

        boolean result = this.checkCourseNameExists();

        fm = new FacesMessage(FacesMessage.SEVERITY_INFO, ConstUtil.MSG_SUMMARY_INFO, "このコース名は使用できます");
        if (result) {
            if (this.isProccessUpdate() && this._lastCourseName.equals(this.editingCourse.getCouName())) {
            } else {
                fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, ConstUtil.MSG_SUMMARY_ERROR, "このコース名はもう既に存在している");
            }
        }

        this.addMessage("myForm:txtCourseName", fm);

    }

    /**
     *
     * Insert MCourse
     */
    public void commitData() {
        FacesMessage fm = null;
        CommitResult result = null;

        if (this.isProccessSelect()) {
            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, ConstUtil.MSG_SUMMARY_ERROR, "新規、編集または削除を選択してください");

            this.addMessage(fm);

            return;
        }

        if (!this.isProccessDelete()) {
            fm = new FacesMessage(FacesMessage.SEVERITY_INFO, ConstUtil.MSG_SUMMARY_INFO, "このコース名は使用できます");
            if (this.checkCourseNameExists()) {
                if (this.isProccessUpdate() && this._lastCourseName.equals(this.editingCourse.getCouName())) {
                } else {
                    fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, ConstUtil.MSG_SUMMARY_ERROR, "このコース名はもう既に存在している");

                    this.addMessage(fm);

                    return;
                }
            }
            this.editingCourse.setUpdDate(new Date());

        }

//        if (this.checkCourseNameExists()) {
//            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, ConstUtil.MSG_SUMMARY_ERROR, "このコース名はもう既に存在している");
//            this.addMessage(fm);
//
//            return;
//        }
        switch (proccessType) {
            case ConstUtil.PROCCESS_TYPE_INSERT:
                result = conService.persistCourse(this.editingCourse);
                break;
            case ConstUtil.PROCCESS_TYPE_UPDATE:
                result = conService.mergeCourse(this.editingCourse);
                break;
            case ConstUtil.PROCCESS_TYPE_DELETE:
                result = conService.removeCourse(this.editingCourse);
                break;
        }

        if (result.isSuccess()) {

            if (this.isProccessInsert()) {
                this.courses.add(editingCourse);

            }

            if (this.isProccessUpdate() || this.isProccessDelete()) {
                this.courses = conService.findCourses();
            }

            this.clearPage();

        }

        fm = result.creatFacesMessage();

        if (fm != null) {
            this.addMessage(fm);
        }
    }

    /**
     * コース名存在チェック
     *
     * @return
     */
    private boolean checkCourseNameExists() {

        long result = conService.checkExistsCourseName(this.editingCourse.getCouName());

        return result > 0;
    }

    /**
     *
     * ページをクリアする
     */
    public void clearPage() {

        this.editingCourse = new MCourse();

        this.pageStatus = ConstUtil.PAGE_STATUS.INIT;

        this.proccessType = ConstUtil.PROCCESS_TYPE_SELECT;

        this._lastCourseName = "";

    }

    /**
     *
     * MCourse Select&&編集
     *
     * @param mc
     * @param processType
     */
    public void rowSelect(MCourse mc, String processType) {

        this.editingCourse = mc;

        this.proccessType = processType;

        this.pageStatus = ConstUtil.PAGE_STATUS.EDITING;

        this._lastCourseName = this.editingCourse.getCouName();

    }

//    public void deleteCourse() {
//        FacesMessage fm = null;
//        CommitResult result = null;
//
//
//        result = conService.removeCourse(this.editingCourse);
//
//        if (result.isSuccess()) {
//
//            this.courses.remove(editingCourse);
//
//            this.clearPage();
//
//        }
//
//        fm = result.creatFacesMessage();
//
//        if (fm != null) {
//            this.addMessage(fm);
//        }
//    }
}
