package com.example.demo.Customer;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link CustomerController}.
 */
@Generated
public class CustomerController__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static CustomerController apply(RegisteredBean registeredBean,
      CustomerController instance) {
    AutowiredFieldValueResolver.forRequiredField("customerService").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
