package org.springframework.boot.autoconfigure.http.client;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link HttpClientSettingsProperties}.
 */
@Generated
public class HttpClientSettingsProperties__BeanDefinitions {
  /**
   * Get the bean definition for 'httpClientSettingsProperties'.
   */
  public static BeanDefinition getHttpClientSettingsPropertiesBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(HttpClientSettingsProperties.class);
    beanDefinition.setInstanceSupplier(HttpClientSettingsProperties::new);
    return beanDefinition;
  }
}
