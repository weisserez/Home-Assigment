package com.soliduslabs.homework.Problem2;

import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.BDDAssertions.then;

public class SolutionTest {

    private final Solution solution = new Solution();


    @Test
    public void  testFindPairEqualToBalance() throws IOException {
        String pairItem = solution.findPair(getClass().getClassLoader().getResource("Shop.txt").getPath(),2500);
        then(pairItem).isEqualTo("Earmuffs 2000, Candy Bar 500");

    }

    @Test
    public void  testFindPairNotFound() throws IOException {
        String pairItem = solution.findPair(getClass().getClassLoader().getResource("Shop.txt").getPath(),1100);
        then(pairItem).isEqualTo("Not Possible");

    }
}
