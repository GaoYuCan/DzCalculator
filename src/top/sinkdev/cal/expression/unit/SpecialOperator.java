package top.sinkdev.cal.expression.unit;



public abstract class SpecialOperator extends ExpressionUnit {

    protected SpecialOperator(String displayText) {
        super(displayText);
    }


    @Override
    public int getType() {
        return TYPE_SPECIAL_OPERATOR;
    }


}
