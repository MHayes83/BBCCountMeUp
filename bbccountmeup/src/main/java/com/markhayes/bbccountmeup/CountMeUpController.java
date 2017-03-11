package com.markhayes.bbccountmeup;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Main Request Mappings for BBC Count Me Up Application **/
@RestController
public class CountMeUpController {

	private Map<String, Integer> results = new HashMap<String, Integer>();
	private Map<String, Integer> votesByVoter = new HashMap<String, Integer>();
	private List<String> validCandidates = Arrays.asList("Candidate 1", "Candidate 2", "Candidate 3", "Candidate 4",
			"Candidate 5");
	
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
			if (validVote(vote)) {
				int votesForCandidate = results.get(vote.getCandidateName()) + 1;
				int votesByUser = 1;
				if (votesByVoter.get(vote.getVoterName()) != null) {
					votesByUser = votesByVoter.get(vote.getVoterName()) + 1;
				}

				results.put(vote.getCandidateName(), votesForCandidate);
				votesByVoter.put(vote.getVoterName(), votesByUser);

			}
		}

		return true;
	}

	/**
	 * Tests that a voter is has not already cast more than 3 votes and the vote
	 * is for a valid candidate
	 **/
	private boolean validVote(Vote vote) {
		if (validCandidates.contains(vote.getCandidateName())) {
			if (votesByVoter.get(vote.getVoterName()) == null || votesByVoter.get(vote.getVoterName()) < 3) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method is used to prepare the scenario given in the tecchnical
	 * challenge. It represents the votes received in the messaging queue which
	 * haven't been processed
	 **/
	public void receiveVotes(List<Vote> votes) {
		unprocessedVotes = votes;
	}
}
