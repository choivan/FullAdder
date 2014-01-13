/**
 * User: yifancai
 * Date: 1/11/14
 * Time: 11:33 PM
 */
public class XNORGate extends XORGate {

    @Override
    public void updateSelf() {
        super.updateSelf();
        Potential myPot = getPotential();
        myPot.reverse();
    }

}
