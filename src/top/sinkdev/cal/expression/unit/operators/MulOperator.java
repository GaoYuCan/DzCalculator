package top.sinkdev.cal.expression.unit.operators;

import top.sinkdev.cal.CalculatorConstants;
import top.sinkdev.cal.expression.unit.Operator;

public class MulOperator extends Operator {
    public MulOperator() {
        super(CalculatorConstants.BTN_OP_MUL);
    }

    @Override
    public int getPriority() {
        return PRIORITY_MUL_DIV;
    }

    @Override
    public double cal(double... args) {
        return args[0] * args[1];
    }
}
