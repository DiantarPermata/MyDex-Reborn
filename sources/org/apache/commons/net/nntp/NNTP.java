package org.apache.commons.net.nntp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import kotlin.text.Typography;
import org.apache.commons.net.MalformedServerReplyException;
import org.apache.commons.net.ProtocolCommandListener;
import org.apache.commons.net.ProtocolCommandSupport;
import org.apache.commons.net.SocketClient;

/* loaded from: classes2.dex */
public class NNTP extends SocketClient {
    public static final int DEFAULT_PORT = 119;
    private static final String __DEFAULT_ENCODING = "ISO-8859-1";
    private StringBuffer __commandBuffer;
    protected ProtocolCommandSupport _commandSupport_;
    boolean _isAllowedToPost;
    protected BufferedReader _reader_;
    int _replyCode;
    String _replyString;
    protected BufferedWriter _writer_;

    public NNTP() {
        setDefaultPort(119);
        this.__commandBuffer = new StringBuffer();
        this._replyString = null;
        this._reader_ = null;
        this._writer_ = null;
        this._isAllowedToPost = false;
        this._commandSupport_ = new ProtocolCommandSupport(this);
    }

    private void __getReply() throws IOException {
        String readLine = this._reader_.readLine();
        this._replyString = readLine;
        if (readLine == null) {
            throw new NNTPConnectionClosedException("Connection closed without indication.");
        }
        if (readLine.length() < 3) {
            throw new MalformedServerReplyException("Truncated server reply: " + this._replyString);
        }
        try {
            this._replyCode = Integer.parseInt(this._replyString.substring(0, 3));
            if (this._commandSupport_.getListenerCount() > 0) {
                this._commandSupport_.fireReplyReceived(this._replyCode, this._replyString + SocketClient.NETASCII_EOL);
            }
            if (this._replyCode == 400) {
                throw new NNTPConnectionClosedException("NNTP response 400 received.  Server closed connection.");
            }
        } catch (NumberFormatException unused) {
            throw new MalformedServerReplyException("Could not parse response code.\nServer Reply: " + this._replyString);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.apache.commons.net.SocketClient
    public void _connectAction_() throws IOException {
        super._connectAction_();
        this._reader_ = new BufferedReader(new InputStreamReader(this._input_, "ISO-8859-1"));
        this._writer_ = new BufferedWriter(new OutputStreamWriter(this._output_, "ISO-8859-1"));
        __getReply();
        this._isAllowedToPost = this._replyCode == 200;
    }

    public void addProtocolCommandListener(ProtocolCommandListener protocolCommandListener) {
        this._commandSupport_.addProtocolCommandListener(protocolCommandListener);
    }

    public void removeProtocolCommandListener(ProtocolCommandListener protocolCommandListener) {
        this._commandSupport_.removeProtocolCommandListener(protocolCommandListener);
    }

    @Override // org.apache.commons.net.SocketClient
    public void disconnect() throws IOException {
        super.disconnect();
        this._reader_ = null;
        this._writer_ = null;
        this._replyString = null;
        this._isAllowedToPost = false;
    }

    public boolean isAllowedToPost() {
        return this._isAllowedToPost;
    }

    public int sendCommand(String str, String str2) throws IOException {
        this.__commandBuffer.setLength(0);
        this.__commandBuffer.append(str);
        if (str2 != null) {
            this.__commandBuffer.append(' ');
            this.__commandBuffer.append(str2);
        }
        this.__commandBuffer.append(SocketClient.NETASCII_EOL);
        BufferedWriter bufferedWriter = this._writer_;
        String stringBuffer = this.__commandBuffer.toString();
        bufferedWriter.write(stringBuffer);
        this._writer_.flush();
        if (this._commandSupport_.getListenerCount() > 0) {
            this._commandSupport_.fireCommandSent(str, stringBuffer);
        }
        __getReply();
        return this._replyCode;
    }

    public int sendCommand(int i, String str) throws IOException {
        return sendCommand(NNTPCommand._commands[i], str);
    }

    public int sendCommand(String str) throws IOException {
        return sendCommand(str, (String) null);
    }

    public int sendCommand(int i) throws IOException {
        return sendCommand(i, (String) null);
    }

    public int getReplyCode() {
        return this._replyCode;
    }

    public int getReply() throws IOException {
        __getReply();
        return this._replyCode;
    }

    public String getReplyString() {
        return this._replyString;
    }

    public int article(String str) throws IOException {
        return sendCommand(0, str);
    }

    public int article(int i) throws IOException {
        return sendCommand(0, Integer.toString(i));
    }

    public int article() throws IOException {
        return sendCommand(0);
    }

    public int body(String str) throws IOException {
        return sendCommand(1, str);
    }

    public int body(int i) throws IOException {
        return sendCommand(1, Integer.toString(i));
    }

    public int body() throws IOException {
        return sendCommand(1);
    }

    public int head(String str) throws IOException {
        return sendCommand(3, str);
    }

    public int head(int i) throws IOException {
        return sendCommand(3, Integer.toString(i));
    }

    public int head() throws IOException {
        return sendCommand(3);
    }

    public int stat(String str) throws IOException {
        return sendCommand(14, str);
    }

    public int stat(int i) throws IOException {
        return sendCommand(14, Integer.toString(i));
    }

    public int stat() throws IOException {
        return sendCommand(14);
    }

    public int group(String str) throws IOException {
        return sendCommand(2, str);
    }

    public int help() throws IOException {
        return sendCommand(4);
    }

    public int ihave(String str) throws IOException {
        return sendCommand(5, str);
    }

    public int last() throws IOException {
        return sendCommand(6);
    }

    public int list() throws IOException {
        return sendCommand(7);
    }

    public int next() throws IOException {
        return sendCommand(10);
    }

    public int newgroups(String str, String str2, boolean z, String str3) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(' ');
        sb.append(str2);
        if (z) {
            sb.append(' ');
            sb.append("GMT");
        }
        if (str3 != null) {
            sb.append(" <");
            sb.append(str3);
            sb.append(Typography.greater);
        }
        return sendCommand(8, sb.toString());
    }

    public int newnews(String str, String str2, String str3, boolean z, String str4) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(' ');
        sb.append(str2);
        sb.append(' ');
        sb.append(str3);
        if (z) {
            sb.append(' ');
            sb.append("GMT");
        }
        if (str4 != null) {
            sb.append(" <");
            sb.append(str4);
            sb.append(Typography.greater);
        }
        return sendCommand(9, sb.toString());
    }

    public int post() throws IOException {
        return sendCommand(11);
    }

    public int quit() throws IOException {
        return sendCommand(12);
    }

    public int authinfoUser(String str) throws IOException {
        return sendCommand(15, "USER " + str);
    }

    public int authinfoPass(String str) throws IOException {
        return sendCommand(15, "PASS " + str);
    }

    public int xover(String str) throws IOException {
        return sendCommand(16, str);
    }

    public int xhdr(String str, String str2) throws IOException {
        return sendCommand(17, str + " " + str2);
    }

    public int listActive(String str) throws IOException {
        return sendCommand(7, "ACTIVE " + str);
    }
}
