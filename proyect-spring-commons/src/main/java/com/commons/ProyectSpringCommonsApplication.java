package com.commons;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class}) //para que no haga la configuracion a la base
//de datos ya que al final, no se conecta a a ninguna, pero spring lo pide y con esto lo solucionamos
public class ProyectSpringCommonsApplication {
}
