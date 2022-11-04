package top.sinkdev.cal.expression.unit.operators;

import top.sinkdev.cal.CalculatorConstants;
import top.sinkdev.cal.expression.unit.Operator;

public class AddOperator extends Operator {
    public AddOperator() {
        super(CalculatorConstants.BTN_OP_ADD);
    }

    @Override
    public int getPriority() {
        return PRIORITY_ADD_SUB;
    }

    @Override
    public double cal(double... args) {
        return args[0] + args[1];
    }
}
