/*
 * Copyright 2008 ZXing authors
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

package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;

import java.util.Hashtable;

import enerj.lang.*;

/**
 * <p>Decodes Code 128 barcodes.</p>
 *
 * @author Sean Owen
 */
public final class Code128Reader extends OneDReader {

  static final @Approx int[][] CODE_PATTERNS = new @Approx int[][]{
      new @Approx int[]{2, 1, 2, 2, 2, 2}, // 0
      new @Approx int[]{2, 2, 2, 1, 2, 2},
      new @Approx int[]{2, 2, 2, 2, 2, 1},
      new @Approx int[]{1, 2, 1, 2, 2, 3},
      new @Approx int[]{1, 2, 1, 3, 2, 2},
      new @Approx int[]{1, 3, 1, 2, 2, 2}, // 5
      new @Approx int[]{1, 2, 2, 2, 1, 3},
      new @Approx int[]{1, 2, 2, 3, 1, 2},
      new @Approx int[]{1, 3, 2, 2, 1, 2},
      new @Approx int[]{2, 2, 1, 2, 1, 3},
      new @Approx int[]{2, 2, 1, 3, 1, 2}, // 10
      new @Approx int[]{2, 3, 1, 2, 1, 2},
      new @Approx int[]{1, 1, 2, 2, 3, 2},
      new @Approx int[]{1, 2, 2, 1, 3, 2},
      new @Approx int[]{1, 2, 2, 2, 3, 1},
      new @Approx int[]{1, 1, 3, 2, 2, 2}, // 15
      new @Approx int[]{1, 2, 3, 1, 2, 2},
      new @Approx int[]{1, 2, 3, 2, 2, 1},
      new @Approx int[]{2, 2, 3, 2, 1, 1},
      new @Approx int[]{2, 2, 1, 1, 3, 2},
      new @Approx int[]{2, 2, 1, 2, 3, 1}, // 20
      new @Approx int[]{2, 1, 3, 2, 1, 2},
      new @Approx int[]{2, 2, 3, 1, 1, 2},
      new @Approx int[]{3, 1, 2, 1, 3, 1},
      new @Approx int[]{3, 1, 1, 2, 2, 2},
      new @Approx int[]{3, 2, 1, 1, 2, 2}, // 25
      new @Approx int[]{3, 2, 1, 2, 2, 1},
      new @Approx int[]{3, 1, 2, 2, 1, 2},
      new @Approx int[]{3, 2, 2, 1, 1, 2},
      new @Approx int[]{3, 2, 2, 2, 1, 1},
      new @Approx int[]{2, 1, 2, 1, 2, 3}, // 30
      new @Approx int[]{2, 1, 2, 3, 2, 1},
      new @Approx int[]{2, 3, 2, 1, 2, 1},
      new @Approx int[]{1, 1, 1, 3, 2, 3},
      new @Approx int[]{1, 3, 1, 1, 2, 3},
      new @Approx int[]{1, 3, 1, 3, 2, 1}, // 35
      new @Approx int[]{1, 1, 2, 3, 1, 3},
      new @Approx int[]{1, 3, 2, 1, 1, 3},
      new @Approx int[]{1, 3, 2, 3, 1, 1},
      new @Approx int[]{2, 1, 1, 3, 1, 3},
      new @Approx int[]{2, 3, 1, 1, 1, 3}, // 40
      new @Approx int[]{2, 3, 1, 3, 1, 1},
      new @Approx int[]{1, 1, 2, 1, 3, 3},
      new @Approx int[]{1, 1, 2, 3, 3, 1},
      new @Approx int[]{1, 3, 2, 1, 3, 1},
      new @Approx int[]{1, 1, 3, 1, 2, 3}, // 45
      new @Approx int[]{1, 1, 3, 3, 2, 1},
      new @Approx int[]{1, 3, 3, 1, 2, 1},
      new @Approx int[]{3, 1, 3, 1, 2, 1},
      new @Approx int[]{2, 1, 1, 3, 3, 1},
      new @Approx int[]{2, 3, 1, 1, 3, 1}, // 50
      new @Approx int[]{2, 1, 3, 1, 1, 3},
      new @Approx int[]{2, 1, 3, 3, 1, 1},
      new @Approx int[]{2, 1, 3, 1, 3, 1},
      new @Approx int[]{3, 1, 1, 1, 2, 3},
      new @Approx int[]{3, 1, 1, 3, 2, 1}, // 55
      new @Approx int[]{3, 3, 1, 1, 2, 1},
      new @Approx int[]{3, 1, 2, 1, 1, 3},
      new @Approx int[]{3, 1, 2, 3, 1, 1},
      new @Approx int[]{3, 3, 2, 1, 1, 1},
      new @Approx int[]{3, 1, 4, 1, 1, 1}, // 60
      new @Approx int[]{2, 2, 1, 4, 1, 1},
      new @Approx int[]{4, 3, 1, 1, 1, 1},
      new @Approx int[]{1, 1, 1, 2, 2, 4},
      new @Approx int[]{1, 1, 1, 4, 2, 2},
      new @Approx int[]{1, 2, 1, 1, 2, 4}, // 65
      new @Approx int[]{1, 2, 1, 4, 2, 1},
      new @Approx int[]{1, 4, 1, 1, 2, 2},
      new @Approx int[]{1, 4, 1, 2, 2, 1},
      new @Approx int[]{1, 1, 2, 2, 1, 4},
      new @Approx int[]{1, 1, 2, 4, 1, 2}, // 70
      new @Approx int[]{1, 2, 2, 1, 1, 4},
      new @Approx int[]{1, 2, 2, 4, 1, 1},
      new @Approx int[]{1, 4, 2, 1, 1, 2},
      new @Approx int[]{1, 4, 2, 2, 1, 1},
      new @Approx int[]{2, 4, 1, 2, 1, 1}, // 75
      new @Approx int[]{2, 2, 1, 1, 1, 4},
      new @Approx int[]{4, 1, 3, 1, 1, 1},
      new @Approx int[]{2, 4, 1, 1, 1, 2},
      new @Approx int[]{1, 3, 4, 1, 1, 1},
      new @Approx int[]{1, 1, 1, 2, 4, 2}, // 80
      new @Approx int[]{1, 2, 1, 1, 4, 2},
      new @Approx int[]{1, 2, 1, 2, 4, 1},
      new @Approx int[]{1, 1, 4, 2, 1, 2},
      new @Approx int[]{1, 2, 4, 1, 1, 2},
      new @Approx int[]{1, 2, 4, 2, 1, 1}, // 85
      new @Approx int[]{4, 1, 1, 2, 1, 2},
      new @Approx int[]{4, 2, 1, 1, 1, 2},
      new @Approx int[]{4, 2, 1, 2, 1, 1},
      new @Approx int[]{2, 1, 2, 1, 4, 1},
      new @Approx int[]{2, 1, 4, 1, 2, 1}, // 90
      new @Approx int[]{4, 1, 2, 1, 2, 1},
      new @Approx int[]{1, 1, 1, 1, 4, 3},
      new @Approx int[]{1, 1, 1, 3, 4, 1},
      new @Approx int[]{1, 3, 1, 1, 4, 1},
      new @Approx int[]{1, 1, 4, 1, 1, 3}, // 95
      new @Approx int[]{1, 1, 4, 3, 1, 1},
      new @Approx int[]{4, 1, 1, 1, 1, 3},
      new @Approx int[]{4, 1, 1, 3, 1, 1},
      new @Approx int[]{1, 1, 3, 1, 4, 1},
      new @Approx int[]{1, 1, 4, 1, 3, 1}, // 100
      new @Approx int[]{3, 1, 1, 1, 4, 1},
      new @Approx int[]{4, 1, 1, 1, 3, 1},
      new @Approx int[]{2, 1, 1, 4, 1, 2},
      new @Approx int[]{2, 1, 1, 2, 1, 4},
      new @Approx int[]{2, 1, 1, 2, 3, 2}, // 105
      new @Approx int[]{2, 3, 3, 1, 1, 1, 2}
  };

