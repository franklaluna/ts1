package zkxy.exception;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice    
public class GlobalExceptionHandler {
 
	    @ResponseBody
	    @ExceptionHandler(value = Exception.class)
	    public Map errorHandler(Exception ex) {
	    	ex.printStackTrace();
	        Map map = new HashMap();
	        map.put("result", false);
	        map.put("message", ex.getMessage());
	        return map;
	    }
}
