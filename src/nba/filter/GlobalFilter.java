package nba.filter;

import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nba.entity.Team;
import nba.entity.User;
import nba.service.AccountService;
import nba.service.GameService;
import nba.tool.WebUtil;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

public class GlobalFilter implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handlerMethod)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		if (this.isResources(handlerMethod)) {

		}

		String local = WebUtil.getCookies(request, "nbagame_lan");

		if (local == null || local.equals("")) {
			request.getSession().setAttribute(
					SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
					LocaleContextHolder.getLocale());
		} else {
			if (local.equals("zh")) {
				Locale locale = new Locale("zh", "CN");
				request.getSession().setAttribute(
						SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
						locale);

			} else if (local.equals("en")) {
				Locale locale = new Locale("en", "US");
				request.getSession().setAttribute(
						SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
						locale);
			}
		}

		User user = (User) request.getSession().getAttribute("loginu");

		if (null == user) {

			String userid = WebUtil.getCookies(request, "loginuid");

			if (null != userid && !userid.equals("")) {

				user = accountService.getUser(userid);

				request.getSession().setAttribute("loginu", user);

				Team team = (Team) request.getSession().getAttribute("team");

				if (team == null) {
					team = gameService.getTeamByUser(user);
					request.getSession().setAttribute("team", team);
				}
			}
		}

		return true;
	}

	/**
	 * Controller interceptor after method run
	 */
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handlerMethod,
			ModelAndView modelAndView) throws Exception {
		if (this.isResources(handlerMethod)) {

		} else {

		}
	}

	/**
	 * Controller interceptor after completion
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handlerMethod, Exception e)
			throws Exception {
	}

	/**
	 * Check is resources
	 */
	public boolean isResources(Object handlerMethod) {
		if (handlerMethod instanceof ResourceHttpRequestHandler) {
			return true;
		}
		return false;
	}

	@Resource
	private AccountService accountService;
	@Resource
	private GameService gameService;

}