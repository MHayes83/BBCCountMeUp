package com.markhayes.bbccountmeup;

/** A single vote for a candidate **/
public class Vote {
	private String candidateName;
	private String voterName;
	
	public Vote() {
		
	}
	
	public Vote(String candidateName, String voterName) {
		this.candidateName = candidateName;
		this.voterName = voterName;
	}
	
	public String getCandidateName() {
		return candidateName;
	}

	public String getVoterName() {
		return voterName;
	}
}
