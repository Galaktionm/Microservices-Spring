package main.services;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
public class OrderService {
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	public Mono<Object> getServiceName() {
		
	   Mono<Object> x=getData().map(e->{
	    	    TreeMap<Object, String> results=new TreeMap<>();
				Map t1=e.getT1();
				Map t2=e.getT2();
				System.out.println(t1);
				System.out.println(t2);
				results.putAll(t1);
				results.putAll(t2);
				return Mono.just(results.firstEntry().getValue());
			}).collectList().map(e->{
				return e.get(0);
			});
	   
	   return x.map(e->{
		   return Mono.just(e);
	   });
	   
	   
	}
	
	private Flux<Tuple2<Map, Map>> getData() {
		return Flux.zip(getLoadPoint("KitchenService-KingsLanding"), getLoadPoint("KitchenService-Shire"));
	}	
	
	private Mono<Map> getLoadPoint(String serviceId){
		return webClientBuilder.build().get()
				.uri("http://{servideId}/api/management/loadPoint", serviceId)
				.retrieve().bodyToMono(Map.class);
	}

}
