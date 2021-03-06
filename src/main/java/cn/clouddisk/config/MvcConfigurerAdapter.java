package cn.clouddisk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfigurerAdapter extends WebMvcConfigurerAdapter {
    @Value("${fileDir}")
    private String storePath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/fileDir/**").addResourceLocations("file:"+storePath);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:index.jsp");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        List<String> excludePatterns = new ArrayList<>();
//        registry.addInterceptor(new LoginInterceptor())
//                .addPathPatterns("/searchUserFile", "/vipPlayer")
//                .excludePathPatterns("/signInPage");
    }
}
