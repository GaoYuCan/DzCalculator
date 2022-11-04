package top.sinkdev.cal.expression.unit.special;

import top.sinkdev.cal.CalculatorConstants;
import top.sinkdev.cal.expression.Expression;
import top.sinkdev.cal.expression.unit.SpecialOperator;

public class DeleteOperator extends SpecialOperator {

    public DeleteOperator() {
        super(CalculatorConstants.BTN_OP_DEL);
    }

    @Override
    public void performUnit(Expression expression) {
        if (!expression.isEmptyExpression()) {
            expression.popUnit();
        }
    }

}
