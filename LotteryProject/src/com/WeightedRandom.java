package com;

import java.util.*;

public class WeightedRandom {
	private static final Map<Integer, Integer> lotteryData = initializeLottoData();
    private static final Map<Integer, Double> weights = calculateWeights();
    private static final Random random = new Random();
    private static int number;
    private static double weight;

	// 복권 번호 리스트
    private static Map<Integer, Integer> initializeLottoData() {
        Map<Integer, Integer> lotteryData = new HashMap<>();
        lotteryData.put(1, 185);
        lotteryData.put(2, 174);
        lotteryData.put(3, 180);
        lotteryData.put(4, 182);
        lotteryData.put(5, 162);
        lotteryData.put(6, 177);
        lotteryData.put(7, 179);
        lotteryData.put(8, 163);
        lotteryData.put(9, 141);
        lotteryData.put(10, 176);
        lotteryData.put(11, 176);
        lotteryData.put(12, 190);
        lotteryData.put(13, 186);
        lotteryData.put(14, 182);
        lotteryData.put(15, 172);
        lotteryData.put(16, 177);
        lotteryData.put(17, 187);
        lotteryData.put(18, 183);
        lotteryData.put(19, 170);
        lotteryData.put(20, 182);
        lotteryData.put(21, 173);
        lotteryData.put(22, 148);
        lotteryData.put(23, 154);
        lotteryData.put(24, 178);
        lotteryData.put(25, 156);
        lotteryData.put(26, 180);
        lotteryData.put(27, 187);
        lotteryData.put(28, 156);
        lotteryData.put(29, 155);
        lotteryData.put(30, 171);
        lotteryData.put(31, 177);
        lotteryData.put(32, 162);
        lotteryData.put(33, 187);
        lotteryData.put(34, 191);
        lotteryData.put(35, 174);
        lotteryData.put(36, 175);
        lotteryData.put(37, 177);
        lotteryData.put(38, 178);
        lotteryData.put(39, 177);
        lotteryData.put(40, 180);
        lotteryData.put(41, 154);
        lotteryData.put(42, 167);
        lotteryData.put(43, 192);
        lotteryData.put(44, 174);
        lotteryData.put(45, 179);
        return lotteryData;
    }
    
    // 가중치 계산
    private static Map<Integer, Double> calculateWeights() {
        Map<Integer, Double> weights = new HashMap<>();
        
        // 전체값 계산
        int totalFrequency = 0;
        for (int frequency : lotteryData.values()) {
            totalFrequency += frequency;
        }
        
        // 각 당첨횟수 / 전체값
        for (Map.Entry<Integer, Integer> entry : lotteryData.entrySet()) {
            int number = entry.getKey();
            int frequency = entry.getValue();
            double weight = (double) frequency / totalFrequency;
            weights.put(number, weight);
        }
        return weights;
    }
    
    // 가중치에 따라 번호 선택
    static List<Integer> selectNumbers() {
        List<Integer> selectedNumbers = new ArrayList<>();
        
        // 6개의 숫자를 선택
        for (int i = 0; i < 6; i++) {
            double randomNumber = random.nextDouble(); // 0부터 1사이의 랜덤한 수 생성
            double cumulativeProbability = 0.0;
            
            // 누적 확률에 따라 번호 선택
            for (Map.Entry<Integer, Double> entry : weights.entrySet()) {
                int number = entry.getKey();
                double weight = entry.getValue();
                
                cumulativeProbability += weight;
                if (randomNumber <= cumulativeProbability) {
                    selectedNumbers.add(number);
                    break;
                }
            }
        }
        return selectedNumbers;
    }

//    // 검증 코드
//    public static void main(String[] args) {
//        // 결과를 저장할 맵 초기화
//        Map<Integer, Integer> numberCounts = new HashMap<>();
//        for (int i = 1; i <= 45; i++) {
//            numberCounts.put(i, 0);
//        }
//
//        // 시뮬레이션 실행
//        int simulations = 10000;
//        for (int i = 0; i < simulations; i++) {
//            List<Integer> selectedNumbers = selectNumbers();
//
//            // 선택된 번호들의 빈도수 증가
//            for (int number : selectedNumbers) {
//                numberCounts.put(number, numberCounts.get(number) + 1);
//            }
//        }
//
//        // 각 숫자의 빈도수 출력
//        System.out.println("각 숫자의 빈도수:");
//        for (Map.Entry<Integer, Integer> entry : numberCounts.entrySet()) {
//            int number = entry.getKey();
//            int count = entry.getValue();
//            double probability = (double) count / simulations;
//            System.out.println(number + " " + probability);
//        }
//    }
}
