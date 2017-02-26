package com.ataybur.utils;

public class Utils {
	public static Integer convertIntToString(String str){
		Integer result;
		try {
			result = Integer.valueOf(str);
		} catch (Exception e) {
			result = 0;
		}
		return result;
	}
	
	public static <T, E extends T> E getter(T t, Class<E> clazz) throws InstantiationException, IllegalAccessException{
		if(t == null){
			t = clazz.newInstance();
		}
		return (E) t;
	}
}
