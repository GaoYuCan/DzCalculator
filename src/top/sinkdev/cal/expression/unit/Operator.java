package top.sinkdev.cal.expression.unit;

import top.sinkdev.cal.CalculatorConstants;
import top.sinkdev.cal.expression.Expression;

public abstract class Operator extends ExpressionUnit {

    public static final int PRIORITY_ADD_SUB = 0;
    public static final int PRIORITY_MUL_DIV = 1;

    protected Operator(String displayText) {
        super(displayText);
    }


    @Override
    public int getType() {
        return TYPE_OPERATOR;
    }

    @Override
    public void performUnit(Expression expression) {
        ExpressionUnit expressionUnit = expression.topUnit();
        if (expressionUnit == null) {
            expression.pushUnit(new Number(CalculatorConstants.BTN_NUM_0));
        } else if (expressionUnit.getType() == TYPE_OPERATOR) {
            expression.popUnit();
        }
        expression.pushUnit(this);
    }

    public abstract int getPriority();

    public abstract double cal(double... args);
}
