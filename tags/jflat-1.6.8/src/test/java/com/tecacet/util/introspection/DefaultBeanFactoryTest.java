package com.tecacet.util.introspection;

import static org.junit.Assert.*;

import org.junit.Test;

public class DefaultBeanFactoryTest {

    @Test
    public void testCreateBeanOK() throws BeanCreationException {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        Order order = (Order) factory.createBean(Order.class);
        assertNotNull(order);
    }
    
    @Test
    public void testCreateBeanFails()  {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        try {
            SmallBean bean = (SmallBean) factory.createBean(SmallBean.class);
            fail();
        } catch (BeanCreationException e) {
           assertEquals(InstantiationException.class, e.getCause().getClass());
        }
    }

}
