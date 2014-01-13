import java.util.ArrayList;

/**
 * User: yifancai
 * Date: 1/11/14
 * Time: 10:48 PM
 */
public class NOTGate extends LogicGateBase {

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
            Potential myPot = getPotential();
            myPot.setPotentialValue(getInputs().get(0).getPotential().getPotentialValue());
            myPot.reverse();
        }
    }

    public static void main(String[] avgs) {
        NOTGate ng = new NOTGate();
        Potential pot = ng.getPotential();
        System.out.println("before --- " + pot.getPotentialValue());
        ArrayList<LogicGateBase> inputs = new ArrayList<LogicGateBase>();
        inputs.add(new NOTGate());
        //inputs.add(new NOTGate());
        try {
            ng.setInputs(inputs);
        } catch (GateIOException e) {
            e.printStackTrace();
        }

        ng.update();
        System.out.println("after --- " + pot.getPotentialValue());
    }
}
