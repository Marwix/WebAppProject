package com.group3.Assignment30.controller;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;


public class SessionContextController {
    
    private static SessionContextController instance;
    
    public static SessionContextController getInstance(){
        if(instance == null){
            instance = new SessionContextController();
        }
        return instance;
    }
    
    private SessionContextController(){
        
    }
    
    private ExternalContext currentExternalContext(){
        if(FacesContext.getCurrentInstance() == null){
            throw new RuntimeException("FacesContext cant be called outside of a HTTP Request");
        }else
            return FacesContext.getCurrentInstance().getExternalContext();
           
    }
    public void encerrarSessao(){
        currentExternalContext().invalidateSession();
    }
    
    public Object getAttribu(String name){
        return currentExternalContext().getSessionMap().get(name);
    }
    public void setAttribute(String name, Object ValueObject){
        currentExternalContext().getSessionMap().put(name, ValueObject);
    }
    
}
