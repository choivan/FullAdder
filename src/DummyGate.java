import java.util.ArrayList;

/**
 * User: yifancai
 * Date: 1/12/14
 * Time: 12:54 AM
 */
public class DummyGate extends LogicGateBase {

    @Override
    public void setInputs(ArrayList<LogicGateBase> input) throws GateIOException {
        if (input.size() > 1) {
            throw new GateIOException(this.getClass().getName() + ": too much inputs");
        }
        super.setInputs(input);
    }

    @Override
    public void updateSelf() {
        if (getInputs().size() > 0) {
            getPotential().setPotentialValue(getInputs().get(0).getPotential().getPotentialValue());
        }
    }
}
