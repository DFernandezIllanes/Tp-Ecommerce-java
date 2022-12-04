package ecommerce.Vendedores;

import ecommerce.Vendedores.app.RepoVendedor;
import ecommerce.Vendedores.models.MetodoPago;
import ecommerce.Vendedores.models.Personalizacion;
import ecommerce.Vendedores.models.ProductoFinal;
import ecommerce.Vendedores.models.Publicacion;
import ecommerce.Vendedores.models.Tienda;
import ecommerce.Vendedores.models.Vendedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
@EnableFeignClients
public class VendedoresApplication {

	@Autowired
	RepositoryRestConfiguration config;

	public static void main(String[] args) {
		SpringApplication.run(VendedoresApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(RepoVendedor repoVendedor){
		config.exposeIdsFor(Vendedor.class, ProductoFinal.class, Personalizacion.class, Tienda.class, Publicacion.class, MetodoPago.class);

		return (algo)->{
//			repoVendedor.save(new Vendedor("Santiago", "Barrios"));
//			repoVendedor.save(new Vendedor("Ramiro", "Barrios"));
		};
	}
}
