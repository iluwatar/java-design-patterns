package com.iluwatar.blackboard;

import java.util.ArrayList;


/**
 * In the blackboard pattern, a blackboard can be read and written by different knowledge sources.
 * The control would pick a knowledge source with highest priority.
 */
public class App {

  /**
   * Main method.
   *
   * @param args runtime arguments
   */
  public static void main(String[] args) {
    final ArrayList<Operator> operators1 = new ArrayList<>();
    operators1.add(new Operator(0, 2));
    operators1.add(new Operator(3, -1));
    operators1.add(new Operator(4, 4));

    final ArrayList<Operator> operators2 = new ArrayList<>();
    operators2.add(new Operator(1, 1));
    operators2.add(new Operator(2, -3));
    operators2.add(new Operator(4, -5));

    final ArrayList<Operator> operators3 = new ArrayList<>();
    operators3.add(new Operator(0, -2));
    operators3.add(new Operator(1, 3));
    operators3.add(new Operator(2, -5));

    final Blackboard blackboard = new Blackboard(5);
    final KnowledgeSource ks1 = new KnowledgeSource(blackboard, operators1, 0);
    final KnowledgeSource ks2 = new KnowledgeSource(blackboard, operators2, 1);
    final Control control = new Control();
    control.knowledgeSources.add(ks1);
    control.knowledgeSources.add(ks2);
    control.executeKnowledgeSource(control.selectKnowledgeSource());
    blackboard.showState();
    final KnowledgeSource ks3 = new KnowledgeSource(blackboard, operators3, 0);
    control.knowledgeSources.add(ks3);
    control.executeKnowledgeSource(control.selectKnowledgeSource());
    blackboard.showState();
    control.executeKnowledgeSource(control.selectKnowledgeSource());
    blackboard.showState();
  }
}