  private static final int MAX_AVG_VARIANCE = (int) (PATTERN_MATCH_RESULT_SCALE_FACTOR * 0.25f);
  private static final int MAX_INDIVIDUAL_VARIANCE = (int) (PATTERN_MATCH_RESULT_SCALE_FACTOR * 0.7f);

  private static final int CODE_SHIFT = 98;

  private static final int CODE_CODE_C = 99;
  private static final int CODE_CODE_B = 100;
  private static final int CODE_CODE_A = 101;

  private static final int CODE_FNC_1 = 102;
  private static final int CODE_FNC_2 = 97;
  private static final int CODE_FNC_3 = 96;
  private static final int CODE_FNC_4_A = 101;
  private static final int CODE_FNC_4_B = 100;

  private static final int CODE_START_A = 103;
  private static final int CODE_START_B = 104;
  private static final int CODE_START_C = 105;
  private static final int CODE_STOP = 106;

  private static int[] findStartPattern(BitArray row) throws NotFoundException {
    int width = row.getSize();
    int rowOffset = 0;
    while (rowOffset < width) {
      if (Endorsements.endorse(row.get(rowOffset))) {
        break;
      }
      rowOffset++;
    }

    int counterPosition = 0;
    @Approx int[] counters = new @Approx int[6];
    @Approx int patternStart = rowOffset; // EnerJ TODO
    boolean isWhite = false;
    int patternLength = counters.length;

    for (int i = rowOffset; i < width; i++) {
      @Approx boolean pixel = row.get(i);
      if (Endorsements.endorse(pixel ^ isWhite)) {
        counters[counterPosition]++;
      } else {
        if (counterPosition == patternLength - 1) {
          @Approx int bestVariance = MAX_AVG_VARIANCE;
          int bestMatch = -1;
          for (int startCode = CODE_START_A; startCode <= CODE_START_C; startCode++) {
            @Approx int variance = patternMatchVariance(counters, CODE_PATTERNS[startCode],
                MAX_INDIVIDUAL_VARIANCE);
            if (Endorsements.endorse(variance < bestVariance)) {
              bestVariance = variance;
              bestMatch = startCode;
            }
          }
          if (bestMatch >= 0) {
            // Look for whitespace before start pattern, >= 50% of width of start pattern
            if (Endorsements.endorse(row.isRange(Endorsements.endorse(ApproxMath.max(0, patternStart - (i - patternStart) / 2)), Endorsements.endorse(patternStart),
                false))) {
              return new int[]{Endorsements.endorse(patternStart), i, Endorsements.endorse(bestMatch)};
            }
          }
          patternStart += counters[0] + counters[1];
          for (int y = 2; y < patternLength; y++) {
            counters[y - 2] = counters[y];
          }
          counters[patternLength - 2] = 0;
          counters[patternLength - 1] = 0;
          counterPosition--;
        } else {
          counterPosition++;
        }
        counters[counterPosition] = 1;
        isWhite = !isWhite;
      }
    }
    throw NotFoundException.getNotFoundInstance();
  }

