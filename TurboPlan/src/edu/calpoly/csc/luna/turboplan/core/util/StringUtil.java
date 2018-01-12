package edu.calpoly.csc.luna.turboplan.core.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

public class StringUtil {
	private static final Logger log = Logger.getLogger(StringUtil.class);

	public static String getHexString(byte[] raw) {
		byte[] hexCharTable = { (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6',
				(byte) '7', (byte) '8', (byte) '9', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e',
				(byte) 'f' };

		byte[] hex = new byte[2 * raw.length];
		int index = 0;

		for (byte b : raw) {
			int v = b & 0xFF;
			hex[index++] = hexCharTable[v >>> 4];
			hex[index++] = hexCharTable[v & 0xF];
		}
		
		String ret = "";
		try {
			ret = new String(hex, "ASCII");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		return ret;
	}

	public static Long parseLong(String str) {
		if (str == null) {
			return null;
		} else {
			return Long.parseLong(str);
		}
	}

	public static String long2str(Long number) {
		if (number == null) {
			return null;
		}

		return number.toString();
	}

	public static StringBuilder beanToString(Object obj, String... exceptFields) {
		Class<?> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();

		Set<String> fieldSet = new HashSet<String>(Arrays.asList(exceptFields));

		for (int i = 0; i < fields.length; i++) {
			if (fieldSet.contains(fields[i].getName())) {
				fields[i] = null;
			}
		}

		return beanToString(obj, fields);
	}
	
	public static StringBuilder beanToString(Object obj) {
		return beanToString(obj, (Field[]) null);
	}

	public static StringBuilder beanToString(Object obj, Field[] fields) {
		StringBuilder builder = new StringBuilder("[");
		Class<?> clazz = obj.getClass();
		if (fields == null || fields.length == 0) {
			fields = clazz.getDeclaredFields();
		}

		for (Field field : fields) {
			if (field == null || Modifier.isStatic(field.getModifiers())) {
				continue;
			}

			StringBuilder methodName = new StringBuilder(field.getName());
			methodName.setCharAt(0, Character.toUpperCase(methodName.charAt(0)));
			methodName.insert(0, "get");

			try {
				Method getter = clazz.getMethod(methodName.toString(), null);
				Object getRet = getter.invoke(obj, null);

				if (getRet != null && getRet.getClass().isArray()) {
					appendFieldValuePair(builder, field.getName(), arrayToString(getRet));
				} else {
					appendFieldValuePair(builder, field.getName(), getRet);
				}
			} catch (SecurityException e1) {
				log.warn("Exception when StringUtil tries to invoke getter of field: " + field.getName(), e1);
			} catch (NoSuchMethodException e2) {
				log.warn("Exception when StringUtil tries to invoke getter of field: " + field.getName(), e2);
			} catch (IllegalArgumentException e3) {
				log.warn("Exception when StringUtil tries to invoke getter of field: " + field.getName(), e3);
			} catch (IllegalAccessException e4) {
				log.warn("Exception when StringUtil tries to invoke getter of field: " + field.getName(), e4);
			} catch (InvocationTargetException e5) {
				log.warn("Exception when StringUtil tries to invoke getter of field: " + field.getName(), e5);
			}
		}

		return builder.delete(builder.length() - 2, builder.length()).append("]");
	}

	private static void appendFieldValuePair(StringBuilder builder, String field, Object obj)
			throws IllegalArgumentException, IllegalAccessException {
		builder.append(field).append(" = ");
		builder.append(obj == null ? "null" : obj.toString());
		builder.append(", ");
	}

	public static String arrayToString(Object obj) {
		if (obj.getClass().isArray()) {
			StringBuilder builder = new StringBuilder("[");

			Object[] arr = (Object[]) obj;

			if (arr.length == 0) {
				builder.append("<length = 0>");
			} else {
				for (Object element : arr) {
					builder.append(element).append(", ");
				}
			}

			return builder.delete(builder.length() - 2, builder.length()).append("]").toString();
		} else {
			return "";
		}
	}
	
	public static String fieldName2GetterName(String fieldname) {
		StringBuilder builder = new StringBuilder("get");
		builder.append(fieldname);
		builder.setCharAt(3, Character.toUpperCase(builder.charAt(3)));
		
		return builder.toString();
	}
	
	public static String fieldName2SetterName(String fieldname) {
		StringBuilder builder = new StringBuilder("set");
		builder.append(fieldname);
		builder.setCharAt(3, Character.toUpperCase(builder.charAt(3)));
		
		return builder.toString();
	}
	
	
}
