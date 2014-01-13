/**
 * User: yifancai
 * Date: 1/11/14
 * Time: 11:21 PM
 */
public class NANDGate extends ANDGate {

    @Override
    public void updateSelf() {
        super.updateSelf();
        Potential myPot = getPotential();
        myPot.reverse();
    }
}
