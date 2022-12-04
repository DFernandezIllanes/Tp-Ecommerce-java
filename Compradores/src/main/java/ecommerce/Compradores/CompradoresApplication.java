package ecommerce.Compradores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import ecommerce.Compradores.models.CarritoDeCompra;
import ecommerce.Compradores.models.Comprador;
import ecommerce.Compradores.models.Item;

@SpringBootApplication
@EnableFeignClients
public class CompradoresApplication {

	@Autowired
	RepositoryRestConfiguration config;

	public static void main(String[] args) {
		SpringApplication.run(CompradoresApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(){
		config.exposeIdsFor(Comprador.class, CarritoDeCompra.class, Item.class);

		return (algo)->{
//			repoVendedor.save(new Vendedor("Santiago", "Barrios"));
//			repoVendedor.save(new Vendedor("Ramiro", "Barrios"));
		};
	}
}