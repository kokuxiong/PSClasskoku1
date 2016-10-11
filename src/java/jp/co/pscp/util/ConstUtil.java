/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.pscp.util;

/**
 *
 * @author 雄
 */
public class ConstUtil {
    //改名时右键，重命名
    //多使用定数

    public enum PAGE_STATUS {

        INIT, EDITING, READONLY
    }

    public static final String URI_INDEX = "index.xhtml";
    public static final String URI_LOGIN = "login.xhtml";
    public static final String URI_COURSE = "course.xhtml";
    public static final String RESOURCE = "javax.faces.resource";

    public static final String LOGIN_TYPE_S = "1";
    public static final String LOGIN_TYPE_T = "2";
    public static final String UNIT_NAME = "PSClasskokuPU";
    public static final String REQUIRED_FLG_YES = "1";
    public static final String REQUIRED_FLG_NO = "0";
    public static final String REQUIRED_FLG_MAN = "1";
    public static final String REQUIRED_FLG_WOMAN = "0";

    /**
     * プロセス種別：挿入
     */
    public static final String PROCCESS_TYPE_INSERT = "I";
    /**
     * プロセス種別：更新
     */
    public static final String PROCCESS_TYPE_UPDATE = "U";
    /**
     * プロセス種別：検索
     */
    public static final String PROCCESS_TYPE_SELECT = "S";
    /**
     * プロセス種別：削除
     */
    public static final String PROCCESS_TYPE_DELETE = "D";

    /**
     * MessageSummary
     */
    public static final String MSG_SUMMARY_INFO = "INFO";
    public static final String MSG_SUMMARY_WARNING = "警告";
    public static final String MSG_SUMMARY_ERROR = "エラー";
    public static final String MSG_SUMMARY_FATAL = "致命的";
    public static final String DEL_FLG_NO="0";
    public static final String DEL_FAIL_MESSAGES = "削除失敗　該当コースを選択している学生がいる";

}
