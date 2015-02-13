package nba.tool;

import java.io.Serializable;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("serial")
public class JsonTool implements Serializable {

	/**
	 * Data
	 */
	private Object data;

	/**
	 * Error message
	 */
	private String message;

	/**
	 * Is success
	 */
	private boolean success;

	/**
	 * To string
	 */
	public String toString() {
		JSONObject jSONObject = JSONObject.fromObject(this);
		return jSONObject.toString();
	}

	/**
	 * Write to response
	 */
	public void write(HttpServletResponse response) {
		String output = this.toString();
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write(output);
			response.getWriter().flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * getJson
	 */
	public static JsonTool getJson(Object object) {
		return new JsonTool(true, null, null, object);
	}

	/**
	 * getJson
	 */
	public static JsonTool getJson(Exception e) {
		JSONObject data = new JSONObject();

		return new JsonTool(false, 0l, e.getMessage(), data);
	}

	/**
	 * getJson
	 */
	public static JsonTool getJson(Long errorCode, String message) {
		return new JsonTool(false, errorCode, message, null);
	}

	/**
	 * getJson
	 */
	public static JsonTool getJson(Long errorCode, String message, Object object) {
		return new JsonTool(false, errorCode, message, object);
	}

	/**
	 * Create
	 */
	@SuppressWarnings("rawtypes")
	private JsonTool(boolean success, Long errorCode, String message,
			Object data) {
		if (data instanceof JSONArray || data instanceof JSONObject) {
			this.data = data;
		} else if (data instanceof List) {
			JSONArray jsonArray = new JSONArray();
			for (Object o : (List) data) {
				jsonArray.add(o);
			}
			this.data = jsonArray;
		} else if (!(data instanceof String)) {
			this.data = JSONObject.fromObject(data);
		} else {
			this.data = data;
		}
		if (success) {
			message = "";
		}
		this.success = success;
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}