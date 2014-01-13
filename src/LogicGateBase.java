import java.util.ArrayList;

/**
 * User: yifancai
 * Date: 1/11/14
 * Time: 10:27 PM
 */
public abstract class LogicGateBase {

    private ArrayList<LogicGateBase> _inputs;
    private ArrayList<LogicGateBase> _outputs;
    private Potential _potential;

    public LogicGateBase() {
        _inputs = new ArrayList<LogicGateBase>();
        _outputs = new ArrayList<LogicGateBase>();
        _potential = new Potential();
    }

    public void setInputs(ArrayList<LogicGateBase> inputs) throws GateIOException {
        // make sure in the subclass
        // always check if there is too much or less inputs, if so, raise exception
        setInputs_(inputs);
        for (LogicGateBase gb : _inputs) {
            if (!gb._outputs.contains(this)) {
                gb._outputs.add(this);
            }
        }
    }

    // will not connect, designed for private purpose
    protected void setInputs_(ArrayList<LogicGateBase> inputs) throws GateIOException {
        _inputs = inputs;
    }

    public ArrayList<LogicGateBase> getInputs() {
        return _inputs;
    }

    public ArrayList<LogicGateBase> getOutputs() {
        return _outputs;
    }

    public Potential getPotential() {
        return _potential;
    }

    protected void updateSelf() {  /* Override me */  }

    public void update() {
        // update self
        // have its own implementation based on gate type
        updateSelf();
        // update for all outputs logic gates
        for (LogicGateBase gb : _outputs) {
            gb.update();
        }
    }

//    public LogicGateBase clone() {
//        Class<? extends LogicGateBase> c = this.getClass();
//        LogicGateBase obj = null;
//        try {
//            obj = c.newInstance();
//            obj.setInputs(_inputs);
//            obj.setOutputs(_outputs);
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (GateIOException e) {
//            e.printStackTrace();
//        } finally {
//            return obj;
//        }
//    }

}
