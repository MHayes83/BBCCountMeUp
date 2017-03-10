package com.markhayes.bbccountmeup;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Main Request Mappings for BBC Count Me Up Application **/
@RestController
public class CountMeUpController {
	
	@RequestMapping("/countmeup")
    public Map<String, Integer> countMeUp()
    {
		Map<String, Integer> results = new HashMap<String, Integer>();
		results.put("Candidate 1", 500000);
		results.put("Candidate 2", 1000000);
		
        return results;
    }
}
