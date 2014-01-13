import java.util.ArrayList;

/**
 * User: yifancai
 * Date: 1/11/14
 * Time: 11:39 PM
 */
public class OneBitFullAdder extends LogicGateBase {

    private final int _input1 = 0;
    private final int _input2 = 1;
    private final int _inputC = 2; // carry

    // dummy gates used as the facade of the circuit
    private LogicGateBase _inputGate1;
    private LogicGateBase _inputGate2;
    private LogicGateBase _inputGateC;

    private DummyGate _outputO; // output for current bit
    private DummyGate _outputC; // output for carry sign

    public OneBitFullAdder() {
        super();
        _outputO = new DummyGate();
        _outputC = new DummyGate();
    }

    public LogicGateBase getOutputO() {
        return _outputO;
    }

    public LogicGateBase getOutputC() {
        return _outputC;
    }

    private void initInnerCircuit() throws GateIOException {
        _inputGate1 = getInputs().get(_input1);
        _inputGate2 = getInputs().get(_input2);
        _inputGateC = getInputs().get(_inputC);
        ArrayList<LogicGateBase> twoInputs = new ArrayList<LogicGateBase>() {{
            add(_inputGate1);
            add(_inputGate2);
        }};
        final XORGate xorGate = new XORGate();
        final XNORGate xnorGate = new XNORGate();
        final ANDGate andGate = new ANDGate();
        final ORGate orGate = new ORGate();
        final NOTGate nCGate1 = new NOTGate();
        final NOTGate nCGate2 = new NOTGate();
        final ArrayList<LogicGateBase> fourAndGates = new ArrayList<LogicGateBase>();
        for (int i = 0; i < 4; ++i) {
            fourAndGates.add(new ANDGate());
        }
        final ArrayList<LogicGateBase> twoOrGates = new ArrayList<LogicGateBase>() {{
            add(new ORGate());
            add(new ORGate());
        }};
        xorGate.setInputs(twoInputs);
        xnorGate.setInputs(twoInputs);
        andGate.setInputs(twoInputs);
        orGate.setInputs(twoInputs);
        nCGate1.setInputs(new ArrayList<LogicGateBase>() {{
            add(_inputGateC);
        }});
        nCGate2.setInputs(new ArrayList<LogicGateBase>() {{
            add(_inputGateC);
        }});
        fourAndGates.get(0).setInputs(new ArrayList<LogicGateBase>() {{
            add(xorGate);
            add(nCGate1);
        }});
        fourAndGates.get(1).setInputs(new ArrayList<LogicGateBase>() {{
            add(xnorGate);
            add(_inputGateC);
        }});
        fourAndGates.get(2).setInputs(new ArrayList<LogicGateBase>() {{
            add(andGate);
            add(nCGate1);
        }});
        fourAndGates.get(3).setInputs(new ArrayList<LogicGateBase>() {{
            add(orGate);
            add(_inputGateC);
        }});
        twoOrGates.get(0).setInputs(new ArrayList<LogicGateBase>() {{
            add(fourAndGates.get(0));
            add(fourAndGates.get(1));
        }});
        twoOrGates.get(1).setInputs(new ArrayList<LogicGateBase>() {{
            add(fourAndGates.get(2));
            add(fourAndGates.get(3));
        }});
        _outputO.setInputs(new ArrayList<LogicGateBase>() {{
            add(twoOrGates.get(0));
        }});
        _outputC.setInputs(new ArrayList<LogicGateBase>() {{
            add(twoOrGates.get(1));
        }});
        getOutputs().add(_outputO);
        getOutputs().add(_outputC);
    }

    @Override
    public void setInputs(ArrayList<LogicGateBase> input) throws GateIOException {
        if (input.size() != 3) {
            throw new GateIOException(this.getClass().getName() + ": inputs size should be 3 (input 1, 2 and carry)");
        }

        super.setInputs_(input);

        initInnerCircuit();
    }

    @Override
    public void updateSelf() {
        _inputGate1.update();
        _inputGate2.update();
        _inputGateC.update();
    }

//    @Override
//    public LogicGateBase clone() {
//
//    }

    // test all 8 cases
    /*      CI      A       B       O       CO      */
    //      0       0       0       0       0
    //      0       0       1       1       0
    //      0       1       0       1       0
    //      0       1       1       0       1
    //      1       0       0       1       0
    //      1       0       1       0       1
    //      1       1       0       0       1
    //      1       1       1       1       1
    public static void main(String[] avgs) {
        OneBitFullAdder adder = new OneBitFullAdder();
        final DummyGate ga = new DummyGate();
        final DummyGate gb = new DummyGate();
        final DummyGate gc = new DummyGate();
        try {
            for (Potential.ValueType a : Potential.ValueType.values()) {
                for (Potential.ValueType b : Potential.ValueType.values()) {
                    for (Potential.ValueType c : Potential.ValueType.values()) {
                        System.out.println("Input --- a: " + a + " b: " + b + " carry: " + c);
                        ga.getPotential().setPotentialValue(a);
                        gb.getPotential().setPotentialValue(b);
                        gc.getPotential().setPotentialValue(c);
                        adder.setInputs(new ArrayList<LogicGateBase>() {{
                            add(ga);
                            add(gb);
                            add(gc);
                        }});
                        adder.update();
                        System.out.println("Output ~~ O: " + adder.getOutputO().getPotential().getPotentialValue() + " carry: " + adder.getOutputC().getPotential().getPotentialValue());
                        System.out.println();
                    }
                }
            }
        } catch (GateIOException e) {
            e.printStackTrace();
        }
    }
}
