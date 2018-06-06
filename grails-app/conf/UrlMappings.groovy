class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(uri:"/faces/anagraphic/home.xhtml")

        "500"(view:'/error')
    }

}
