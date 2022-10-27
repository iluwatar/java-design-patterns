/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.ruleengine;


public class Candidate {
  // Add private fields here
  private int lengthOfCurrentResidence;
  private String englishProficiency;
  private boolean isNominatedOccupationOnTheList;
  private boolean isSmallBusinessOwner;
  private int smallBusinessTurnover;
  private int lengthOfEmployment;
  private int yearsOfStudy;
  private String tertiaryQualification;
  private int assets;
  private String closeFamilyTies;

  /**
   * Get years of study from the candidate.
   *
   * @return int yearsOfStudy : years of study from the candidate
   * @author Dehao Liu
   */
  public int getYearsOfStudy() {
    return yearsOfStudy;
  }

  /**
   * Set years of study to the candidate.
   *
   * @param yearsOfStudy : updated years of study.
   * @author Dehao Liu
   */
  public void setYearsOfStudy(int yearsOfStudy) {
    this.yearsOfStudy = yearsOfStudy;
  }

  /**
   * Get length of employment from the candidate.

   * @return int lengthOfEmployment: length of employment from the candidate
   * @author Dehao Liu
   */
  public int getLengthOfEmployment() {
    return lengthOfEmployment;
  }

  /**
   * Set length of employment to the candidate.
   * @param lengthOfEmployment : new length of employment to the candidate.
   * @author Dehao Liu
   */
  public void setLengthOfEmployment(int lengthOfEmployment) {
    this.lengthOfEmployment = lengthOfEmployment;
  }

  /**
   * get length of TertiaryQualificatio to the candidate.

   * @author Dehao Liu
   */
  public String getTertiaryQualification() {
    return tertiaryQualification;
  }

  /**
   * Set length of TertiaryQualificatio to the candidate.

   * @author Dehao Liu
   */
  public void setTertiaryQualification(String tertiaryQualification) {
    this.tertiaryQualification = tertiaryQualification;
  }

  /**
   * get length of Assets to the candidate.

   * @author Dehao Liu
   */
  public int getAssets() {
    return assets;
  }

  /**
   * Set length of Assets to the candidate.

   * @author Dehao Liu
   */
  public void setAssets(int assets) {
    this.assets = assets;
  }

  /**
   * get length of CloseFamilyTies to the candidate.

   * @author Dehao Liu
   */
  public String getCloseFamilyTies() {
    return closeFamilyTies;
  }


  /**
   * set length of CloseFamilyTies to the candidate.

   * @author Dehao Liu
   */
  public void setCloseFamilyTies(String closeFamilyTies) {
    this.closeFamilyTies = closeFamilyTies;
  }

  /**
   * Get length of current residence from the candidate.
   *
   * @return int lengthOfCurrentResidence: length of current residence from the candidate
   * @author Harry Li
   */
  public int getLengthOfCurrentResidence() {
    return lengthOfCurrentResidence;
  }

  /**
   * Set length of current residence to the candidate.
   *
   * @param lengthOfCurrentResidence : new length of current residence.
   * @author Harry Li
   */
  public void setLengthOfCurrentResidence(int lengthOfCurrentResidence) {
    this.lengthOfCurrentResidence = lengthOfCurrentResidence;
  }

  /**
   * Get English proficiency from the candidate.
   *
   * @return String englishProficiency : the candidate's English proficiency.
   * @author Harry Li
   */
  public String getEnglishProficiency() {
    return englishProficiency;
  }

  /**
   * Set English proficiency to the candidate.
   *
   * @param englishProficiency : new value to the candidate's English proficiency
   * @author Harry Li
   */
  public void setEnglishProficiency(String englishProficiency) {
    this.englishProficiency = englishProficiency;
  }

  /**
   * Check if the candidate's occupation is on the designated skill list.
   *
   * @return nominatedOccupationOnTheList: true if the candidate's occupation is on the list, false otherwise.
   * @author Harry Li
   */
  public boolean isNominatedOccupationOnTheList() {
    return isNominatedOccupationOnTheList;
  }

  /**
   * Set if the candidate's occupation is on the designated skill list.
   *
   * @param nominatedOccupationOnTheList : true if the candidate's occupation is on the list, false otherwise.
   * @author Harry Li
   */
  public void setNominatedOccupationOnTheList(boolean nominatedOccupationOnTheList) {
    isNominatedOccupationOnTheList = nominatedOccupationOnTheList;
  }

  /**
   * Check if the candidate is a small business owner.
   *
   * @return boolean isSmallBusinessOwner : true if the candidate is a small business owner, false otherwise
   * @author Harry Li
   */
  public boolean isSmallBusinessOwner() {
    return isSmallBusinessOwner;
  }

  /**
   * Set small business owner to the candidate.
   *
   * @param smallBusinessOwner : new small business owner
   * @author Harry Li
   */
  public void setSmallBusinessOwner(boolean smallBusinessOwner) {
    isSmallBusinessOwner = smallBusinessOwner;
  }

  /**
   * Get small business turnover from the candidate.
   *
   * @return int smallBusinessTurnover: the candidate's small business turnover.
   * @author Harry Li
   */
  public int getSmallBusinessTurnover() {
    return smallBusinessTurnover;
  }

  /**
   * Set small business turnover to the candidate.
   *
   * @param smallBusinessTurnover : new small business turnover.
   * @author Harry Li
   */
  public void setSmallBusinessTurnover(int smallBusinessTurnover) {
    this.smallBusinessTurnover = smallBusinessTurnover;
  }
}
