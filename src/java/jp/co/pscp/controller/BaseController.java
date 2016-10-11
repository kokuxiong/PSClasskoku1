/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.pscp.controller;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 雄
 */
public abstract class BaseController implements Serializable{
    protected FacesContext getFacesContext(){
        return FacesContext.getCurrentInstance();
    }
    protected ExternalContext getExternalContext(){
        return this.getFacesContext().getExternalContext();
    }
    protected ServletContext getServletContext(){
        return (ServletContext)this.getExternalContext().getContext();
    }
    protected HttpServletResponse getHttpServletResponse(){
        return (HttpServletResponse)this.getExternalContext().getResponse();
    }
    protected HttpServletRequest getHttpServletRequest(){
        return (HttpServletRequest)this.getExternalContext().getRequest();
    }
    protected void addMessage(FacesMessage fm){
        this.addMessage(null,fm);
    }
    protected void addMessage(String msgId,FacesMessage fm){
        this.getFacesContext().addMessage(msgId, fm);
    }
}
