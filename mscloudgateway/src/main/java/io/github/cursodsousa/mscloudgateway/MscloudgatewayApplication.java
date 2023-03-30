package io.github.cursodsousa.mscloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient //é usada quando o aplicativo usa apenas o servidor Eureka como servidor de descoberta
@EnableDiscoveryClient //é usada quando o aplicativo pode usar diferentes servidores de descoberta.
public class MscloudgatewayApplication {

	public static void main(String[] args) {

		SpringApplication.run(MscloudgatewayApplication.class, args);
	}
	@Bean //gerenciado pelo spring
	public RouteLocator routes(RouteLocatorBuilder builder){
		return builder
				.routes() //configura as rotas
				//patterns: caminho da solicitacao
				//uri: serviço de destino
					.route(r -> r.path("/clientes/**").uri("lb://msclientes"))
					.route(r -> r.path("/cartoes/**").uri("lb://mscartoes"))
					.route(r -> r.path("/avaliacoes-credito/**").uri("lb://msavaliadorcredito"))
				.build();
	}

}

