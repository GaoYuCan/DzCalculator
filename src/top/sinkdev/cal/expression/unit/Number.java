package top.sinkdev.cal.expression.unit;

import top.sinkdev.cal.CalculatorConstants;
import top.sinkdev.cal.expression.Expression;

public class Number extends ExpressionUnit {

    public Number(String displayText) {
        super(displayText);
    }

    @Override
    public int getType() {
        return TYPE_NUMBER;
    }

    @Override
    public void performUnit(Expression expression) {
        if (CalculatorConstants.BTN_NUM_POINT.equals(getDisplayText())) {
            // 遍历当前表达式
            int size = expression.getSize();
            boolean preHasNumber = false;
            for (int i = size - 1; i >= 0; i--) {
                ExpressionUnit unit = expression.get(i);
                int unitType = unit.getType();
                if (unitType != TYPE_NUMBER) {
                    break;
                }
                // 数字类型
                if (CalculatorConstants.BTN_NUM_POINT.equals(unit.getDisplayText())) {
                    // 前面有 . 了，输入非法
                    return;
                }
                preHasNumber = true;
            }
            if (!preHasNumber) {
                expression.pushUnit(new Number(CalculatorConstants.BTN_NUM_0));
            }
        }
        expression.pushUnit(this);
    }
}
