package com.iluwatar.blackboard;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The test for knowledgeSource, blackboard and control's action.
 */
public class BlackboardArchitectureTest {

  /**
   * Wrong message.
   */
  private String wrong = "Wrong";

  /**
   * Test whether a blackboard's initialization works well.
   */
  @Test
  public void testBlackboard(){
    final Blackboard blackboard=new Blackboard(3);
    assertEquals(blackboard.dimension,3,wrong);
    for (int i = 0; i<blackboard.dimension; i++){
      assertEquals(blackboard.access(i),0,wrong);
    }
    assertEquals(blackboard.currentState(),"0,0,0",wrong);
  }

  /**
   * Test whether a knowledgeSource's operators work well on a blackboard.
   */
  @Test
  public void testKnowledgeSource(){
    final Blackboard blackboard=new Blackboard(3);
    final ArrayList<Operator> operatorArrayList=new ArrayList<>();
    operatorArrayList.add(new Operator(0,1));
    operatorArrayList.add(new Operator(1,-2));
    operatorArrayList.add(new Operator(2,3));
    final KnowledgeSource knowledgeSource=new KnowledgeSource(blackboard,operatorArrayList,0);
    knowledgeSource.execCondition();
    assertEquals(blackboard.currentState(),"1,-2,3",wrong);
  }

  /**
   * Test whether a control component can choose the knowledgeSource with highest priority.
   */
  @Test
  public void testControl(){
    final Blackboard blackboard=new Blackboard(5);
    final Control control=new Control();

    final ArrayList<Operator> operators1=new ArrayList<>();
    operators1.add(new Operator(0,2));
    operators1.add(new Operator(3,-1));
    operators1.add(new Operator(4,4));

    final ArrayList<Operator> operators2=new ArrayList<>();
    operators2.add(new Operator(1,1));
    operators2.add(new Operator(2,-3));
    operators2.add(new Operator(4,-5));

    final ArrayList<Operator> operators3=new ArrayList<>();
    operators3.add(new Operator(0,-2));
    operators3.add(new Operator(1,3));
    operators3.add(new Operator(2,-5));

    final KnowledgeSource ks1=new KnowledgeSource(blackboard,operators1,0);
    final KnowledgeSource ks2=new KnowledgeSource(blackboard,operators2,1);
    control.knowledgeSources.add(ks1);
    control.knowledgeSources.add(ks2);
    control.executeKnowledgeSource(control.selectKnowledgeSource());
    assertEquals(blackboard.currentState(),"2,0,0,-1,4",wrong);
    final KnowledgeSource ks3=new KnowledgeSource(blackboard,operators3,0);
    control.knowledgeSources.add(ks3);
    control.executeKnowledgeSource(control.selectKnowledgeSource());
    assertEquals(blackboard.currentState(),"2,1,-3,-1,-1",wrong);
    control.executeKnowledgeSource(control.selectKnowledgeSource());
    assertEquals(blackboard.currentState(),"0,4,-8,-1,-1",wrong);
  }
}
