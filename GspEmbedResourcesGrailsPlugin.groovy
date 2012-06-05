class GspEmbedResourcesGrailsPlugin {
    // the plugin version
    def version = "0.1"
    
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.0.1 > *"
    
    // the other plugins this plugin depends on the resources plugin
    def dependsOn = [resources:'1.0.RC3 > *']
    
    def loadAfter = ['resources']
    
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp",
        "web-app/css/**/*.*",
        "web-app/js/**/*.*"        
    ]

    // TODO Fill in these fields
    def title = "GSP Embed Resources Plugin" // Headline display name of the plugin
    def author = "Joseph Faisal Nusairat"
    def authorEmail = "jnusairat@integrallis.com"
    def description = '''\

This plugin allows for embeding of dynamic content directly inside the JS/CSS files that are colocated
inside the web-app/ directory.

This way instead of having to call via a GSP you can add application settings as well as call resource() so that images that are references will be given proper resource references directly in the CSS file.

ie :
.twistee-closed {
	background-image: url("${resource(dir : 'images', file : 'd.png')}");

'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/gsp-embed-resources"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.grails-plugins.codehaus.org/browse/grails-plugins/" ]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
