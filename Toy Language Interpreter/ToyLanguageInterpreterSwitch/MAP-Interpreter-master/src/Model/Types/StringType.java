package Model.Types;

import Model.Values.StringValue;
import Model.Values.Value;

public class StringType implements Type{

    public boolean equals(Object another){
        return another instanceof StringType;
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    @Override
    public Type deepCopy() {
        return new StringType();
    }

    @Override
    public String toString() {
        return "String ";
    }
}
