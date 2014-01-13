import java.util.ArrayList;

/**
 * User: yifancai
 * Date: 1/11/14
 * Time: 11:06 PM
 */
public class ANDGate extends LogicGateBase {

    @Override
    public void setInputs(ArrayList<LogicGateBase> input) throws GateIOException {
        if (input.size() != 2) {
            throw new GateIOException(this.getClass().getName() + ": inputs size should be 2");
        }
        super.setInputs(input);
    }

    @Override
    public void updateSelf() {
        if (getInputs().size() == 2) {
            Potential pot1 = getInputs().get(0).getPotential();
            Potential pot2 = getInputs().get(1).getPotential();
            Potential myPot = getPotential();
            if (pot1.isHighPotential() && pot2.isHighPotential()) {
                myPot.setPotentialValue(Potential.ValueType.HIGH_POTENTIAL);
            }
            else {
                myPot.setPotentialValue(Potential.ValueType.LOW_POTENTIAL);
            }
        }
    }

}
