package com.kerz.mail;

import java.util.Map;

public interface MailSender
{
	void send(String from, String to, String subject, String bodyCode, Map<String, Object> model);
}
