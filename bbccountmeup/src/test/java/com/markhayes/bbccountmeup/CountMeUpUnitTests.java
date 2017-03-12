package com.markhayes.bbccountmeup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    
    private boolean testDuration = false;
	
	@Test
	@SuppressWarnings("unchecked")
    public void testCountMeUp()
    {
		List<Vote> votes = prepareVotes();
		controller.receiveVotes(votes);
		
		long startTime = System.currentTimeMillis();               
		Map<String, Integer> results = restTemplate.getForObject("http://localhost:" + port + "/countmeup", Map.class);
        long endTime = System.currentTimeMillis();
        
        long countMeUpDuration = endTime-startTime;
        
        Assert.assertEquals((Integer)500000, results.get("Candidate 1"));
        Assert.assertEquals((Integer)1000000, results.get("Candidate 2"));
        Assert.assertEquals((Integer)2000000, results.get("Candidate 3"));
        Assert.assertEquals((Integer)2500000, results.get("Candidate 4"));
        Assert.assertEquals((Integer)3000000, results.get("Candidate 5"));
        
        if(testDuration) {
        	Assert.assertTrue(countMeUpDuration<1000);
        }
    }
	
	private List<Vote> prepareVotes() {
		List<Vote> votes = new ArrayList<Vote>();
			
		//valid votes
		//Assume average of 2 votes by voter
		for(int i=0; i<250000 ; i++) {
			String user = UUID.randomUUID().toString();
			votes.add(new Vote("Candidate 1", user));
			votes.add(new Vote("Candidate 1", user));
		}	
		for(int i=0; i<500000 ; i++) {
			String user = UUID.randomUUID().toString();
			votes.add(new Vote("Candidate 2", user));
			votes.add(new Vote("Candidate 2", user));
		}
		for(int i=0; i<1000000 ; i++) {
			String user = UUID.randomUUID().toString();
			votes.add(new Vote("Candidate 3", user));
			votes.add(new Vote("Candidate 3", user));
		}
		for(int i=0; i<1250000 ; i++) {
			String user = UUID.randomUUID().toString();
			votes.add(new Vote("Candidate 4", user));
			votes.add(new Vote("Candidate 4", user));
		}
		//Three votes per voter for Candidate 5 to test edge case
		for(int i=0; i<999999 ; i++) {
			String user = UUID.randomUUID().toString();
			votes.add(new Vote("Candidate 5", user));
			votes.add(new Vote("Candidate 5", user));
			votes.add(new Vote("Candidate 5", user));
		}	
		
		//duplicate votes
		for(int i=0; i<1000000 ; i++) {
			votes.add(new Vote("Candidate 5", "Duplicate Voter"));
		}
		
		return votes;
		
	}
}
