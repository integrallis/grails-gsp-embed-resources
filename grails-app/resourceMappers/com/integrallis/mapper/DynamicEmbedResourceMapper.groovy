package com.integrallis.mapper

import org.grails.plugin.resource.mapper.MapperPhase

import grails.gsp.PageRenderer 

import static org.apache.commons.io.FileUtils.copyInputStreamToFile

// Move these imports when it is seperated out
import org.springframework.web.context.request.RequestContextHolder
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.codehaus.groovy.grails.web.pages.FastStringWriter
//import grails.gsp.PageRenderResponse
/*import javax.servlet.http.HttpServletResponse
import javax.servlet.http.Cookie
import javax.servlet.ServletOutputStream
import javax.servlet.http.HttpSession
import javax.servlet.AsyncContext
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse*/
//import javax.servlet.http.Part
/*import javax.servlet.ServletInputStream
import org.apache.commons.collections.iterators.IteratorEnumeration
import java.util.concurrent.ConcurrentHashMap
import javax.servlet.RequestDispatcher
import java.security.Principal
import javax.servlet.DispatcherType
import javax.servlet.ServletContext
import javax.servlet.http.HttpServletRequest*/

import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse

/**
 * This will go thorugh and dynamically update the CSS/JS files
 * 
 */
class DynamicEmbedResourceMapper {

    def phase = MapperPhase.MUTATION
    
    PageRenderer groovyPageRenderer
    
    static defaultIncludes = [
        '**/*.css',
        '**/*.js',        
    ]
    
    /**
     * Rename the file to include the version at the end of the file.
     */
    def map(resource, config) {
        def processedStr = parseFile(resource.originalUrl, resource.processedFile)

        if (processedStr
            && processedStr != resource.processedFile.text) {
                log.debug "Replace Contents : ${resource.originalUrl}"                
                
                // Now replace the file with the details from the processed string
                def source = new ByteArrayInputStream(processedStr.getBytes("UTF8"));
        
                copyInputStreamToFile(source, resource.processedFile) 
        
                // This will update the URL for the actual file
                resource.updateActualUrlFromProcessedFile()
        }
    }
    
    
    /**
     * Will parse the css/js file for any dymanic data inside.
     */
    def parseFile(uri, file) {
        String contents
        try {
            //TODO :  Needs to be moved to an initialization
            pageRendererEnhancement()
            contents = groovyPageRenderer.renderFile(uri, file)
        } catch (Exception e) {
            log.error("Error processing : $uri : ${e.getMessage()}", e)
        }
        contents
    }
    
    def pageRendererEnhancement() {
        groovyPageRenderer.metaClass.renderFile = {uri, sourceFile, model = [:] ->
            def writer = new FastStringWriter()
            
            def oldRequestAttributes = RequestContextHolder.getRequestAttributes()
            try {
//                def webRequest = new GrailsWebRequest(new PageRenderRequest(uri),
                def webRequest = new GrailsWebRequest(new MockHttpServletRequest(),
                      //new PageRenderResponse(writer instanceof PrintWriter ? writer : new PrintWriter(writer)),
                      new MockHttpServletResponse(),
                      servletContext, applicationContext)
                RequestContextHolder.setRequestAttributes(webRequest)
                def template = delegate.templateEngine.createTemplate(sourceFile)
                if (template != null) {
                    template.make(model).writeTo(writer)
                }
            } finally {
                RequestContextHolder.setRequestAttributes(oldRequestAttributes)
            }
            return writer.toString()            
        }
    }
}