package utils;

/**
 * 指定传送的数据类型
 * Created by betty.bao on 2017/7/28.
 */
public enum DataType {

    REGISTER_MSG("register", 0), IMAG_GIF("gif", 1), IMAGE_JPEG("jpg", 2), IMAGE_PNG("png", 3), TEXT_PLAIN("text", 4), MEDIA_WMV("wmv", 5);

    private String name;
    private int index;

    // 普通方法
    public static String getType(int index) {
        for (DataType c : DataType.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    DataType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

}
