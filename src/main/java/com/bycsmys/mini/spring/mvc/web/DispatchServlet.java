package com.bycsmys.mini.spring.mvc.web;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DispatchServlet extends HttpServlet {

    private Map<String, MappingValue> mappingValues;

    private Map<String, Class<?>> mappingClz = new HashMap<>();

    private Map<String, Object> mappingObjs = new HashMap<>();


    private List<String> packageNames = new ArrayList<>();

    private Map<String, Object> controllerObjs = new HashMap<>();

    private List<String> controllerNames = new ArrayList<>();

    private Map<String, Class<?>> controllerClasses = new HashMap<>();

    private Map<String, Method> mappingMethods = new HashMap<>();

    private List<String> urlMappingNames = new ArrayList<>();


    @Override
    public void init(ServletConfig config) {
        try {
            super.init(config);
            String contextConfigLocation = config.getInitParameter("contextConfigLocation");
            URL xmlPath = this.getServletContext().getResource(contextConfigLocation);
            Resource resource = new ClassPathXmlResource(xmlPath);
            XmlConfigReader xmlConfigReader = new XmlConfigReader();
            mappingValues = xmlConfigReader.loadConfig(resource);

            this.packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);

            Refresh();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    private void Refresh() {

        initController();
        initMapping();

        for (Map.Entry<String, MappingValue> entry : mappingValues.entrySet()) {
            String beanId = entry.getKey();
            String className = entry.getValue().getClz();
            try {
                Class<?> clz = Class.forName(className);
                Object obj = clz.newInstance();

                mappingClz.put(beanId, clz);
                mappingObjs.put(beanId, obj);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


    }

    private void initMapping() {
        for (String controllerName : this.controllerNames) {
            Class<?> clazz = this.controllerClasses.get(controllerName);
            Object obj = this.controllerObjs.get(controllerName);

            Method[] methods = clazz.getDeclaredMethods();

            if (methods == null) {
                continue;
            }

            for (Method method : methods) {
                boolean annotationPresent = method.isAnnotationPresent(RequestMapping.class);
                if (annotationPresent) {
                    String methodName = method.getName();
                    String value = method.getAnnotation(RequestMapping.class).value();
                    this.urlMappingNames.add(value);
                    this.mappingObjs.put(value, obj);
                    this.mappingMethods.put(value, method);
                }
            }

        }

    }

    private void initController() {
        this.controllerNames = scanPackages(this.packageNames);
        for (String controllerName : controllerNames) {
            try {
                Class<?> clz = Class.forName(controllerName);
                this.controllerClasses.put(controllerName, clz);
                Object obj = clz.newInstance();
                this.controllerObjs.put(controllerName, obj);
            } catch (Exception e) {
            }
        }
    }

    private List<String>  scanPackages(List<String> packages) {
        List tempControllerNames = new ArrayList<>();
        for (String packageName : packages) {
            tempControllerNames.addAll(scanPackage(packageName));
        }
        return tempControllerNames;
    }

    private List<String> scanPackage(String packageName) {
        List<String> tempControllerNames = new ArrayList<>();
        try {
            URI uri = this.getClass().getResource("/" + packageName.replaceAll("\\.", "/")).toURI();
            File dir = new File(uri);

            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    scanPackage(packageName + "." + file.getName());
                }else {
                    String controllerName = packageName + "." + file.getName().replace(".class", "");
                    tempControllerNames.add(controllerName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempControllerNames;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String servletPath = req.getServletPath();
        if (this.mappingValues.get(servletPath) == null || this.urlMappingNames.contains(servletPath)) {
            return;
        }

        try {
            Class<?> clz = this.mappingClz.get(servletPath);
            Object obj = this.mappingObjs.get(servletPath);

            Method method = this.mappingMethods.get(servletPath);
            if (method == null) {
                String methodName = this.mappingValues.get(servletPath).getMethod();
                method = clz.getMethod(methodName);
            }

            Object res = method.invoke(obj);
            resp.getWriter().append(res.toString());

        } catch (Exception e) {
        }


    }
}
