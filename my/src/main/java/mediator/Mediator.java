//package mediator;
//
//import java.util.ArrayList;
//import java.util.List;
// Todo 未完成
//class Test {
//    public static void main(String[] args) {
//        MediatorImpl mediator = new MediatorImpl();
//        var hobbit = new Hobbit();
//        var wizard = new Wizard();
//        var rogue = new Rogue();
//        var hunter = new Hunter();
//
//        // add mediator members
//        mediator.addMember(hobbit);
//        mediator.addMember(wizard);
//        mediator.addMember(rogue);
//        mediator.addMember(hunter);
//        System.out.println();
//
//        // 执行行动->其他成员被Platform通知
//        hobbit.actionAll(Action.ENEMY);
//        System.out.println();
//        wizard.actionAll(Action.TALE);
//        System.out.println();
//        rogue.actionAll(Action.GOLD);
//        System.out.println();
//        hunter.actionAll(Action.HUNT);
//    }
//}
//
//interface PlatformMember {
//
//    void joinedPlatform(Mediator mediator);
//
//    void send(String content);
//
//    void receive(String content);
//}
//
//class PlatformMemberBase implements PlatformMember {
//    private String name;
//    protected Mediator mediator;
//
//    @Override
//    public void joinedPlatform(Mediator mediator) {
//        System.out.println(this + " joins the Platform");
//        this.mediator = mediator;
//    }
//
//    @Override
//    public void send(String content) {
//        System.out.println(name + " : 发送 : " + content);
//    }
//
//    @Override
//    public void receive(String content) {
//        System.out.println(name + " : 接收 : " + content);
//        if (mediator != null) {
//            mediator.notifyOtherReceive(this, content);
//        }
//    }
//}
//
////class Hobbit extends PlatformMemberBase {
////
////    @Override
////    public String toString() {
////        return "Hobbit";
////    }
////
////}
////
////class Rogue extends PlatformMemberBase {
////
////    @Override
////    public String toString() {
////        return "Rogue";
////    }
////
////}
////
////class Wizard extends PlatformMemberBase {
////
////    @Override
////    public String toString() {
////        return "Wizard";
////    }
////
////}
////
////class Hunter extends PlatformMemberBase {
////
////    @Override
////    public String toString() {
////        return "Hunter";
////    }
////}
//
//public interface Mediator {
//
//    void addMember(PlatformMember member);
//
//    void notifyOtherReceive(PlatformMember member, String content);
//
//}
//
//class MediatorImpl implements Mediator {
//
//    private final List<PlatformMember> members;
//
//    public MediatorImpl() {
//        members = new ArrayList<>();
//    }
//
//    @Override
//    public void addMember(PlatformMember member) {
//        members.add(member);
//        member.joinedPlatform(this);
//    }
//
//    @Override
//    public void notifyOtherReceive(PlatformMember platformMember, String content) {
//        for (var member : members) {
//            if (!member.equals(actor)) {
//                member.actionSelf(action);
//            }
//        }
//    }
//}
//
//
////// 中介ＱＱ群
////interface MediatorQQGroup {
////    void addMember(HouseTransactionMember member);
////}
////
////// 房子-交易成员
////interface HouseTransactionMember {
////    // 加入到交易平台
////    void joinedPlatform(Mediator mediator);
////
////    // 加入到交易平台
////    void seller(Mediator mediator);
////}
////
////// 房子-交易成员-Base
////class HouseTransactionMemberBase implements HouseTransactionMember {
////    private Mediator mediator;
////
////    // 加入到交易平台
////    @Override
////    public void joinedPlatform(Mediator mediator) {
////        this.mediator = mediator;
////    }
////}
////
////// 房子-卖方
////class HouseSeller implements HouseTransactionMemberBase {
////
////    @Override
////    public void joinedPlatform(Mediator mediator) {
////
////    }
////}
////
////// 房子-买方
////class HouseBuyer {
////
////}