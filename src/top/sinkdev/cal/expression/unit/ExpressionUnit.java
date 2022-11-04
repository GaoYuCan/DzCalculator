package top.sinkdev.cal.expression.unit;

import org.jetbrains.annotations.NotNull;
import top.sinkdev.cal.CalculatorConstants;
import top.sinkdev.cal.expression.Expression;
import top.sinkdev.cal.expression.unit.operators.*;
import top.sinkdev.cal.expression.unit.special.ClearOperator;
import top.sinkdev.cal.expression.unit.special.CoreOperator;
import top.sinkdev.cal.expression.unit.special.DeleteOperator;
import top.sinkdev.cal.expression.unit.special.EqualOperator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class ExpressionUnit {
    public static final int TYPE_OPERATOR = 0;
    public static final int TYPE_NUMBER = 1;

    public static final int TYPE_SPECIAL_OPERATOR = 2;

    private final String displayText;

    public ExpressionUnit(String displayText) {
        this.displayText = displayText;
    }

    public abstract int getType();


    public String getDisplayText() {
        return displayText;
    }

    public abstract void performUnit(Expression expression);

    public static class Factory {
        private static final Set<String> numberSet = new HashSet<>();
        private static final Map<String, Class<? extends ExpressionUnit>> operatorClassMap = new HashMap<>();

        static {
            numberSet.add(CalculatorConstants.BTN_NUM_0);
            numberSet.add(CalculatorConstants.BTN_NUM_1);
            numberSet.add(CalculatorConstants.BTN_NUM_2);
            numberSet.add(CalculatorConstants.BTN_NUM_3);
            numberSet.add(CalculatorConstants.BTN_NUM_4);
            numberSet.add(CalculatorConstants.BTN_NUM_5);
            numberSet.add(CalculatorConstants.BTN_NUM_6);
            numberSet.add(CalculatorConstants.BTN_NUM_7);
            numberSet.add(CalculatorConstants.BTN_NUM_8);
            numberSet.add(CalculatorConstants.BTN_NUM_9);
            numberSet.add(CalculatorConstants.BTN_NUM_POINT);

            operatorClassMap.put(CalculatorConstants.BTN_DING_ZHEN, CoreOperator.class);
            operatorClassMap.put(CalculatorConstants.BTN_OP_DEL, DeleteOperator.class);
            operatorClassMap.put(CalculatorConstants.BTN_OP_CEL, ClearOperator.class);
            operatorClassMap.put(CalculatorConstants.BTN_OP_EQ, EqualOperator.class);

            operatorClassMap.put(CalculatorConstants.BTN_OP_ADD, AddOperator.class);
            operatorClassMap.put(CalculatorConstants.BTN_OP_SUB, SubOperator.class);
            operatorClassMap.put(CalculatorConstants.BTN_OP_MUL, MulOperator.class);
            operatorClassMap.put(CalculatorConstants.BTN_OP_DIV, DivOperator.class);
            operatorClassMap.put(CalculatorConstants.BTN_OP_MOD, ModOperator.class);
        }

        public static ExpressionUnit createExpressionUnit(@NotNull String displayText) {

            if (numberSet.contains(displayText)) {
                return new Number(displayText);
            }
            if (operatorClassMap.containsKey(displayText)) {
                try {
                   return operatorClassMap.get(displayText).getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            return null;
        }
    }
}
