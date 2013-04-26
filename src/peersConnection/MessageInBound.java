package peersConnection;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;


public class MessageInBound extends MessageInbound {

    private final String user;

    public MessageInBound(String user) {
        this.user = user;
    }
    
    public String getUser(){
    	return this.user;
    }

    @Override
    protected void onOpen(WsOutbound outbound) {
	//trigging the connected event, add the connection into pool of connections 
    	InBoundPool.addMsgInBound(this);
    }

    @Override
    protected void onClose(int status) {
    	//trigging closing event, remove the connection from pool of connections.
    	InBoundPool.removeMsgInBound(this);
    }

    @Override
    protected void onBinaryMessage(ByteBuffer message) throws IOException {
        throw new UnsupportedOperationException(
                "Binary message not supported.");
    }

    @Override
    protected void onTextMessage(CharBuffer message) throws IOException {
    	
    }
}
