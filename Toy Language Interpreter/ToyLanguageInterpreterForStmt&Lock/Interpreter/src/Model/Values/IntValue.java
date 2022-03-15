package Model.Values;

import Model.Types.IntType;
import Model.Types.StringType;
import Model.Types.Type;

public class IntValue implements Value {
    private int val;

    public IntValue(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public boolean equals(Object another){
        return another instanceof IntValue;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value deepCopy() {
        return new IntValue(val);
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }
}
