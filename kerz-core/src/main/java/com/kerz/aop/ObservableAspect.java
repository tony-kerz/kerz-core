package com.kerz.aop;

import java.util.Observable;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObservableAspect extends Observable
{
	private Logger log = LoggerFactory.getLogger(ObservableAspect.class);
	
	public void notifyObserversAdvice(JoinPoint joinPoint) throws Throwable
	{
		log.debug("target={}, args={}", joinPoint.getTarget(), joinPoint.getArgs());
		Signature sig = joinPoint.getSignature();
		ObservedEvent oe = new ObservedEvent(sig.getDeclaringType(), sig.getName(), joinPoint.getArgs());
		log.debug("observed-event={}", oe);
		setChanged();
		notifyObservers(oe);
	}
}
