package nba.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nba.tool.Code;
import nba.tool.JsonTool;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContext;

@Controller
@RequestMapping("/global")
public class GlobalController {

	@RequestMapping("/changeLocal/")
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String local = request.getParameter("local");

		if (local.equals("zh")) {
			Locale locale = new Locale("zh", "CN");
			request.getSession()
					.setAttribute(
							SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
							locale);
		} else if (local.equals("en")) {
			Locale locale = new Locale("en", "US");
			request.getSession()
					.setAttribute(
							SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
							locale);
		} else
			request.getSession().setAttribute(
					SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
					LocaleContextHolder.getLocale());

		JsonTool.getJson(Code.CHANGELOCALOK).write(response);
	}
}