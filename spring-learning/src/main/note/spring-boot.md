# Spring Boot

### WebApplicationInitializer
- 如果是使用spring-boot内嵌的tomcat这样的容器中，建议不要 WebApplicationInitializer
- 如果是要部署到其他容器，则可以采用 WebApplicationInitializer做些自定义的事情
>WebApplicationInitializer is only needed if you are building a war file and deploying it. If you prefer to run an embedded container then you won't need this at all.

