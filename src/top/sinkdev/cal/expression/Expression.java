package top.sinkdev.cal.expression;

import top.sinkdev.cal.expression.unit.ExpressionUnit;

import java.util.Stack;

public class Expression {
    private final Stack<ExpressionUnit> unitList;

    public Expression() {
        this.unitList = new Stack<>();
    }

    public void op(String op) {
        ExpressionUnit unit = ExpressionUnit.Factory.createExpressionUnit(op);
        if (unit != null) {
            unit.performUnit(this);
        }
    }

    public ExpressionUnit popUnit() {
        return isEmptyExpression() ? null : unitList.pop();
    }

    public void clearUnitList() {
        unitList.clear();
    }

    public ExpressionUnit topUnit() {
        return isEmptyExpression() ? null : unitList.peek();
    }

    public void pushUnit(ExpressionUnit unit) {
        unitList.push(unit);
    }

    public boolean isEmptyExpression() {
        return unitList.isEmpty();
    }

    public int getSize() {
        return unitList.size();
    }

    public ExpressionUnit get(int index) {
        return unitList.get(index);
    }


    public String getExpression() {
        if (isEmptyExpression()) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        unitList.forEach(unit -> {
            sb.append(unit.getDisplayText());
        });
        return sb.toString();
    }
}
