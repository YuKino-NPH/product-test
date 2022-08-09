package com.cvte.product.test.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author cvte
 * @date 2022/8/8 7:54 PM
 */
@Component
@ConfigurationProperties(prefix = "customer.product")
public class CommonConfig {
    private Set<String> status;

    public Set<String> getStatus() {
        return status;
    }

    public void setStatus(Set<String> status) {
        this.status = status;
    }
}
