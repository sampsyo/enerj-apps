/*
 * Copyright (C) 2010 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.zxing.oned.rss;

import com.google.zxing.NotFoundException;
import com.google.zxing.oned.OneDReader;

import enerj.lang.*;

public abstract class AbstractRSSReader extends OneDReader {

  private static final int MAX_AVG_VARIANCE = (int) (PATTERN_MATCH_RESULT_SCALE_FACTOR * 0.2f);
  private static final int MAX_INDIVIDUAL_VARIANCE = (int) (PATTERN_MATCH_RESULT_SCALE_FACTOR * 0.4f);

  private static final float MIN_FINDER_PATTERN_RATIO = 9.5f / 12.0f;
  private static final float MAX_FINDER_PATTERN_RATIO = 12.5f / 14.0f;

  protected final @Approx int[] decodeFinderCounters;
  protected final @Approx int[] dataCharacterCounters;
  protected final @Approx float[] oddRoundingErrors;
  protected final @Approx float[] evenRoundingErrors;
  protected final @Approx int[] oddCounts;
  protected final @Approx int[] evenCounts;

  protected AbstractRSSReader(){
      decodeFinderCounters = new @Approx int[4];
      dataCharacterCounters = new @Approx int[8];
      oddRoundingErrors = new @Approx float[4];
      evenRoundingErrors = new @Approx float[4];
      oddCounts = new @Approx int[dataCharacterCounters.length / 2];
      evenCounts = new @Approx int[dataCharacterCounters.length / 2];
  }


  protected static int parseFinderValue(@Approx int[] counters, @Approx int [][] finderPatterns) throws NotFoundException {
    for (int value = 0; value < finderPatterns.length; value++) {
      if (Endorsements.endorse(patternMatchVariance(counters, finderPatterns[value], MAX_INDIVIDUAL_VARIANCE) <
          MAX_AVG_VARIANCE)) {
        return value;
      }
    }
    throw NotFoundException.getNotFoundInstance();
  }

  protected static int count(int[] array) {
    int count = 0;
    for (int i = 0; i < array.length; i++) {
      count += array[i];
    }
    return count;
  }

  protected static void increment(int[] array, float[] errors) {
    int index = 0;
    float biggestError = errors[0];
    for (int i = 1; i < array.length; i++) {
      if (errors[i] > biggestError) {
        biggestError = errors[i];
        index = i;
      }
    }
    array[index]++;
  }

  protected static void decrement(int[] array, float[] errors) {
    int index = 0;
    float biggestError = errors[0];
    for (int i = 1; i < array.length; i++) {
      if (errors[i] < biggestError) {
        biggestError = errors[i];
        index = i;
      }
    }
    array[index]--;
  }

  protected static @Approx boolean isFinderPattern(@Approx int[] counters) {
    @Approx int firstTwoSum = counters[0] + counters[1];
    @Approx int sum = firstTwoSum + counters[2] + counters[3];
    @Approx float ratio = (@Approx float) firstTwoSum / (@Approx float) sum;
    if (Endorsements.endorse(ratio >= MIN_FINDER_PATTERN_RATIO && ratio <= MAX_FINDER_PATTERN_RATIO)) {
      // passes ratio test in spec, but see if the counts are unreasonable
      @Approx int minCounter = Integer.MAX_VALUE;
      @Approx int maxCounter = Integer.MIN_VALUE;
      for (int i = 0; i < counters.length; i++) {
        @Approx int counter = counters[i];
        if (Endorsements.endorse(counter > maxCounter)) {
          maxCounter = counter;
        }
        if (Endorsements.endorse(counter < minCounter)) {
          minCounter = counter;
        }
      }
      return maxCounter < 10 * minCounter;
    }
    return false;
  }
}
