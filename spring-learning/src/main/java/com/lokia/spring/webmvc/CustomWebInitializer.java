package com.lokia.spring.webmvc;

import com.lokia.spring.webmvc.config.RootAppConfig;
import com.lokia.spring.webmvc.config.DispatcherServletConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * @author gushu
 * @data 2018/7/26
 */
public class CustomWebInitializer implements WebApplicationInitializer {


    private final static String[] basePackages= {"com.lokia.spring.webmvc"};

//    // Create the 'root' Spring application context
//    AnnotationConfigWebApplicationContext rootContext =
//            new AnnotationConfigWebApplicationContext();
//      rootContext.register(AppConfig.class);
//
//    // Manage the lifecycle of the root application context
//      container.addListener(new ContextLoaderListener(rootContext));
//
//    // Create the dispatcher servlet's Spring application context
//    AnnotationConfigWebApplicationContext dispatcherContext =
//            new AnnotationConfigWebApplicationContext();
//      dispatcherContext.register(DispatcherConfig.class);
//
//    // Register and map the dispatcher servlet
//    ServletRegistration.Dynamic dispatcher =
//            container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
//      dispatcher.setLoadOnStartup(1);
//      dispatcher.addMapping("/");

//    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

//        startWithoutContextLoader(servletContext);
        startWithContextLoader(servletContext);

    }

    private void startWithContextLoader(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(RootAppConfig.class);
//        rootContext.refresh();

        servletContext.addListener(new ContextLoaderListener(rootContext));

        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(DispatcherServletConfig.class);
        dispatcherContext.scan(basePackages);
//        dispatcherContext.refresh();

        ServletRegistration.Dynamic  dispatcher = servletContext.addServlet("my-dispatcher",new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");


    }

    private void startWithoutContextLoader(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(RootAppConfig.class);
        rootContext.refresh();

//        AnnotationConfigWebApplicationContext dispatcherServletContext = new AnnotationConfigWebApplicationContext();
//        dispatcherServletContext.register(DispatcherServletConfig.class);
//        dispatcherServletContext.refresh();

        ServletRegistration.Dynamic dispathcer = servletContext.addServlet("dispatcherServlet",new DispatcherServlet(rootContext));
        dispathcer.setLoadOnStartup(1);
        dispathcer.addMapping("/");
    }
}
