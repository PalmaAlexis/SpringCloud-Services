package com.proyectspringzuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PostTiempoTranscurridoFilter extends ZuulFilter{
	
	private static Logger log= LoggerFactory.getLogger(PostTiempoTranscurridoFilter.class);

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		
		RequestContext ctx= RequestContext.getCurrentContext();
		HttpServletRequest request= ctx.getRequest();
		
		log.info("Entrando a post filter");
		Long tiempoTranscurridoPre= (Long)request.getAttribute("tiempoTranscurridoPre");
		Long tiempoTranscurridoPost= System.currentTimeMillis();
		
		Long tiempoTranscurridoFinal= tiempoTranscurridoPost-tiempoTranscurridoPre;
		
		
		
		log.info("Tiempo transcurrido en segundos= "+tiempoTranscurridoFinal.doubleValue()/1000 +" segundos");
		log.info("Tiempo transcurrido en milisegundos= "+tiempoTranscurridoFinal+" milisegundos");
		
		return null;
	}

	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
