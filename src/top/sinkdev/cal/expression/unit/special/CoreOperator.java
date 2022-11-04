package top.sinkdev.cal.expression.unit.special;

import top.sinkdev.cal.CalculatorConstants;
import top.sinkdev.cal.utils.MusicPlayer;
import top.sinkdev.cal.expression.Expression;
import top.sinkdev.cal.expression.unit.SpecialOperator;

import java.io.File;

public class CoreOperator extends SpecialOperator {

    public CoreOperator() {
        super(CalculatorConstants.BTN_DING_ZHEN);
    }

    @Override
    public void performUnit(Expression expression) {
        MusicPlayer.playMusic(new File("res/xuebao.wav"));
    }

}
