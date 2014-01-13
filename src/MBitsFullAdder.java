import java.util.ArrayList;
import java.util.Random;

/**
 * User: yifancai
 * Date: 1/12/14
 * Time: 3:32 PM
 */
public class MBitsFullAdder extends LogicGateBase {

    private ArrayList<LogicGateBase> _adderComponents;
    private int _adderComponentsSize;

    private ArrayList<LogicGateBase> _outputPorts;

    public MBitsFullAdder() {
        super();
        _adderComponents = new ArrayList<LogicGateBase>();
        _outputPorts = new ArrayList<LogicGateBase>();
    }

    public ArrayList<LogicGateBase> getOutputPorts() {
        return _outputPorts;
    }

    // size > 0: set components size to the target size
    // size <= 0: remove all components
    public void setAdderComponentsSize(int size) {
        _adderComponents.clear();
        _outputPorts.clear();
        if (size <= 0) {
            _adderComponentsSize = 0;
            return;
        }
        _adderComponentsSize = size;
        for (int i = 0; i < size; ++i) {
            _adderComponents.add(new OneBitFullAdder());
        }
    }

    private void initInnerCircuit() throws GateIOException {
        ArrayList<LogicGateBase> inputs = new ArrayList<LogicGateBase>();
        inputs.addAll(getInputs().subList(0, 3));
        _adderComponents.get(0).setInputs(inputs);

        // connect 1 bit adders
        for (int i = 1; i < _adderComponentsSize; ++i) {
            OneBitFullAdder oneBitFullAdder1 = (OneBitFullAdder)_adderComponents.get(i - 1);
            OneBitFullAdder oneBitFullAdder2 = (OneBitFullAdder)_adderComponents.get(i);
            LogicGateBase adder1OutputC = oneBitFullAdder1.getOutputC();

            // synthesize input
            inputs.clear();
            inputs.addAll(getInputs().subList(i * 2 + 1, i * 2 + 3)); // input a, b
            inputs.add(adder1OutputC); // input c
            oneBitFullAdder2.setInputs(inputs);
        }

        // connect adders to its output ports
        for (int i = 0; i < _adderComponentsSize; ++i) {
            OneBitFullAdder oneBitFullAdder = (OneBitFullAdder)_adderComponents.get(i);
            _outputPorts.add(oneBitFullAdder.getOutputO());
        }
    }

    @Override
    public void setInputs(ArrayList<LogicGateBase> input) throws GateIOException {
        if (_adderComponentsSize == 0) {
            throw new GateIOException(this.getClass().getName() + ": no adder components available. Call setAdderComponentsSize to set up first.");
        }
        if (input.size() != _adderComponentsSize * 2 + 1) {
            throw new GateIOException(this.getClass().getName() + ": input size not match adder components size");
        }

        super.setInputs_(input);

        initInnerCircuit();
    }

    @Override
    public void updateSelf() {
        for (LogicGateBase gb : _adderComponents) {
            gb.update();
        }
    }

    // test 1 + 3
    public static void main(String[] avgs) {
        final int m = 8;
        int a, b, k = 0;
        while (k ++ < 20) {
            a = (int)(Math.random() * 30);
            b = (int)(Math.random() * 50);
        MBitsFullAdder mBitsFullAdder = new MBitsFullAdder();
        mBitsFullAdder.setAdderComponentsSize(m);
        ArrayList<LogicGateBase> number1 = reversedBitsFromNumber(a, m);
        ArrayList<LogicGateBase> number2 = reversedBitsFromNumber(b, m);
        ArrayList<LogicGateBase> inputs = new ArrayList<LogicGateBase>();
        inputs.add(new DummyGate());
        for (int i = 0; i < m; ++i) {
            inputs.add(number1.get(i));
            inputs.add(number2.get(i));
        }
        try {
            mBitsFullAdder.setInputs(inputs);
        } catch (GateIOException e) {
            e.printStackTrace();
        }
        mBitsFullAdder.update();
        ArrayList<LogicGateBase> output = mBitsFullAdder.getOutputPorts();

        int result = valueFromReversedBits(output);
        System.out.println("---   a: " + a + "   ---");
        for (int i = 0; i < number1.size(); ++i) {
            System.out.print((number1.get(i).getPotential().isHighPotential() ? 1 : 0) + " ");
        }
        System.out.println();
        System.out.println("---   b: " + b + "   ---");
        for (int i = 0; i < number2.size(); ++i) {
            System.out.print((number2.get(i).getPotential().isHighPotential() ? 1 : 0) + " ");
        }
        System.out.println();
        System.out.println("---  result  ---");
        for (int i = 0; i < output.size(); ++i) {
            System.out.print((output.get(i).getPotential().isHighPotential() ? 1 : 0) + " ");
        }
        System.out.println();
        System.out.println(a + " + " + b + " = " + result + "\n\n");
        }
    }

    public static ArrayList<LogicGateBase> reversedBitsFromNumber(int n, int size) {
        ArrayList<LogicGateBase> reversedBits = new ArrayList<LogicGateBase>();
        while (size > 0 && n > 0) {
            DummyGate dg = new DummyGate();
            if (n % 2 == 0) {
                dg.getPotential().setPotentialValue(Potential.ValueType.LOW_POTENTIAL);
            }
            else {
                dg.getPotential().setPotentialValue(Potential.ValueType.HIGH_POTENTIAL);
            }
            reversedBits.add(dg);
            n /= 2;
            --size;
        }
        while (size > 0) {
            DummyGate dg = new DummyGate();
            reversedBits.add(dg);
            --size;
        }
        return reversedBits;
    }

    public static int valueFromReversedBits(ArrayList<LogicGateBase> bits) {
        int ret = 0;
        for (int i = 0; i < bits.size(); ++i) {
            LogicGateBase gb = bits.get(i);
            if (gb.getPotential().isHighPotential()) {
                ret += (1 << i);
            }
        }
        return ret;
    }
}
