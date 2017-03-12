package com.markhayes.bbccountmeup;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Main Request Mappings for BBC Count Me Up Application **/
@RestController
public class CountMeUpController {

	private Map<String, Integer> results = new HashMap<String, Integer>();
	private List<String> validCandidates = Arrays.asList("Candidate 1", "Candidate 2", "Candidate 3", "Candidate 4",
			"Candidate 5");
	
	private Map<String,Integer> votesByVoters = new HashMap<String, Integer>(10000000, 0.5f);
	
	private List<Vote> unprocessedVotes;

	public CountMeUpController() {
		initializeResults();
	}

	private void initializeResults() {
		for (String candidate : validCandidates) {
			results.put(candidate, 0);
		}
	}

	@RequestMapping("/countmeup")
	public Map<String, Integer> countMeUp() {
		if(unprocessedVotes != null && !unprocessedVotes.isEmpty()) {
			processVotes();
		}
		return results;
	}

	public Boolean processVotes() {
		for (Vote vote : unprocessedVotes) {
			String voterName = vote.getVoterName();
			int votesByVoter = votesByVoters.getOrDefault(voterName,0);

			if (votesByVoter<3) {
				int votesForCandidate = results.get(vote.getCandidateName()) + 1;
				
				votesByVoters.put(voterName, votesByVoter+1);		

				results.put(vote.getCandidateName(), votesForCandidate);
			}
		}

		return true;
	}

	/**
	 * This method is used to prepare the scenario given in the technical
	 * challenge. It represents the votes received in the messaging queue which
	 * haven't been processed
	 **/
	public void receiveVotes(List<Vote> votes) {
		unprocessedVotes = votes;
	}
}
