package com.iluwatar.blackboard;

import java.util.ArrayList;

/**
 * Control is a complex scheduler implementing a fixed strategy. It computes a priority
 * for every enabled knowledge source. For simplicity, we think the first knowledgeSource
 * in the knowledgeSourceList has the highest priority.
 */
public class Control {

  /**
   * List of knowledgesources needing to be served.
   */
  public ArrayList<KnowledgeSource> knowledgeSources = new ArrayList<>();

  /**
   * Return the first knowledgeSource in the knowledgeSourceList.
   */
  public KnowledgeSource selectKnowledgeSource() {
    if (knowledgeSources.size() > 0) {
      return knowledgeSources.get(0);
    } else {
      return null;
    }
  }

  /**
   * Execute the given knowledgeSource.
   */
  public void executeKnowledgeSource(KnowledgeSource knowledgeSource) {
    knowledgeSource.execCondition();
    knowledgeSources.remove(knowledgeSource);
  }
}
