package com.company.demo.beans

import grails.util.Holders
import org.apache.log4j.Logger
import javax.annotation.PostConstruct

import com.company.demo.Anagraphic
import com.company.demo.AnagraphicService

import grails.plugins.primefaces.GrailsService
import grails.plugins.primefaces.MessageSourceBean

import javax.faces.bean.ManagedBean
import javax.faces.bean.ManagedProperty
import javax.faces.bean.ViewScoped
import javax.faces.context.FacesContext

import org.primefaces.event.SelectEvent
import org.primefaces.model.LazyDataModel

import org.springframework.core.SpringVersion
import org.springframework.validation.FieldError
import javax.persistence.EntityManagerFactory

import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.RequestAttributes


@ManagedBean(name = "anagraphicMB")
@ViewScoped
public class AnagraphicManagedBean implements Serializable {

    Logger log = Logger.getLogger(AnagraphicManagedBean.class)
        
    @PostConstruct
    public void init() {
		println "---> @PostConstruct --- " + new java.util.Date().toString()
        anagraphics = new LazyAnagraphicDataModel()
    }
    
    @ManagedProperty(value = "#{message}")
    MessageSourceBean message
    
    //@GrailsService(name = "anagraphicService")
    def anagraphicService=Holders.grailsApplication.mainContext.getBean 'anagraphicService'
    
    Anagraphic anagraphic
    LazyDataModel<Anagraphic> anagraphics

    public void save() {
        log.info(anagraphic)        
        boolean updated = (anagraphic.id != null)
        List<FieldError> errors = getAnagraphicService().save(anagraphic)
        if (errors == null) {
            if (updated == true) {
                message.infoPF("com.company.demo.Anagraphic.updated.message", null)
            } else {
                message.infoPF("com.company.demo.Anagraphic.created.message", null)
            }
            reset()
        } else {
            for (FieldError error : errors) {
                message.errorMessagePF("", message.getErrorMessage(error))
            }
        }
    }
    
    public void reset() {
        log.info "reset"
        anagraphic = new Anagraphic()
    }
    
    public void delete() {
        def id=RequestContextHolder.getRequestAttributes().getParams().get("id")
        Long longId=Long.parseLong(id)
        getAnagraphicService().delete(longId)
        message.infoPF("com.company.demo.Anagraphic.deleted.message", null)
    }

    public void onRowSelect(SelectEvent event) {
        Long id =((Anagraphic) event.getObject()).getId()
        log.info("id = " + id)
        anagraphic = anagraphicService.get(id)
    }
	

}

