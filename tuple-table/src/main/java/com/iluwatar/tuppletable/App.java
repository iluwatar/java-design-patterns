package com.iluwatar.tuppletable;


import java.sql.SQLException;

public class App {

    public static void main(String[] args) throws SQLException {
        MemberTupleDAO mtd = new MemberTupleDAO();
        MemberDTO member = mtd.findMember(1);
        System.out.println(member.getFirstname() + " " + member.getLastname());
        System.out.println(member.getFreePasses());
        System.out.println(member.getCity());
        System.out.println(member.getAddress1());
        member.setMemberNumber(4);
        member.setFirstname("Atif");
        member.setLastname("Ahmed");
        member.setAddress1("USA");
        member.setAddress2("USA");
        member.setCity("Columbus");
        member.setState("Ohio");
        member.setZip("12345");
        mtd.saveMember(member);
        member = mtd.findMember(4);
        System.out.println(member.getFirstname() + " " + member.getLastname());
    }
}
