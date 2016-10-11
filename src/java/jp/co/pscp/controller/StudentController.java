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
import jp.co.pscp.entity.MStudent;
import jp.co.pscp.service.StudentService;
import jp.co.pscp.util.CommitResult;
import jp.co.pscp.util.ConstUtil;
import jp.co.pscp.util.SecurityUtil;

/**
 *
 * @author 雄
 */
@Named("c_student")
@ViewScoped
public class StudentController extends PageBaseController {

    private List<MStudent> students;
    private MStudent editingStudent;

    @EJB
    private StudentService stuService;
    private String _lastStudentEmail;

    public List<MStudent> getStudents() {
        return students;
    }

    public void setStudents(List<MStudent> students) {
        this.students = students;
    }

    public MStudent getEditingStudent() {
        return editingStudent;
    }

    public void setEditingStudent(MStudent editingStudent) {
        this.editingStudent = editingStudent;
    }

    @PostConstruct
    public void init() {

        this.students = stuService.findStudents();

        this.clearPage();
    }

    public void rowSelectStudent(MStudent ms) {

        this.editingStudent = ms;

        this.proccessType = ConstUtil.PROCCESS_TYPE_UPDATE;

        this.pageStatus = ConstUtil.PAGE_STATUS.EDITING;

        this._lastStudentEmail = this.editingStudent.getEmail();

    }

    public void startInsert() {
        this.editingStudent = new MStudent();

        this.pageStatus = ConstUtil.PAGE_STATUS.EDITING;

        this.proccessType = ConstUtil.PROCCESS_TYPE_INSERT;
    }

    public void clearPage() {

        this.editingStudent = new MStudent();

        this.pageStatus = ConstUtil.PAGE_STATUS.INIT;

        this.proccessType = ConstUtil.PROCCESS_TYPE_SELECT;

        this._lastStudentEmail = "";

    }

    /**
     * メール存在チェック
     *
     * @return
     */
    private boolean checkEmailExists() {

        long result = stuService.checkExistsEmail(this.editingStudent.getEmail());

        return result > 0;
    }

    /**
     *
     * メールは既に存在しているかどうかをチェックする。
     */
    public void studentEmailChanged() {
        FacesMessage fm = null;

        boolean result = this.checkEmailExists();

        fm = new FacesMessage(FacesMessage.SEVERITY_INFO, ConstUtil.MSG_SUMMARY_INFO, "このメールは使用できます");
        if (result) {
            if (this.isProccessUpdate() && this._lastStudentEmail.equals(this.editingStudent.getEmail())) {
            } else {
                fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, ConstUtil.MSG_SUMMARY_ERROR, "このメールはもう既に存在している");
            }
        }

        this.addMessage("myForm:txtEmail", fm);

    }

    public void commitData() {

        FacesMessage fm = null;
        CommitResult result = null;

        fm = new FacesMessage(FacesMessage.SEVERITY_INFO, ConstUtil.MSG_SUMMARY_INFO, "このメールは使用できます");
        if (this.checkEmailExists()) {
            if (this.isProccessUpdate() && this._lastStudentEmail.equals(this.editingStudent.getEmail())) {
            } else {
                fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, ConstUtil.MSG_SUMMARY_ERROR, "このメールはもう既に存在している");

                this.addMessage(fm);

                return;
            }
        }

        //密码暗号化
        String convertedPassword = SecurityUtil.MD5(this.editingStudent.getPassword()).toLowerCase();
        this.editingStudent.setPassword(convertedPassword);
        //设置更新年月
        this.editingStudent.setUpdDate(new Date());
        
        //如果是插入把版本设为初始值
        if (this.isProccessInsert()) {

            this.editingStudent.setVersion(0);

        }

        switch (proccessType) {
            case ConstUtil.PROCCESS_TYPE_INSERT:
                result = stuService.persistStudent(this.editingStudent);
                break;
            case ConstUtil.PROCCESS_TYPE_UPDATE:
                result = stuService.mergeStudent(this.editingStudent);
                break;
            case ConstUtil.PROCCESS_TYPE_DELETE:
                result = stuService.removeStudent(this.editingStudent);
                break;
        }

        if (result.isSuccess()) {

//            if (this.isProccessInsert()) {
//                this.students.add(editingStudent);
//
//            }
            if (this.isProccessInsert()) {
                this.students = stuService.findStudents();

            }

            if (this.isProccessUpdate()) {
                this.students = stuService.findStudents();
            }

            this.clearPage();

        }

        fm = result.creatFacesMessage();

        if (fm != null) {
            this.addMessage(fm);
        }

    }

}
