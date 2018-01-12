package edu.calpoly.csc.luna.turboplan.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import org.apache.log4j.Logger;

public class ReflectionUtil {
	private static final Logger log = Logger.getLogger(ReflectionUtil.class);
	
	public static Class<?> getCollectionContentType(Collection<?> col) {
		for (Object obj : col) {
			return obj.getClass();
		}
		
		return null;
	}
	
	public static void setFieldValueOfObject(Object obj, String fieldname, Object value) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Class<?> clazz = obj.getClass();
		Method setter = clazz.getMethod(fieldToSetterOrGetter(fieldname, true), value.getClass());
		
		setter.invoke(obj, value);
	}
	
	public static Object getFieldValueOfObject(Object obj, String fieldname) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Class<?> clazz = obj.getClass();
		Method setter = clazz.getMethod(fieldToSetterOrGetter(fieldname, false));
		
		return setter.invoke(obj);
	}
	
	private static String fieldToSetterOrGetter(String fieldname, boolean setter) {
		if (setter) {
			return StringUtil.fieldName2SetterName(fieldname);
		} else {
			return StringUtil.fieldName2GetterName(fieldname);
		}
	}
	
	public static void nullCheck(Object obj) {
		Method[] methods = obj.getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("get")
					&& method.getReturnType() != Void.class
					&& method.getParameterTypes().length == 0) {
				try {
					if (method.invoke(obj) == null) {
						StringBuilder builder = new StringBuilder();
						builder.append(method.getName()).append(" returned null");
						log.trace(builder);
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
