package sd.config.inner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sd.filter.RequestFilter;
import sd.log.Logger;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RequestFilter> tokenFilter(
            @Autowired Logger logger
    ) {
        FilterRegistrationBean<RequestFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RequestFilter(logger));
        registration.addUrlPatterns(
                "/file/*", "/picture/*", "/query/*"
        );
        registration.setName("tokenFilter");
        return registration;
    }
}