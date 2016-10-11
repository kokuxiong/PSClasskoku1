/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.pscp.util;

import javax.ejb.TransactionRequiredLocalException;
import javax.faces.application.FacesMessage;
import javax.persistence.OptimisticLockException;

/**
 *
 * @author 雄
 */
public class CommitResult {

    private boolean success;
    private boolean courseIsSelected;

    private FacesMessage _fm;

    private String successMsg;
    private String failMsg;

    public CommitResult(String processType) {
        switch (processType) {
            case ConstUtil.PROCCESS_TYPE_INSERT:
                this.successMsg = "新規成功";
                this.failMsg = "新規失敗";
                break;
            case ConstUtil.PROCCESS_TYPE_UPDATE:
                this.successMsg = "更新成功";
                this.failMsg = "更新失敗";
                break;
            case ConstUtil.PROCCESS_TYPE_DELETE:
                this.successMsg = "削除成功";
                this.failMsg = "削除失敗";
                break;
        }
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public String getFailMsg() {
        return failMsg;
    }

    public void setFailMsg(String failMsg) {
        this.failMsg = failMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isCourseIsSelected() {
        return courseIsSelected;
    }

    public void setCourseIsSelected(boolean courseIsSelected) {
        this.courseIsSelected = courseIsSelected;
    }

    public FacesMessage creatFacesMessage() {

        if (this.success) {
            this._fm = new FacesMessage(FacesMessage.SEVERITY_INFO, ConstUtil.MSG_SUMMARY_INFO, successMsg);
        } else {
            if (this.courseIsSelected) {
                this._fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, ConstUtil.MSG_SUMMARY_ERROR, ConstUtil.DEL_FAIL_MESSAGES);
            } else {
                this._fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, ConstUtil.MSG_SUMMARY_ERROR, failMsg);
            }
        }
        return _fm;
    }

    /**
     * 例外分析
     *
     * @param e
     */
    public void anylizeException(Exception e) {
//        //ロールバックを引き起こす例外であるかどうか
//        if (e.getCause() instanceof TransactionRequiredLocalException) {
//            TransactionRequiredLocalException trExp = (TransactionRequiredLocalException) e.getCause();
//
//            //排他例外の場合はそれなりの処理をする。
//            if (trExp.getCausedByException() instanceof OptimisticLockException) {
//                this.failMsg = "既に他の端末に更新されていました。";
//            }
//        }

        //排他例外の場合はそれなりの処理をする
        if (e instanceof OptimisticLockException) {
            this.failMsg = "すでに他の端末に更新されていました。（一度取消して再編集してください）";
        }

    }
}
