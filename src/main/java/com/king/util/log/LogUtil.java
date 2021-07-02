package com.king.util.log;

import com.king.util.StringTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * @description: 日志工具类
 **/
public class LogUtil {
	private static Logger logger;
	private static final String NO_EXCEPTION_TEMPLATE  = "{0}.{1}({2}:{3}) desc is => {4} ; info is => ";

	private static final String HAS_EXCEPTION_TEMPLATE = "{0}.{1}({2}:{3}) desc is => {6} ; error is => ({4}:{5}) ; info is => ";

	private static final String HAS_EXCEPTION_TEMPLATE2 = "{0}.{1}({2}:{3}) desc is => {6} ; error is => ({4}:{5}) ; ";

	public static void error(Throwable e, String methodDesc, String... info) {
		StackTraceElement stackTraceElement = getStackTraceElement();
		Logger logger = getLogger(stackTraceElement.getClassName());
		error(logger, stackTraceElement, e, methodDesc, "", info);
	}

	public static void error(String methodDesc,Throwable e) {
		StackTraceElement stackTraceElement = getStackTraceElement();
		Logger logger = getLogger(stackTraceElement.getClassName());
		error(logger, stackTraceElement, e, methodDesc, "");
	}

	public static void error(String methodDesc, String infoFormat, Object... info) {
		StackTraceElement stackTraceElement = getStackTraceElement();
		Logger logger = getLogger(stackTraceElement.getClassName());
		error(logger, stackTraceElement, null, methodDesc, infoFormat, info);
	}

	public static void info(Throwable e, String methodDesc, Object... info) {
		StackTraceElement stackTraceElement = getStackTraceElement();
		Logger log = getLogger(stackTraceElement.getClassName());
		info(log, stackTraceElement, e, methodDesc, "", info);
	}

	public static void info(String methodDesc, String infoFormat, Object... info) {
		StackTraceElement stackTraceElement = getStackTraceElement();
		Logger log = getLogger(stackTraceElement.getClassName());
		info(log, stackTraceElement, null, methodDesc, infoFormat, info);
	}

	private static void info(Logger log, StackTraceElement stackTraceElement, Throwable throwable, String methodDesc,
                             String infoFormat, Object... info) {
		StackTraceElement showSte = stackTraceElement;
		if (throwable == null) {
			if(infoFormat ==null && info !=null ){
				StringBuffer sb = new StringBuffer();
				for(int i=0 ;i<info.length;i++){
					sb.append("{} ");
				}
				infoFormat=sb.toString();
			}
			String infoMsg = MessageFormat.format(NO_EXCEPTION_TEMPLATE, showSte.getClassName(),
					showSte.getMethodName(), showSte.getFileName(), showSte.getLineNumber() + "", methodDesc)
					+ infoFormat;
			log.info(infoMsg, info);
		} else {
			StackTraceElement[] stackTraceElements = throwable.getStackTrace();
			int steLen = stackTraceElements.length;
			for (int i = steLen - 1; i >= 0; i--) {
				StackTraceElement eSte = stackTraceElements[i];
				if (eSte.getClassName().contains(".kingcar.")) {
					showSte = eSte;
				}
			}
			String infoMsg = "";
			if(info ==null || info.length ==0){
				infoMsg = MessageFormat.format(HAS_EXCEPTION_TEMPLATE2, showSte.getClassName(),
						showSte.getMethodName(), showSte.getFileName(), showSte.getLineNumber() + "",
						throwable.getClass().getName(), throwable.getMessage(), methodDesc);
			}else{
				infoMsg = MessageFormat.format(HAS_EXCEPTION_TEMPLATE, showSte.getClassName(),
						showSte.getMethodName(), showSte.getFileName(), showSte.getLineNumber() + "",
						throwable.getClass().getName(), throwable.getMessage(), methodDesc)
						+ StringTools.arrayToStr(info.toString(), ";");
			}
			log.info(infoMsg, throwable);
		}
	}

	private static void error(Logger log, StackTraceElement stackTraceElement, Throwable throwable, String methodDesc,
                              String infoFormat, Object... info) {
		StackTraceElement showSte = stackTraceElement;
		if (throwable == null) {
			if(infoFormat ==null && info !=null ){
				StringBuffer sb = new StringBuffer();
				for(int i=0 ;i<info.length;i++){
					sb.append("{} ");
				}
				infoFormat=sb.toString();
			}
			String errorMsg = MessageFormat.format(NO_EXCEPTION_TEMPLATE, showSte.getClassName(),
					showSte.getMethodName(), showSte.getFileName(), showSte.getLineNumber() + "", methodDesc)
					+ infoFormat;
			log.error(errorMsg, (Object[]) info);
		} else {
			StackTraceElement[] eStes = throwable.getStackTrace();
			int steLen = eStes.length;
			for (int i = steLen - 1; i >= 0; i--) {
				StackTraceElement eSte = eStes[i];
				if (eSte.getClassName().contains(".kingcar.")) {
					showSte = eSte;
				}
			}
			String errorMsg = "";
			if(info ==null || info.length ==0){
				errorMsg = MessageFormat.format(HAS_EXCEPTION_TEMPLATE2, showSte.getClassName(),
						showSte.getMethodName(), showSte.getFileName(), showSte.getLineNumber() + "",
						throwable.getClass().getName(), throwable.getMessage(), methodDesc);
			}else{
				errorMsg = MessageFormat.format(HAS_EXCEPTION_TEMPLATE, showSte.getClassName(),
						showSte.getMethodName(), showSte.getFileName(), showSte.getLineNumber() + "",
						throwable.getClass().getName(), throwable.getMessage(), methodDesc)
						+ StringTools.arrayToStr(info.toString(), ";");
			}
			log.error(errorMsg, throwable);
		}
	}

	private static Logger getLogger(String className) {
		Logger log;
		try {
			log = LoggerFactory.getLogger(className);
		} catch (Exception e) {
			log = logger;
		}
		return log;
	}

	private static StackTraceElement getStackTraceElement() {
		StackTraceElement[] temp = Thread.currentThread().getStackTrace();
		return temp[3];
	}

}
