package javacodingassignment.config;

import java.io.IOException;
import javax.sql.DataSource;
import org.hsqldb.server.Server;
import org.hsqldb.server.ServerAcl.AclFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

@Configuration
public class HsqldbConfig {

  @Bean(initMethod = "start", destroyMethod = "stop")
  public Server hsqldbServer(@Value("classpath:hsqldb.properties") Resource props)
      throws IOException, AclFormatException {
    Server server = new Server();
    server.setProperties(PropertiesLoaderUtils.loadProperties(props));
    return server;
  }

  @Bean
  @DependsOn("hsqldbServer")
  public DataSource dataSource(@Autowired DataSourceProperties dsProps) {
    DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
    dataSourceBuilder.driverClassName(dsProps.getDriverClassName());
    dataSourceBuilder.url(dsProps.getUrl());
    dataSourceBuilder.username(dsProps.getUsername());
    dataSourceBuilder.password(dsProps.getPassword());
    return dataSourceBuilder.build();
  }

}
