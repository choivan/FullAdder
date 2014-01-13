/**
 * User: yifancai
 * Date: 1/11/14
 * Time: 11:28 PM
 */
public class NORGate extends ORGate {

    @Override
    public void updateSelf() {
        super.updateSelf();
        Potential myPot = getPotential();
        myPot.reverse();
    }
}
