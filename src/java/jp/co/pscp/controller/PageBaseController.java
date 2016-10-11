/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.pscp.controller;

import jp.co.pscp.util.ConstUtil;
import jp.co.pscp.util.ConstUtil.PAGE_STATUS;

/**
 *
 * @author 雄
 */
public class PageBaseController extends BaseController {

    protected String proccessType;

    protected PAGE_STATUS pageStatus;

    public PageBaseController() {
        this.proccessType = ConstUtil.PROCCESS_TYPE_SELECT;
        this.pageStatus = PAGE_STATUS.INIT;
    }

    public boolean isProccessInsert() {
        return ConstUtil.PROCCESS_TYPE_INSERT.equals(this.proccessType);
    }

    public boolean isProccessUpdate() {
        return ConstUtil.PROCCESS_TYPE_UPDATE.equals(this.proccessType);
    }

    public boolean isProccessSelect() {
        return ConstUtil.PROCCESS_TYPE_SELECT.equals(this.proccessType);
    }

    public boolean isProccessDelete() {
        return ConstUtil.PROCCESS_TYPE_DELETE.equals(this.proccessType);
    }

    public PAGE_STATUS getPageStatus() {
        return pageStatus;
    }

    public boolean isPageStatusInit() {
        return this.pageStatus == PAGE_STATUS.INIT;
    }

    public boolean isPageStatusEditing() {
        return this.pageStatus == PAGE_STATUS.EDITING;
    }

    public boolean isPageStatusReadOnly() {
        return this.pageStatus == PAGE_STATUS.READONLY;
    }

    public String getProccessType() {
        return proccessType;
    }

    public void setProccessType(String proccessType) {
        this.proccessType = proccessType;
    }

    public String getProcessText() {
        String message = "";
        switch (this.proccessType) {
            case ConstUtil.PROCCESS_TYPE_INSERT:
                message = "新規";
                break;
            case ConstUtil.PROCCESS_TYPE_UPDATE:
                message = "更新";
                break;
            case ConstUtil.PROCCESS_TYPE_DELETE:
                message = "削除";
                break;
            case ConstUtil.PROCCESS_TYPE_SELECT:
                message = "検索";
                break;
        }
        return message;
    }
}
