/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.User;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ELOCK2
 */
@SessionScoped
@ManagedBean(name = "UserController", eager = true)
public class UserController implements Serializable{

    private User user;
  
    
    public String login(){
       if (this.user.getName().equals("user") && this.user.getPassword().equals("pass")) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("valid_user", this.user);
            this.getUser().setAuth(true);
            return "/admin/blogs?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Hatali kullanici adi veya sifre")); 
            return "/login";
        }
    }
    public String logout(){
        return "index";
    }

    public User getUser() {
        if (this.user == null) {
            this.user = new User();
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

     
}