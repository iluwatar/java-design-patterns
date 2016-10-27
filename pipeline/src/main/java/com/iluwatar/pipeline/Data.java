/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iluwatar.pipeline;
/**
 * List of seemingly garbage data that will be decoded to meaningful sentences
 * in 4 stages.
 */
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Ayush
 */
public class Data {
  private static Queue v;
    /**
     * @return Vector
     * @Data initialization
     */
  Data() {
    v = new LinkedList();
    String []list = {"g%2O%d2& Y$7z$A8)l !8E)h6&T ^1R&e9#Vo$7 ^p0*Mu)6J^ 9#xO(5"
            + "f* 3#Nw(1O!r1)B (4k&C3@iU&9q^ 9@Eh$5T$" ,
      "o^4D$ 7(I $5T)a2&Ht^7 )S5)gN@8i@H6(t (6E(m2^As(8 (E5*hT&8 ^l9^La)7 ^S7*e"
            + "K$5i)L7( e)1H^s2% T)0a^H3#t *0S&y6!As^6 $M7&uM(6 ^y0(M" ,
      "T^5n!R6)uB!0n#U0!s &7P)u7& D$9e#D4&nE(2 $d2!Na!5 @T2#hG!3i(N3( e^1H(t3% "
            + "F^7o$ 9@El@7D!d9$Im*9 )E8$hT^7 (n4%I #3e*T2)iK$6 $a1# W#1e^L4@f "
            + "(0Y!e3@Kn(4O@d9& N$2e^E1!rG*9 )a6^ D@4n$A4! g%2I&p9% E^4l)P3^rU%"
            + "2p# 8$A" ,
      "e$6M$a2^S )5E!h8&T !1l(L5$a &8E^r4*A !1E!w9@ E$9s$I9&lA^9e#R7& e!1W# 2^l"
            + "I^4t&n4$U *0r!E7!hT(7e)g7%Ot)3 !E2)uq(8I!n3%U $6E$b9* L%3l(A6! s"
            + "$3T!e1@L" ,
      "S#5e(H5*cI(2w@d2*Na#7S! 6(aN!0u*T3^ D(9n@A7@ e*0S@e1@Eh$3C^ 1*De(6T#s8@a"
            + "O@5t% 5!Gn!2i&T1$aE!9 $e0#Vo)6l% 3#I" ,
      "R@3e^H6#tO@2 &h7#Ca^3E# 9!mO!1r@F1$ t#3N!e0@Re%3F*f3@Id)6 ^R7&uo(7H* 6$1"
            + " !8E@r3$A *2P^o8*Tp#7A)l0& y*5M* 5(No@7 (K0#cO#7l)C3& e(5H&t6% D"
            + "%1n$A3$ g)9O&l1^B %6s^I6*hT^9 *n9&Ih)0T!i5(W )1k&C0&oL&9c) 2(Eh("
            + "7T(" ,
      "e#7C*n6&O (8t)A2! K(8c!A6#b (5E(m5)Oc%5 *O3&t (2M%i3%H #8d^E0^si^2V&D9(a"
            + " #1e$H1!S" ,
      "t^6N&a2)Ca(6V! 5%eR&1e)W1( S&6t#a5@eS$7 )O7(wT)3" ,
      "S$2s^A2*lG)2 $n8&Ek$6O$r5(B ^5e!H6^t ^7n&O7% p)2E@t2^S #0t)'6!No$3D(" ,
      "e)6B% 6&dL!0u@O0$w (1T&i0% t$5H%g9$Uo%3H)t6) Y!4l#L0$aI&0t$I5*nI@8 )I4! "
            + "N*1a(H0&t (6R%e2$Dr!9A$h0# S(8i^ 7!Se#3C(n6*Et^0N#e9#S %6m&O1&dN"
            + "%7a@R1! f(4O% 6*tS!9i)L0* a%2 $G0@nI$3t*I5@rW$6"};
    for (int i = 0;i < 10;++i) {
      v.add(list[i]);
    }
  }
  public static Queue data() {
    return v;
  }
    
}
