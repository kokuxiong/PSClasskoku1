/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.pscp.converter;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import jp.co.pscp.entity.MStudent;

/**
 *
 * @author 雄
 */
@FacesConverter(forClass = MStudent.class, value = "studentConverter")
public class StudentConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        System.out.println(value);

        if (value == null || "".equals(value)) {
            return null;
        }

        //获取当前下拉列表控件
        HtmlSelectOneMenu menu = (HtmlSelectOneMenu) component;

        //获取下拉列表的子控件
        List<UIComponent> children = menu.getChildren();
        
        UISelectItems sis=null;
        
        for(UIComponent uiCom:children){
            if(uiCom instanceof UISelectItems){
                sis=(UISelectItems)uiCom;
                break;
            }
        }

        if(sis==null){
            return null;
        }
        
        List<MStudent> students=(List<MStudent>)sis.getValue();
        
        
        if(students==null || students.isEmpty()){
            return null;
        }
        
        for(MStudent stu:students){
            if(value.equals(stu.getStuId())){
                return stu;
            }
        }
        
        return null;

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            System.out.println(value);
            return ((MStudent) value).getStuId();
        }
        return "";
    }

}
