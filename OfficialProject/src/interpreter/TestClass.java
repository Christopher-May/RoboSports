package interpreter;

import units.Unit;
import units.UnitFactory;

public class TestClass {
  public static void main(String[] args/* , TeamColour team */) {
    System.out.println("Begin faux forth interpreter!");


    /*
     * String fudgedForth = "variable canshoot ;" + "	: canShoot? canShoot ? ; ";
     */
    // String fudgedForth= "123 ( this should be ignored ) 456 + .\"hello world many more spaces \"
    // : forthPlusMinus + - ; ";

    // String fudgedForth =": stutter 0 do dup . 2 0 do nothing loop loop ; .\"um\" 3 stutter ";

    // String fudgedForth = ": newWord ( this text not processed ) 5 2 + ; newWord ";

    // String fudgedForth = ": testDoWord 5 0 do I + loop ; 0 testDoWord "; ";

    // String fudgedForth = "variable canShoot ; true canShoot ! canShoot ? ";

    /*
     * String fudgedForth = " : empty? drop drop true ; " +
     * ": shoot! 42 ; : play ( -- ) 5 0 do I 1 empty? " +
     * "if .\"no one there\" else I 1 shoot! leave then loop ; ";
     */

    String fudgedForth = " begin false until  ";


    /** Sitting Duck */
    /*
     * String fudgedForth = "variable moved ; ( have I moved? ) "+
     * "moved false !                      "+ ": play moved ? if                  "+
     * "                 ( do nothing )    "+ "               else                "+
     * "                   move move move  "+ "                   moved true !    "+
     * "               then ;            ";
     */

    /** Statue */
    // String fudgedForth =": play ( -- ) ( do nothing ) ;";

    // String fudgedForth = " .\"um um\" .\"um um\" = ";

    /** Centralizer */

    /*
     * String fudgedForth = "variable moved ; ( have I moved? )         "+
     * ": moved? moved ? ;                         "+ "moved false !                              "+
     * "                                           "+ ": firstMove ( move to center first )       "+
     * "       moved? if                           "+ "                ( already moved )          "+
     * "              else                         "+ "                move move move             "+
     * "                moved true !               "+ "              then ;                       "+
     * "                                           "+ "variable shot  ; ( have I shot this play? )"+
     * ": canShoot? ( -- b ) ( shot available? )   "+ "            shot? ;                       "+
     * "                                           "+ ": shoot!! ( id ir -- ) ( shoot if allowed )"+
     * "          canShoot? if                     "+ "             pop pop      ( remove ir id ) "+
     * "          else                             "+ "              shoot!      ( really shoot ) "+
     * "              shot true ! ( remember it )  "+ "            then                           "+
     * "          then ;                           "+ "                                           "+
     * ": doNotShoot ( id ir -- ) ( pretend shot ) "+ "             pop pop ;                     "+
     * "                                           "+ ": enemy? ( s -- b ) ( decide if enemy )    "+
     * "         team <> ;                         "+ "                                           "+
     * ": nonZeroRange? ( i -- b i )               "+ "             dup 0 <> ;                    "+
     * "                                           "+ ": tryShooting! ( ih id ir st -- )          "+
     * "               enemy?                      "+ "               swap nonZeroRange? rot      "+
     * "             and if                        "+ "                   shoot!!                 "+
     * "                 else                      "+ "                   doNotShoot              "+
     * "                 then pop ( remove ih ) ;  "+ "                                           "+
     * ": shootEveryone ( try shot at all targets )"+ "       scan! 1 - dup 0 <                   "+
     * "       if                                  "+ "         ( no one to shoot at )            "+
     * "       else 0 do                           "+ "                I identify! tryShooting!   "+
     * "              loop                         "+ "       then ;                              "+
     * "                                           "+ ": play ( -- )                              "+
     * "       firstMove                           "+ "       shot false ! ( prepare to shoot )   "+
     * "       shootEveryone ; play ";
     */


    /** Creeper.jsn **/
    /*
     * String fudgedForth = "variable moved ; ( have I moved? )         "+
     * ": moved? moved ? ;                         "+ "moved false !                              "+
     * "                                           "+ ": firstMove ( align along left edge )      "+
     * "       moved? if                           "+ "                ( already moved )          "+
     * "              else                         "+ "                5 turn!                    "+
     * "              then ;                       "+ "                                           "+
     * ": edgeMove ( -- ) ( move along an edge )   "+ "           0 check!                        "+
     * "           .\"OUT OF BOUNDS\" =            "+ "           if                              "+
     * "             1 turn!                       "+ "           else                            "+
     * "             ( )                           "+ "           then move! ;                    "+
     * "                                           "+ ": noMovesLeft? ( -- b ) ( no moves left? ) "+
     * "             movesLeft 0 <> ;              "+ "                                           "+
     * "variable shot ; ( have I shot this play? ) "+ ": canShoot? ( -- b ) ( shot available? )   "+
     * "            shot? ;                        "+ "                                           "+
     * ": shoot!! ( id ir -- ) ( shoot if allowed )"+ "          canShoot? if                     "+
     * "             pop pop      ( remove ir id ) "+ "          else                             "+
     * "              shoot!      ( really shoot ) "+ "              shot true ! ( remember it )  "+
     * "            then                           "+ "          then ;                           "+
     * "                                           "+ ": doNotShoot ( id ir -- ) ( pretend shot ) "+
     * "             pop pop ;                     "+ "                                           "+
     * ": enemy? ( s -- b ) ( decide if enemy )    "+ "         team <> ;                         "+
     * "                                           "+ ": nonZeroRange? ( i -- b i )               "+
     * "             dup 0 <> ;                    "+ "                                           "+
     * ": tryShooting! ( ih id ir st -- )          "+ "             enemy?                        "+
     * "             swap nonZeroRange? rot        "+ "             and if                        "+
     * "                   shoot!!                 "+ "                 else                      "+
     * "                   doNotShoot              "+ "                 then pop ( remove ih ) ;  "+
     * "                                           "+ ": shootEveryone ( try shot at all targets )"+
     * "       scan!                               "+ "       1 -                                 "+
     * "       dup 0 < if                          "+ "         ( no one to shoot at )            "+
     * "       else 0 do                           "+ "                I identify! tryShooting!   "+
     * "              loop                         "+ "       then ;                              "+
     * "                                           "+ ": play ( -- )                              "+
     * "       firstMove                           "+ "       shot false ! ( prepare to shoot )   "+
     * "       begin                               "+ "         edgeMove                          "+
     * "         shootEveryone                     "+
     * "       noMovesLeft? until ; play ;               ";
     */
    // String fudgedForth =".\"shot\" ";


    // String fudgedForth = "false ";

    System.out.println("The faux interpretter will be evaluating: " + fudgedForth + "\n --------");

    Unit test = UnitFactory.getTestUnit();
    ForthCodeParser interp = new ForthCodeParser(test, null);
    System.out.println("The faux interpretter will be evaluating: " + fudgedForth + "\n --------");

    interp.parseCode(fudgedForth);
    interp.printStack();
  }
}
