package com.markhayes.bbccountmeup;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

/** Unit Tests for BBC Count Me Up Application **/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CountMeUpUnitTests 
{

    @Autowired
    private CountMeUpController controller;
    @Autowired
    private TestRestTemplate restTemplate;
    
    @LocalServerPort
    private int port;
	
	@Test
    public void testCountMeUp()
    {
        Assert.assertNotNull(controller);
        
        @SuppressWarnings("unchecked")
		Map<String, Integer> results = restTemplate.getForObject("http://localhost:" + port + "/countmeup", Map.class);
        
        Assert.assertEquals((Integer)500000, results.get("Candidate 1"));
        Assert.assertEquals((Integer)1000000, results.get("Candidate 2"));
    }
}
