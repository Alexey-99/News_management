package com.mjc.school.config.spring;

import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * The type Spring dispatcher.
 */
public class SpringDispatcher {
        //extends AbstractAnnotationConfigDispatcherServletInitializer {
//    @Override
//    protected Class<?>[] getRootConfigClasses() {
//        return new Class[]{SpringConfig.class};
//    }
//
//    @Override
//    protected Class<?>[] getServletConfigClasses() {
//        return new Class<?>[]{SpringConfig.class};
//    }
//
//    @Override
//    protected String[] getServletMappings() {
//        return new String[]{"/"};
//    }
//
//    @Override
//    protected DispatcherServlet createDispatcherServlet(
//            WebApplicationContext servletAppContext) {
//        System.out.println("________________________________HELLO!!!____________________________________________");
//        DispatcherServlet ds = new DispatcherServlet(servletAppContext);
//        ds.setThrowExceptionIfNoHandlerFound(true);
//        return ds;
//    }
}