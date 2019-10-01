package com.soliduslabs.homework.Problem2;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;


public class Solution {
    private Set<Integer> itemSet = new LinkedHashSet<>();
    private Map<Integer, String> costToItemDescriptionMap = new HashMap<>();
    private static final String formatAnswer = "{0} {1}, {2} {3}";
    private int firstItemCost = 0;
    private int secondItemCost = 0;
    private String firstItemDescription = null;
    private String secondItemDescription = null;

    /**
     * @param pathToFile
     * @param limit
     * @return
     */
    public String findPair(String pathToFile, int limit) throws IOException {


        int maxLineToIterate = 1000;
        int counter = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(new File(pathToFile)))) {
            String line;
            String description;
            int itemCost;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                description = row[0];
                itemCost = Integer.parseInt(row[1]);
                if (itemCost >= limit) {
                    break;
                }
                if (itemSet.contains(limit - itemCost)) {
                    return getFormatAnswer(description, itemCost, costToItemDescriptionMap.get((limit - itemCost)), (limit - itemCost));
                }
                itemSet.add(itemCost);
                costToItemDescriptionMap.put(itemCost, description);
                if (counter == maxLineToIterate) {
                    findSecondSum(limit);
                }
                counter++;
            }

        }
        if (counter < maxLineToIterate) {
            if (findSecondSum(limit)) {
                return getFormatAnswer(firstItemDescription, firstItemCost, secondItemDescription, secondItemCost);
            }

        } else if (firstItemDescription != null && secondItemDescription != null) {
            return getFormatAnswer(firstItemDescription, firstItemCost, secondItemDescription, secondItemCost);
        }
        return "Not Possible";
    }

    private boolean findSecondSum(int limit) {
        List<Integer> arr = new ArrayList<>(itemSet);
        int maxSum = 0;
        for (int i = 0; i < arr.size() - 1; i++) {
            for (int j = i + 1; j < arr.size() - 2; j++) {
                if (arr.get(i) + arr.get(j) < limit && arr.get(i) + arr.get(j) > maxSum) {
                    maxSum = arr.get(i) + arr.get(j);
                    firstItemCost = arr.get(i);
                    secondItemCost = arr.get(j);
                }
            }
        }
        firstItemDescription = costToItemDescriptionMap.get(firstItemCost);
        secondItemDescription = costToItemDescriptionMap.get(secondItemCost);
        itemSet.clear();
        costToItemDescriptionMap.clear();
        return firstItemDescription != null && secondItemDescription != null;
    }


    private String getFormatAnswer(String firstItemDescription, int firstItemCost, String secondItemDescription, int secondItemCost) {
        return MessageFormat.format(formatAnswer, firstItemDescription, Integer.toString(firstItemCost), secondItemDescription, Integer.toString(secondItemCost));
    }
}
