package com.kerz.mail;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

public abstract class AbstractMailSender implements MailSender
{
	private Logger log = LoggerFactory.getLogger(AbstractMailSender.class);
	
	JavaMailSender sender;
	
	protected abstract String getText(String bodyCode, Map<String, Object> model);

	@Override
	public void send(final String from, final String to, final String subject, final String bodyCode,
	    final Map<String, Object> model)
	{
		MimeMessagePreparator mmp = new MimeMessagePreparator()
		{
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception
			{
				MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage);
				mmh.setTo(to);
				mmh.setFrom(from);
				mmh.setSubject(subject);
				mmh.setText(getText(bodyCode, model));
				log.debug("message={}", mmh.getMimeMessage().getContent());
			}
		};
		sender.send(mmp);
	}
	
	public void setSender(JavaMailSender sender)
  {
  	this.sender = sender;
  }
}
