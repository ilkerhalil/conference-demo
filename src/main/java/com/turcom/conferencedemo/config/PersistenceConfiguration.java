// package com.turcom.conferencedemo.config;

// import java.beans.PropertyVetoException;

// import javax.sql.DataSource;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.SpringBootConfiguration;
// import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
// import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
// import org.springframework.boot.jdbc.DataSourceBuilder;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Profile;
// import org.springframework.context.annotation.PropertySource;
// import org.springframework.core.env.Environment;
// import org.springframework.jdbc.datasource.DriverManagerDataSource;

// @SpringBootConfiguration
// @EnableAutoConfiguration
// public class PersistenceConfiguration {

//     @Autowired
//     Environment environment;

//     // @Bean
//     // public DataSource dataSource(){
//     // DataSourceBuilder builder = DataSourceBuilder.create();
//     // builder.username("postgres");
//     // builder.password("Welcome");
//     // builder.url("jdbc:postgresql://localhost:5432/conference_app");
//     // System.out.println("My custom datasource bean has been initialized and set");
//     // return builder.build();
//     // }
//     // @Bean
//     // @Profile("beta")
//     // public DataSource datasource() {
//     //     // final DriverManagerDataSource dataSource = new DriverManagerDataSource();
//     //     // //dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
//     //     // DataSource dSource = new DataSource()
//     //     // dSource.

//     //     // dataSource.setUrl(environment.getProperty("spring.datasource.url"));
//     //     // dataSource.setUsername(environment.getProperty("spring.datasource.username"));
//     //     // dataSource.setPassword(environment.getProperty("spring.datasource.password"));
//     //     // return dataSource;
//     //     final DataSourceBuilder builder = DataSourceBuilder.create();
        
     
//     //     return builder.build();
//     // }
// }
