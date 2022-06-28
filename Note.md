# Bean的加载方式
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- xml方式声明自己开发的bean -->
    <bean id="lamb" class="com.mildlamb.pojo.Lamb"></bean>

    <!-- xml方式声明第三方的bean -->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource"></bean>
    <bean class="com.alibaba.druid.pool.DruidDataSource"></bean>
    <bean class="com.alibaba.druid.pool.DruidDataSource"></bean>

</beans>
```