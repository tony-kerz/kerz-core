package com.kerz.mail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class MailSenderTest
{
	@Autowired
	private MailSender mailSender;
	
	@Test
	public void basic()
	{
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("key-1", "val-1");
		model.put("key-2", "val-2");
		mailSender.send("tony.kerz@gmail.com", "tony.kerz@gmail.com", "test", "testCode", model);
	}
}
