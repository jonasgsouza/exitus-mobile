package br.ueg.mobile.exitus.utils;

import java.util.Collection;
import java.util.Map;

public class ObjectUtils {
	
	public static boolean isNullOrEmpty(Object object){

		if(object == null) return true;
		
		if(object instanceof Character){
			return String.valueOf(object).trim().isEmpty();
		}
		if(object instanceof Object[]){
			return ((Object[]) object).length == 0;
		}
		if(object instanceof String){
			return ((String) object).trim().isEmpty();
		}
		if(object instanceof Collection<?>){
			return ((Collection<?>) object).isEmpty();
		}
		if(object instanceof Map<?,?>){
			return ((Map<?,?>) object).isEmpty();
		}
		
		return false;
	}

}
