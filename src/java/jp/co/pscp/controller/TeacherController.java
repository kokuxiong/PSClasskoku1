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
import jp.co.pscp.entity.MTeacher;
import jp.co.pscp.service.TeacherService;
import jp.co.pscp.util.CommitResult;
import jp.co.pscp.util.ConstUtil;
import jp.co.pscp.util.SecurityUtil;

/**
 *
 * @author 雄
 */
@Named("c_teacher")
@ViewScoped
public class TeacherController extends PageBaseController{
    private List<MTeacher> teachers;
    private MTeacher editingTeacher;
    
    @EJB
    private TeacherService conTeacher;
    private String _lastTeacherEmail;

    public List<MTeacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<MTeacher> teachers) {
        this.teachers = teachers;
    }

    public MTeacher getEditingTeacher() {
        return editingTeacher;
    }

    public void setEditingTeacher(MTeacher editingTeacher) {
        this.editingTeacher = editingTeacher;
    }
    
    @PostConstruct
    void init(){
        this.teachers = conTeacher.findTeachers();
        this.clearPage();
    }
    
    public void rowSelectStudent(MTeacher mt) {

        this.editingTeacher = mt;

        this.proccessType = ConstUtil.PROCCESS_TYPE_UPDATE;

        this.pageStatus = ConstUtil.PAGE_STATUS.EDITING;

        this._lastTeacherEmail = this.editingTeacher.getEmail();

    }

    public void startInsert() {
        this.editingTeacher = new MTeacher();

        this.pageStatus = ConstUtil.PAGE_STATUS.EDITING;

        this.proccessType = ConstUtil.PROCCESS_TYPE_INSERT;
    }

    public void clearPage() {

        this.editingTeacher = new MTeacher();

        this.pageStatus = ConstUtil.PAGE_STATUS.INIT;

        this.proccessType = ConstUtil.PROCCESS_TYPE_SELECT;

        this._lastTeacherEmail = "";

    }

    /**
     * メール存在チェック
     *
     * @return
     */
    private boolean checkEmailExists() {

        long result = conTeacher.checkExistsEmail(this.editingTeacher.getEmail());

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
            if (this.isProccessUpdate() && this._lastTeacherEmail.equals(this.editingTeacher.getEmail())) {
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
            if (this.isProccessUpdate() && this._lastTeacherEmail.equals(this.editingTeacher.getEmail())) {
            } else {
                fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, ConstUtil.MSG_SUMMARY_ERROR, "このメールはもう既に存在している");

                this.addMessage(fm);

                return;
            }
        }

        //密码暗号化
        String convertedPassword = SecurityUtil.MD5(this.editingTeacher.getPassword()).toLowerCase();
        this.editingTeacher.setPassword(convertedPassword);
        //设置更新年月
        this.editingTeacher.setUpdDate(new Date());
        
        //如果是插入把版本设为初始值
        if (this.isProccessInsert()) {

            this.editingTeacher.setVersion(0);

        }

        switch (proccessType) {
            case ConstUtil.PROCCESS_TYPE_INSERT:
                result = conTeacher.persistTeacher(this.editingTeacher);
                break;
            case ConstUtil.PROCCESS_TYPE_UPDATE:
                result = conTeacher.mergeTeacher(this.editingTeacher);
                break;
            case ConstUtil.PROCCESS_TYPE_DELETE:
                result = conTeacher.removeTeacher(this.editingTeacher);
                break;
        }

        if (result.isSuccess()) {

//            if (this.isProccessInsert()) {
//                this.students.add(editingStudent);
//
//            }
            if (this.isProccessInsert()) {
                this.teachers = conTeacher.findTeachers();

            }

            if (this.isProccessUpdate()) {
                this.teachers = conTeacher.findTeachers();
            }

            this.clearPage();

        }

        fm = result.creatFacesMessage();

        if (fm != null) {
            this.addMessage(fm);
        }

    }
}
