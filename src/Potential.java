/**
 * User: yifancai
 * Date: 1/11/14
 * Time: 10:38 PM
 */
public class Potential {

    public enum ValueType {
        HIGH_POTENTIAL,
        LOW_POTENTIAL
    }

    private ValueType _potentialValue;

    public Potential() {
        _potentialValue = ValueType.LOW_POTENTIAL;
    }

    public ValueType getPotentialValue() {
        return _potentialValue;
    }

    public void setPotentialValue(ValueType v) {
        _potentialValue = v;
    }

    public boolean isHighPotential() {
        return _potentialValue == ValueType.HIGH_POTENTIAL;
    }

    public void reverse() {
        if (isHighPotential())
            _potentialValue = ValueType.LOW_POTENTIAL;
        else
            _potentialValue = ValueType.HIGH_POTENTIAL;
    }
}
