package com.formacionbdi.springboot.app.gateway.filters.factory;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class EjemploGatewayFilterFactory extends AbstractGatewayFilterFactory<EjemploGatewayFilterFactory.Configuacion>{

	private final Logger logger = LoggerFactory.getLogger(EjemploGatewayFilterFactory.class);
	
	
	public EjemploGatewayFilterFactory() {
		super(Configuacion.class);
		
	}

	@Override
	public GatewayFilter apply(Configuacion config) {
		return(exchange, chain)-> {
			
			logger.info("Ejecutando pre gateway filter factory: " + config.mensaje);			
			return chain.filter(exchange).then(Mono.fromRunnable(()->{
				
				Optional.ofNullable(config.cookieValor).ifPresent(cookie -> {
					exchange.getResponse().addCookie(ResponseCookie.from(config.cookieNombre, cookie).build());
				});
				
				logger.info("Ejecutando post gateway filter factory: "+ config.mensaje);
				
			}));
		};
	}
	
public static class Configuacion {
		
		private String mensaje;
		private String cookieValor;
		private String cookieNombre;
		public String getMensaje() {
			return mensaje;
		}
		public void setMensaje(String mensaje) {
			this.mensaje = mensaje;
		}
		public String getCookieValor() {
			return cookieValor;
		}
		public void setCookieValor(String cookieValor) {
			this.cookieValor = cookieValor;
		}
		public String getCookieNombre() {
			return cookieNombre;
		}
		public void setCookieNombre(String cookieNombre) {
			this.cookieNombre = cookieNombre;
		}	
	}
}
