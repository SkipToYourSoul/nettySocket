package pojo;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 传输协议类
 * Created by betty.bao on 2017/7/30.
 */
public class Message {

    //报文的数据类型 dataType， 具体值可参考类DataType
    private byte dataType;

    //报文的长度
    private int length;

    //报文主体
    private byte[] body;

    public Message(byte type, int length, byte[] body) {
        this.dataType = type;
        this.length = length;
        this.body = body;
    }

    public Message(){}

    public byte getDataType() {
        return dataType;
    }

    public void setDataType(byte dataType) {
        this.dataType = dataType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
