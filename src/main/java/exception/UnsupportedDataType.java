package exception;

/**
 * 当传输的数据类型不在已有的DataType中时抛出的异常
 * Created by betty.bao on 2017/7/28.
 */
public class UnsupportedDataType extends EnumConstantNotPresentException {
    private static final long serialVersionUID = -9130197827153789500L;

    public UnsupportedDataType(Class<? extends Enum> enumType, String constantName) {
        super(enumType, constantName);
        this.setErrorInfo(constantName);
    }

    public void setErrorInfo(String info){

    }
}
