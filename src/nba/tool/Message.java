package nba.tool;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestContext;

public class Message {
	public static String getMessage(HttpServletRequest request,String message_key){
		RequestContext requestContext = new RequestContext(request);
		return requestContext.getMessage(message_key);
	}
}
