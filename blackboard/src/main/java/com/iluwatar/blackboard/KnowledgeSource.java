package com.iluwatar.blackboard;

import java.util.ArrayList;

/**
 * Knowledge sources can be seen as specialists in sub-elds of the global application and are
 * only able to solve sub-problems.
 */

public class KnowledgeSource {

  /**
   * The blackboard it needs to read and write.
   */
  private final Blackboard blackboard;

  /**
   * List of operates it needs to do on the blackboard.
   */
  private final ArrayList<Operator> operatorList;

  /**
   * The integer determines whether the knowledgesource should write
   * in the blackboard's current state.
   */
  private final int activeNum;

  /**
   * Initialize a KnowledgeSource with a blackboard, the operations it needs to do on the blackboard
   * and the activeNum.
   */

  public KnowledgeSource(Blackboard blackboard, ArrayList<Operator> operatorList, int activeNum) {
    this.blackboard = blackboard;
    this.activeNum = activeNum;
    this.operatorList = new ArrayList<>();
    this.operatorList.addAll(operatorList);
  }

  /**
   * Update the blackboard if the condition is satisfied. In this program, we use the
   * sum of the blackboard's feature's elements.
   */
  public void execCondition() {
    int state = 0;
    for (int i = 0; i < blackboard.dimension; i++) {
      state += blackboard.access(i);
    }
    if (state % 2 == activeNum) {
      blackboard.update(operatorList);
    }
  }
}
