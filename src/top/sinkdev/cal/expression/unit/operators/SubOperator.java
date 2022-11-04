package top.sinkdev.cal.expression.unit.operators;

import top.sinkdev.cal.CalculatorConstants;
import top.sinkdev.cal.expression.unit.Operator;

public class SubOperator extends Operator {
    public SubOperator() {
        super(CalculatorConstants.BTN_OP_SUB);
    }

    @Override
    public int getPriority() {
        return PRIORITY_ADD_SUB;
    }

    @Override
    public double cal(double... args) {
        return args[0] - args[1];
    }
}
