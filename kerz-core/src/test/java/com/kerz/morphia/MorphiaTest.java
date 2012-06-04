package com.kerz.morphia;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.code.morphia.Datastore;
import com.kerz.domain.IdentifiableHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class MorphiaTest
{
	@Autowired
	private Datastore ds;
	private Logger log = LoggerFactory.getLogger(MorphiaTest.class);
	
	@Autowired
	private WidgitRepository repo;
	
	// @After
	public void after()
	{
		MorphiaHelper.deleteAll(ds, Widgit.class);
	}
	
	@Test
	public void basic()
	{
		// Widgit widgit = new Widgit();
		// widgit.getWhatsits().add(new Whatsit(0));
		// repo.save(widgit);
		// Query<Widgit> q =
		// repo.createQuery().field(Mapper.ID_KEY).equal(widgit.getId()).field("whatsits.id").equal(0);
		// List<Widgit> widgits = repo.find(q).asList();
		// log.debug("widgets={} where id={}", widgits, widgit.getId());
		// Whoosit whoosit = new Whoosit(1);
		// UpdateOperations<Widgit> ops =
		// repo.createUpdateOperations().add("whatsits.whoosits", whoosit);
		// UpdateResults<Widgit> results = repo.update(q, ops);
		// Assert.assertTrue(!results.getHadError());
		// Assert.assertTrue(results.getUpdatedCount() == 1);
		
		Widgit widgit = new Widgit();
		Whatsit whatsit = new Whatsit(0);
		widgit.getWhatsits().add(whatsit);
		repo.save(widgit);
		widgit = repo.get(widgit.getId());
		Assert.assertNotNull(widgit);
		log.debug("widget={}", widgit);
		// List<Identifiable<Object>> list = widgit.getWhatsits();
		whatsit = (Whatsit) IdentifiableHelper.find(widgit.getWhatsits(), new Integer(0));
		// whatsit = IdentifiableHelper.find(widgit.getWhatsits(), new Integer(0));
		Whoosit whoosit = new Whoosit(1);
		whatsit.getWhoosits().add(whoosit);
		repo.save(widgit);
	}
}
