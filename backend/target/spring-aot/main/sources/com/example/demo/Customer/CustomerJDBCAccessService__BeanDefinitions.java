package com.example.demo.Customer;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * Bean definitions for {@link CustomerJDBCAccessService}.
 */
@Generated
public class CustomerJDBCAccessService__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'JDBC'.
   */
  private static BeanInstanceSupplier<CustomerJDBCAccessService> getJDBCInstanceSupplier() {
    return BeanInstanceSupplier.<CustomerJDBCAccessService>forConstructor(JdbcTemplate.class, RowMapper.class)
            .withGenerator((registeredBean, args) -> new CustomerJDBCAccessService(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'jDBC'.
   */
  public static BeanDefinition getJDBCBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(CustomerJDBCAccessService.class);
    beanDefinition.setInstanceSupplier(getJDBCInstanceSupplier());
    return beanDefinition;
  }
}
