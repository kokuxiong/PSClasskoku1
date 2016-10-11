package jp.co.pscp.controller;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import jp.co.pscp.entity.LoginUser;
import jp.co.pscp.service.LoginService;
import jp.co.pscp.util.ConstUtil;
import jp.co.pscp.util.SecurityUtil;

/**
 *
 * @author 雄
 */
//实现可序列化接口
@Named("c_login")
@SessionScoped
public class LoginController extends PageBaseController implements Serializable {

    private String loginId;
    private String password;
    private LoginUser loginUser;
    private String setLoginType;
    private boolean hasLogined;
    @EJB
    private LoginService loginService;

    public boolean isHasLogined() {
        return hasLogined;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginUser getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(LoginUser loginUser) {
        this.loginUser = loginUser;
    }

    public LoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    public String getSetLoginType() {
        return setLoginType;
    }

    public void setSetLoginType(String setLoginType) {
        this.setLoginType = setLoginType;
    }

    public void setHasLogined(boolean hasLogined) {
        this.hasLogined = hasLogined;
    }

    //加载第一个程序
    @PostConstruct
    public void init() {
        //login画面的登陆用户类型，默认是学生
        this.setLoginType = ConstUtil.LOGIN_TYPE_S;
        //默认是老师
        //this.setLoginType = ConstUtil.LOGIN_TYPE_T;
    }

    /**
     *
     *
     *
     */
    public boolean isTeacherLogin() {
        return ConstUtil.LOGIN_TYPE_T.equals(this.setLoginType);
    }

    public String logout() {

        this.hasLogined = false;
        this.loginUser = new LoginUser();
        return ConstUtil.URI_INDEX.concat("?faces-redirect=true");
    }

    /**
     *
     * login syori
     *
     * @return Index.xhtml
     */
    public String doLogin() {

        FacesMessage fm = null;

        String convertedPassword = SecurityUtil.MD5(this.password).toLowerCase();

        this.loginUser = loginService.findLoginUser(isTeacherLogin(), loginId, convertedPassword);

        if (this.loginUser != null && this.loginUser.getId().length() > 0) {
            this.hasLogined = true;
            return ConstUtil.URI_INDEX.concat("?faces-redirect=true");
        } else {
            this.hasLogined = false;
            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, ConstUtil.MSG_SUMMARY_ERROR, "メールまたはパスワードが間違っている。");

            this.addMessage("myFormLogin:btnCmdBtn", fm);

        }

        return null;
    }

}
