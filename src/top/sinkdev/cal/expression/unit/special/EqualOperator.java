package top.sinkdev.cal.expression.unit.special;

import top.sinkdev.cal.CalculatorConstants;
import top.sinkdev.cal.expression.Expression;
import top.sinkdev.cal.expression.unit.ExpressionUnit;
import top.sinkdev.cal.expression.unit.Number;
import top.sinkdev.cal.expression.unit.Operator;
import top.sinkdev.cal.expression.unit.SpecialOperator;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Stack;


public class EqualOperator extends SpecialOperator {
    public EqualOperator() {
        super(CalculatorConstants.BTN_OP_EQ);
    }

    @Override
    public void performUnit(Expression expression) {
        ExpressionUnit lastUnit = null;
        if (expression.topUnit().getType() != TYPE_NUMBER) {
            lastUnit = expression.popUnit();
        }

        // 1. 使用 逆波兰法 求后缀表达式
        ArrayList<ExpressionUnit> L = new ArrayList<>();
        Stack<Operator> O = new Stack<>();
        int expressionSize = expression.getSize();
        // 临时数字
        StringBuilder tempNumber = new StringBuilder();
        for (int i = 0; i < expressionSize; i++) {
            ExpressionUnit unit = expression.get(i);
            if (unit.getType() == TYPE_NUMBER) {
                tempNumber.append(unit.getDisplayText());
            } else if (unit.getType() == TYPE_OPERATOR) {
                if (!tempNumber.isEmpty()) {
                    L.add(new Number(tempNumber.toString()));
                    tempNumber = new StringBuilder();
                }
                // 判断操作符优先级
                while (!O.isEmpty() && O.peek().getPriority() >= ((Operator) unit).getPriority()) {
                    L.add(O.pop());
                }
                O.push((Operator) unit);
            }
        }
        if (!tempNumber.isEmpty()) {
            L.add(new Number(tempNumber.toString()));
        }
        while (!O.isEmpty()) {
            L.add(O.pop());
        }

        // 2. 根据后缀表达式计算结果
        Stack<Double> calStack = new Stack<>();
        for (ExpressionUnit unit : L) {
            if (unit.getType() == TYPE_NUMBER) {
                calStack.push(Double.parseDouble(unit.getDisplayText()));
            } else if (unit.getType() == TYPE_OPERATOR) {
                Operator operator = (Operator) unit;
                Double b = calStack.pop();
                Double a = calStack.pop();
                calStack.push(operator.cal(a, b));
            }
        }
        expression.clearUnitList();
        Double result = calStack.pop();
        NumberFormat numberFormat = new DecimalFormat("0.0#######");
        String resultStr = numberFormat.format(result);
        for (int i = 0; i < resultStr.length(); i++) {
            expression.pushUnit(new Number(resultStr.charAt(i) + ""));
        }
        if (lastUnit != null) {
            expression.pushUnit(lastUnit);
        }
    }
}
