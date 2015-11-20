package crm

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ManagedPropertyController {

    static allowedMethods = [save: "POST", update: "PUT"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ManagedProperty.list(params), model:[managedPropertyCount: ManagedProperty.count()]
    }

    def show(ManagedProperty managedProperty) {
        respond managedProperty
    }

    def create() {
        respond new ManagedProperty(params)
    }
	def addEditImages(ManagedProperty managedProperty){
		redirect(controller:'upload', action:'images', params: [obj:'property', oid: managedProperty.id])
	}
    @Transactional
    def save(ManagedProperty managedProperty) {
        if (managedProperty == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (managedProperty.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond managedProperty.errors, view:'create'
            return
        }

        managedProperty.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'managedProperty.label', default: 'ManagedProperty'), managedProperty.id])
                redirect managedProperty
            }
            '*' { respond managedProperty, [status: CREATED] }
        }
    }

    def edit(ManagedProperty managedProperty) {
        respond managedProperty
    }

    @Transactional
    def update(ManagedProperty managedProperty) {
        if (managedProperty == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (managedProperty.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond managedProperty.errors, view:'edit'
            return
        }

        managedProperty.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'managedProperty.label', default: 'ManagedProperty'), managedProperty.id])
                redirect managedProperty
            }
            '*'{ respond managedProperty, [status: OK] }
        }
    }

    /*@Transactional
    def delete(ManagedProperty managedProperty) { //must add cascade delete for building and so on..

        if (managedProperty == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        managedProperty.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'managedProperty.label', default: 'ManagedProperty'), managedProperty.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }*/

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'managedProperty.label', default: 'ManagedProperty'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}