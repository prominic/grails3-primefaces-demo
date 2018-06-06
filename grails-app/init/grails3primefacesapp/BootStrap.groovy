package grails3primefacesapp
import com.company.demo.Anagraphic

class BootStrap {

    def init = { servletContext ->
        log.info "BootStrap demo"

        def t1 = System.currentTimeMillis()
        def input
        try {
            if(Anagraphic.list().size()<=0) {
                input = servletContext.getResourceAsStream("/WEB-INF/resources/data.csv")
                // input will be null when using "run-command <command>", so make this robust
                input?.eachLine { line ->
                    def a = new Anagraphic(firstName: line.split(";")[0], surname: line.split(";")[1])
                    //   println a.firstName
                    if (!a.save(flush: true)) {
                        println a.errors
                    }
                }
            }
        } finally {
            if (input)
                input.close()
        }
        def t2 = System.currentTimeMillis()
        def delay = t2 - t1
        log.info "delay = ${delay} [mills]"
    }
    def destroy = {
    }
}
