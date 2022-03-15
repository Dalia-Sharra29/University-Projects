package Model.Values;

import Model.Types.BoolType;
import Model.Types.StringType;
import Model.Types.Type;

public class BoolValue implements Value {
    private boolean val;

    public BoolValue(boolean val) {
        this.val = val;
    }

    public boolean getVal() {
        return val;
    }

    public boolean equals(Object another){
        return another instanceof BoolValue;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(val);
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }
}
