package com.company.demo

import grails.transaction.Transactional
import org.springframework.validation.FieldError

@Transactional
class AnagraphicService {
    static transactional = false
    Anagraphic get(Long id) {
        Anagraphic.withTransaction {
            Anagraphic.get(id)
        }
    }
    
    def list() {
        Anagraphic.withTransaction {
            Anagraphic.list()
        }
    }
    def list(int max, int offset) {
        log.info "[list] max = ${max}, offset = ${offset}"
        Anagraphic.withTransaction {
            Anagraphic.list(max: max, offset: offset)
        }
    }
    def list(int max, int offset, String sort, String order) {
        log.info "[list] max = ${max}, offset = ${offset}, sort = ${sort}, order = ${order}"
        Anagraphic.withTransaction {
            Anagraphic.list(max: max, offset: offset, sort: sort, order: order)
        }
    }
    
    def delete(Long id) {
        def anagraphic = Anagraphic.get(id)
        anagraphic.delete(flush: true)
    }
    
    List<FieldError> save(Anagraphic anagraphic) {
        def errors = null
        if (anagraphic.save(flush: true)) {
            return null
        } else {
            anagraphic.errors.allErrors
        }
    }
    
    def filter(Map filters, int max, int offset) {
        def c = Anagraphic.createCriteria()
        c.list (max: max, offset: offset) {
            and {
                filters.each { k, v ->
                    ilike(k, "%${v}%")
                }
            }
        }
    }
    
    def filter(Map filters, int max, int offset, String sort, String sortOrder) {
        def c = Anagraphic.createCriteria()
        c.list (max: max, offset: offset) {
            and {
                filters.each { k, v ->
                    ilike(k, "%${v}%")
                }
            }
            order(sort, sortOrder)
        }
    }
}

