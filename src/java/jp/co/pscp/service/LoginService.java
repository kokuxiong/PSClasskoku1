/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.pscp.service;

import jp.co.pscp.entity.LoginUser;


public interface LoginService {
    /**
 *@param loginId
 * @param password
 * @author 雄
 */

    
    LoginUser findLoginUser(boolean isTeacher,String loginId,String password);
}
