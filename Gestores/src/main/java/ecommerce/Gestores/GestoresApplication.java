package ecommerce.Gestores;

import ecommerce.Gestores.app.RepoGestor;
import ecommerce.Gestores.app.RepoProductoBase;
import ecommerce.Gestores.models.Gestor;
import ecommerce.Gestores.models.ProductoBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class GestoresApplication {

	@Autowired
	RepositoryRestConfiguration config;

	public static void main(String[] args) {
		SpringApplication.run(GestoresApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(RepoGestor repoGestor, RepoProductoBase repoProductoBase){

		config.exposeIdsFor(Gestor.class, ProductoBase.class);

		return (cosas) -> {
//			Gestor gestor1 = new Gestor("Alejandro", "Fantino");
//			Gestor gestor2 = new Gestor("Sergio", "Figluolo");
//
//			repoGestor.save(gestor1);
//			repoGestor.save(gestor2);
//
//			repoProductoBase.save(new ProductoBase("remera", gestor1));
//			repoProductoBase.save(new ProductoBase("pantalon", gestor1));
//			repoProductoBase.save(new ProductoBase("pantalon", gestor2));
		};
	}
}
