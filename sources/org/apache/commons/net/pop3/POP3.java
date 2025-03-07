package org.apache.commons.net.pop3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.Vector;
import org.apache.commons.net.MalformedServerReplyException;
import org.apache.commons.net.ProtocolCommandListener;
import org.apache.commons.net.ProtocolCommandSupport;
import org.apache.commons.net.SocketClient;

/* loaded from: classes2.dex */
public class POP3 extends SocketClient {
    public static final int AUTHORIZATION_STATE = 0;
    public static final int DEFAULT_PORT = 110;
    public static final int DISCONNECTED_STATE = -1;
    public static final int TRANSACTION_STATE = 1;
    public static final int UPDATE_STATE = 2;
    static final String _ERROR = "-ERR";
    static final String _OK = "+OK";
    private static final String __DEFAULT_ENCODING = "ISO-8859-1";
    private StringBuffer __commandBuffer;
    private int __popState;
    private BufferedWriter __writer;
    protected ProtocolCommandSupport _commandSupport_;
    String _lastReplyLine;
    BufferedReader _reader;
    int _replyCode;
    Vector<String> _replyLines;

    public POP3() {
        setDefaultPort(110);
        this.__commandBuffer = new StringBuffer();
        this.__popState = -1;
        this._reader = null;
        this.__writer = null;
        this._replyLines = new Vector<>();
        this._commandSupport_ = new ProtocolCommandSupport(this);
    }

    private void __getReply() throws IOException {
        this._replyLines.setSize(0);
        String readLine = this._reader.readLine();
        if (readLine == null) {
            throw new EOFException("Connection closed without indication.");
        }
        if (readLine.startsWith(_OK)) {
            this._replyCode = 0;
        } else if (readLine.startsWith(_ERROR)) {
            this._replyCode = 1;
        } else {
            throw new MalformedServerReplyException("Received invalid POP3 protocol response from server.");
        }
        this._replyLines.addElement(readLine);
        this._lastReplyLine = readLine;
        if (this._commandSupport_.getListenerCount() > 0) {
            this._commandSupport_.fireReplyReceived(this._replyCode, getReplyString());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.apache.commons.net.SocketClient
    public void _connectAction_() throws IOException {
        super._connectAction_();
        this._reader = new BufferedReader(new InputStreamReader(this._input_, "ISO-8859-1"));
        this.__writer = new BufferedWriter(new OutputStreamWriter(this._output_, "ISO-8859-1"));
        __getReply();
        setState(0);
    }

    public void addProtocolCommandListener(ProtocolCommandListener protocolCommandListener) {
        this._commandSupport_.addProtocolCommandListener(protocolCommandListener);
    }

    public void removeProtocolCommandistener(ProtocolCommandListener protocolCommandListener) {
        this._commandSupport_.removeProtocolCommandListener(protocolCommandListener);
    }

    public void setState(int i) {
        this.__popState = i;
    }

    public int getState() {
        return this.__popState;
    }

    public void getAdditionalReply() throws IOException {
        String readLine = this._reader.readLine();
        while (readLine != null) {
            this._replyLines.addElement(readLine);
            if (readLine.equals(".")) {
                return;
            } else {
                readLine = this._reader.readLine();
            }
        }
    }

    @Override // org.apache.commons.net.SocketClient
    public void disconnect() throws IOException {
        super.disconnect();
        this._reader = null;
        this.__writer = null;
        this._lastReplyLine = null;
        this._replyLines.setSize(0);
        setState(-1);
    }

    public int sendCommand(String str, String str2) throws IOException {
        this.__commandBuffer.setLength(0);
        this.__commandBuffer.append(str);
        if (str2 != null) {
            this.__commandBuffer.append(' ');
            this.__commandBuffer.append(str2);
        }
        this.__commandBuffer.append(SocketClient.NETASCII_EOL);
        BufferedWriter bufferedWriter = this.__writer;
        String stringBuffer = this.__commandBuffer.toString();
        bufferedWriter.write(stringBuffer);
        this.__writer.flush();
        if (this._commandSupport_.getListenerCount() > 0) {
            this._commandSupport_.fireCommandSent(str, stringBuffer);
        }
        __getReply();
        return this._replyCode;
    }

    public int sendCommand(String str) throws IOException {
        return sendCommand(str, (String) null);
    }

    public int sendCommand(int i, String str) throws IOException {
        return sendCommand(POP3Command._commands[i], str);
    }

    public int sendCommand(int i) throws IOException {
        return sendCommand(POP3Command._commands[i], (String) null);
    }

    public String[] getReplyStrings() {
        String[] strArr = new String[this._replyLines.size()];
        this._replyLines.copyInto(strArr);
        return strArr;
    }

    public String getReplyString() {
        StringBuilder sb = new StringBuilder(256);
        Enumeration<String> elements = this._replyLines.elements();
        while (elements.hasMoreElements()) {
            sb.append(elements.nextElement());
            sb.append(SocketClient.NETASCII_EOL);
        }
        return sb.toString();
    }
}
