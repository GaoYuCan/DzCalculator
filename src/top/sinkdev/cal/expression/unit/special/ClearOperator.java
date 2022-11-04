package top.sinkdev.cal.expression.unit.special;

import top.sinkdev.cal.CalculatorConstants;
import top.sinkdev.cal.expression.Expression;
import top.sinkdev.cal.expression.unit.SpecialOperator;

public class ClearOperator extends SpecialOperator {

    public ClearOperator() {
        super(CalculatorConstants.BTN_OP_CEL);
    }

    @Override
    public void performUnit(Expression expression) {
        expression.clearUnitList();
    }
}
