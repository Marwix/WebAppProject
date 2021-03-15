package com.group3.Assignment30.controller;

import java.util.Iterator;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;



public class SessionContextController extends FacesContext{
    
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

    public void setCurrentInstances(FacesContext facesContext){
       FacesContext.setCurrentInstance(facesContext);
    }

    @Override
    public Application getApplication() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<String> getClientIdsWithMessages() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ExternalContext getExternalContext() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FacesMessage.Severity getMaximumSeverity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<FacesMessage> getMessages() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<FacesMessage> getMessages(String clientId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RenderKit getRenderKit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getRenderResponse() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getResponseComplete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResponseStream getResponseStream() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setResponseStream(ResponseStream responseStream) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResponseWriter getResponseWriter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setResponseWriter(ResponseWriter responseWriter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UIViewRoot getViewRoot() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setViewRoot(UIViewRoot root) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addMessage(String clientId, FacesMessage message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void release() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void renderResponse() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void responseComplete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
