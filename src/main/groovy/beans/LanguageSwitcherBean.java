package com.company.demo.beans;

import grails.plugins.primefaces.MessageSourceBean;
import java.util.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean
public class LanguageSwitcherBean {

    @ManagedProperty(value = "#{message}")
    private MessageSourceBean message;
     
    private String selectedLanguage;
    private List<String> languages;

    public MessageSourceBean getMessage() {
        return message;
    }

    public void setMessage(MessageSourceBean message) {
        this.message = message;
    }

    public String getSelectedLanguage() {
        selectedLanguage = message.getLocale().getLanguage();
        return selectedLanguage;
    }

    public void setSelectedLanguage(String selectedLanguage) {
        message.setLocale(selectedLanguage);
        this.selectedLanguage = selectedLanguage;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public List<String> getLanguages() {
        if (languages == null) {
            languages = new ArrayList<String>() {
                {
                    add("it");
                    add("en");
                }
            };
        }
        return languages;
    }

}