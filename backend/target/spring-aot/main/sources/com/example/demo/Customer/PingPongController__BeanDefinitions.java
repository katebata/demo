package com.example.demo.Customer;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link PingPongController}.
 */
@Generated
public class PingPongController__BeanDefinitions {
  /**
   * Get the bean definition for 'pingPongController'.
   */
  public static BeanDefinition getPingPongControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(PingPongController.class);
    beanDefinition.setInstanceSupplier(PingPongController::new);
    return beanDefinition;
  }
}
