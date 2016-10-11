/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.pscp.service.impl;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jp.co.pscp.util.ConstUtil;

/**
 *工场写到父类了
 * @author 雄
 */
public class BaseService implements Serializable {
    @PersistenceContext(unitName = ConstUtil.UNIT_NAME)
    protected EntityManager em;

}