  private static @Approx int decodeCode(BitArray row, @Approx int[] counters, int rowOffset) throws NotFoundException {
    recordPattern(row, rowOffset, counters);
    @Approx int bestVariance = MAX_AVG_VARIANCE; // worst variance we'll accept
    @Approx int bestMatch = -1;
    for (int d = 0; d < CODE_PATTERNS.length; d++) {
      @Approx int[] pattern = CODE_PATTERNS[d];
      @Approx int variance = patternMatchVariance(counters, pattern, MAX_INDIVIDUAL_VARIANCE);
      if (Endorsements.endorse(variance < bestVariance)) {
        bestVariance = variance;
        bestMatch = d;
      }
    }
    // TODO We're overlooking the fact that the STOP pattern has 7 values, not 6.
    if (Endorsements.endorse(bestMatch >= 0)) {
      return bestMatch;
    } else {
      throw NotFoundException.getNotFoundInstance();
    }
  }

  public Result decodeRow(int rowNumber, BitArray row, Hashtable hints)
      throws NotFoundException, FormatException, ChecksumException {

    int[] startPatternInfo = findStartPattern(row);
    @Approx int startCode = startPatternInfo[2];
    @Approx int codeSet;
    switch (Endorsements.endorse(startCode)) {
      case CODE_START_A:
        codeSet = CODE_CODE_A;
        break;
      case CODE_START_B:
        codeSet = CODE_CODE_B;
        break;
      case CODE_START_C:
        codeSet = CODE_CODE_C;
        break;
      default:
        throw FormatException.getFormatInstance();
    }

    boolean done = false;
    boolean isNextShifted = false;

    StringBuffer result = new StringBuffer(20);
    int lastStart = startPatternInfo[0];
    int nextStart = startPatternInfo[1];
    @Approx int[] counters = new @Approx int[6];

    @Approx int lastCode = 0;
    @Approx int code = 0;
    @Approx int checksumTotal = startCode;
    @Approx int multiplier = 0;
    @Approx boolean lastCharacterWasPrintable = true;

    while (!done) {

      boolean unshift = isNextShifted;
      isNextShifted = false;

      // Save off last code
      lastCode = code;

      // Decode another code from image
      code = decodeCode(row, counters, nextStart);

      // Remember whether the last code was printable or not (excluding CODE_STOP)
      if (Endorsements.endorse(code != CODE_STOP)) {
        lastCharacterWasPrintable = true;
      }

      // Add to checksum computation (if not CODE_STOP of course)
      if (Endorsements.endorse(code != CODE_STOP)) {
        multiplier++;
        checksumTotal += multiplier * code;
      }

      // Advance to where the next code will to start
      lastStart = nextStart;
      for (int i = 0; i < counters.length; i++) {
        nextStart += counters[i];
      }

      // Take care of illegal start codes
      switch (Endorsements.endorse(code)) {
        case CODE_START_A:
        case CODE_START_B:
        case CODE_START_C:
          throw FormatException.getFormatInstance();
      }

      switch (codeSet) {

        case CODE_CODE_A:
          if (Endorsements.endorse(code < 64)) {
            result.append((char) Endorsements.endorse(' ' + code));
          } else if (Endorsements.endorse(code < 96)) {
            result.append((char) Endorsements.endorse(code - 64));
          } else {
            // Don't let CODE_STOP, which always appears, affect whether whether we think the last
            // code was printable or not.
            if (Endorsements.endorse(code != CODE_STOP)) {
              lastCharacterWasPrintable = false;
            }
            switch (Endorsements.endorse(code)) {
              case CODE_FNC_1:
              case CODE_FNC_2:
              case CODE_FNC_3:
              case CODE_FNC_4_A:
                // do nothing?
                break;
              case CODE_SHIFT:
                isNextShifted = true;
                codeSet = CODE_CODE_B;
                break;
              case CODE_CODE_B:
                codeSet = CODE_CODE_B;
                break;
              case CODE_CODE_C:
                codeSet = CODE_CODE_C;
                break;
              case CODE_STOP:
                done = true;
                break;
            }
          }
          break;
        case CODE_CODE_B:
          if (Endorsements.endorse(code < 96)) {
            result.append((char) Endorsements.endorse(' ' + code));
          } else {
            if (Endorsements.endorse(code != CODE_STOP)) {
              lastCharacterWasPrintable = false;
            }
            switch (Endorsements.endorse(code)) {
              case CODE_FNC_1:
              case CODE_FNC_2:
              case CODE_FNC_3:
              case CODE_FNC_4_B:
                // do nothing?
                break;
              case CODE_SHIFT:
                isNextShifted = true;
                codeSet = CODE_CODE_C;
                break;
              case CODE_CODE_A:
                codeSet = CODE_CODE_A;
                break;
              case CODE_CODE_C:
                codeSet = CODE_CODE_C;
                break;
              case CODE_STOP:
                done = true;
                break;
            }
          }
          break;
        case CODE_CODE_C:
          if (Endorsements.endorse(code < 100)) {
            if (Endorsements.endorse(code < 10)) {
              result.append('0');
            }
            result.append(Endorsements.endorse(code));
          } else {
            if (Endorsements.endorse(code != CODE_STOP)) {
              lastCharacterWasPrintable = false;
            }
            switch (Endorsements.endorse(code)) {
              case CODE_FNC_1:
                // do nothing?
                break;
              case CODE_CODE_A:
                codeSet = CODE_CODE_A;
                break;
              case CODE_CODE_B:
                codeSet = CODE_CODE_B;
                break;
              case CODE_STOP:
                done = true;
                break;
            }
          }
          break;
      }

      // Unshift back to another code set if we were shifted
      if (unshift) {
        switch (Endorsements.endorse(codeSet)) {
          case CODE_CODE_A:
            codeSet = CODE_CODE_C;
            break;
          case CODE_CODE_B:
            codeSet = CODE_CODE_A;
            break;
          case CODE_CODE_C:
            codeSet = CODE_CODE_B;
            break;
        }
      }

    }

    // Check for ample whitespace following pattern, but, to do this we first need to remember that
    // we fudged decoding CODE_STOP since it actually has 7 bars, not 6. There is a black bar left
    // to read off. Would be slightly better to properly read. Here we just skip it:
    int width = row.getSize();
    while (nextStart < width && Endorsements.endorse(row.get(nextStart))) {
      nextStart++;
    }
    if (!row.isRange(nextStart, Math.min(width, nextStart + (nextStart - lastStart) / 2),
        false)) {
      throw NotFoundException.getNotFoundInstance();
    }

    // Pull out from sum the value of the penultimate check code
    checksumTotal -= multiplier * lastCode;
    // lastCode is the checksum then:
    if (Endorsements.endorse(checksumTotal % 103 != lastCode)) {
      throw ChecksumException.getChecksumInstance();
    }

    // Need to pull out the check digits from string
    int resultLength = result.length();
    // Only bother if the result had at least one character, and if the checksum digit happened to
    // be a printable character. If it was just interpreted as a control code, nothing to remove.
    if (Endorsements.endorse(resultLength > 0 && lastCharacterWasPrintable)) {
      if (Endorsements.endorse(codeSet == CODE_CODE_C)) {
        result.delete(resultLength - 2, resultLength);
      } else {
        result.delete(resultLength - 1, resultLength);
      }
    }

    String resultString = result.toString();

    if (resultString.length() == 0) {
      // Almost surely a false positive
      throw FormatException.getFormatInstance();
    }

    float left = (float) (startPatternInfo[1] + startPatternInfo[0]) / 2.0f;
    float right = (float) (nextStart + lastStart) / 2.0f;
    return new Result(
        resultString,
        null,
        new ResultPoint[]{
            new ResultPoint(left, (float) rowNumber),
            new ResultPoint(right, (float) rowNumber)},
        BarcodeFormat.CODE_128);

  }

}
