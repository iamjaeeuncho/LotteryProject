package com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

class Pair<K, V> {

    K number;
    V weight;

    public Pair(K number, V weight) {
        this.number = number;
        this.weight = weight;
    }
}

class WeightedRandomLogic {

    private final List<Pair<String, Double>> candidates;

    public WeightedRandomLogic(List<Pair<String, Integer>> target) {
        // 1. 총 가중치 합 계산
        double totalWeight = 0;
        for (Pair<String, Integer> pair : target) {
            totalWeight += pair.weight;
        }

        // 2. 주어진 가중치를 백분율로 치환 (가중치 / 총 가중치)
        List<Pair<String, Double>> candidates = new ArrayList<>();
        for (Pair<String, Integer> pair : target) {
            candidates.add(new Pair<>(pair.number, pair.weight / totalWeight));
        }

        // 3. 가중치의 오름차순으로 정렬
        candidates.sort(Comparator.comparingDouble(p -> p.weight));
        this.candidates = candidates;
    }

    public String getRandom() {
        // 1. 랜덤 기준점 설정
        final double pivot = Math.random();

        // 2. 가중치의 오름차순으로 원소들을 순회하며 가중치를 누적
        double acc = 0;
        for (Pair<String, Double> pair : candidates) {
            acc += pair.weight;

            // 3. 누적 가중치 값이 기준점 이상이면 종료
            if (pivot <= acc) {
                return pair.number;
            }
        }

        return null;
    }
}

public class WeightedRandom {

    public static void main(String[] args) {
        List<Pair<String, Integer>> target = Arrays.asList(                
                new Pair<>("1", 185),
                new Pair<>("2", 174),
                new Pair<>("3", 180),
                new Pair<>("4", 182),
                new Pair<>("5", 162),
                new Pair<>("6", 177),
                new Pair<>("7", 179),
                new Pair<>("8", 163),
                new Pair<>("9", 141),
                new Pair<>("10", 176),
                new Pair<>("11", 176),
                new Pair<>("12", 190),
                new Pair<>("13", 186),
                new Pair<>("14", 182),
                new Pair<>("15", 172),
                new Pair<>("16", 177),
                new Pair<>("17", 187),
                new Pair<>("18", 183),
                new Pair<>("19", 170),
                new Pair<>("20", 182),
                new Pair<>("21", 173),
                new Pair<>("22", 148),
                new Pair<>("23", 154),
                new Pair<>("24", 178),
                new Pair<>("25", 156),
                new Pair<>("26", 180),
                new Pair<>("27", 187),
                new Pair<>("28", 156),
                new Pair<>("29", 155),
                new Pair<>("30", 171),
                new Pair<>("31", 177),
                new Pair<>("32", 162),
                new Pair<>("33", 187),
                new Pair<>("34", 191),
                new Pair<>("35", 174),
                new Pair<>("36", 175),
                new Pair<>("37", 177),
                new Pair<>("38", 178),
                new Pair<>("39", 177),
                new Pair<>("40", 180),
                new Pair<>("41", 154),
                new Pair<>("42", 167),
                new Pair<>("43", 192),
                new Pair<>("44", 174),
                new Pair<>("45", 179)
        );

        WeightedRandomLogic random = new WeightedRandomLogic(target);

        int count = 10000;
        Map<String, Integer> wordCount = new HashMap<>();
        for (int i = 0; i < count; i++) {
            String word = random.getRandom();
            wordCount.put(word, 1 + wordCount.getOrDefault(word, 0));
        }

        for (Entry<String, Integer> e : wordCount.entrySet()) {
            System.out.printf("%s 등장 확률 : %.2f\n", e.getKey(), (double) e.getValue() / (double) count);
        }
    }
}