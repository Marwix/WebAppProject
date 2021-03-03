package com.group3.Assignment30.controller;

import com.group3.Assignment30.views.LoginBackingBean;
import java.io.IOException;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import lombok.Data;

@Data
@Named
@SessionScoped
public class LogoutController implements Serializable{
    
    @Inject
    private LoginBackingBean loginBackingBean;
    
    private int activeUserID;
    
    private SessionContextController sessionContextController = SessionContextController.getInstance();
    
    public void onLogout() throws IOException{
        activeUserID = (int) sessionContextController.getAttribu("user_id");
        sessionContextController.encerrarSessao();
        
        loginBackingBean.setLoggedin(false);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        System.out.println("Hej");
        
        
    }
}
