package gnu.kawa.sax;

import gnu.text.LineBufferedReader;
import gnu.text.SourceMessages;
import gnu.xml.XMLFilter;
import gnu.xml.XMLParser;
import java.io.IOException;
import java.io.Reader;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

/* loaded from: classes.dex */
public class KawaXMLReader extends ContentConsumer implements XMLReader {
    ErrorHandler errorHandler;

    @Override // org.xml.sax.XMLReader
    public boolean getFeature(String name) {
        return false;
    }

    @Override // org.xml.sax.XMLReader
    public void setFeature(String name, boolean value) {
    }

    @Override // org.xml.sax.XMLReader
    public Object getProperty(String name) {
        return null;
    }

    @Override // org.xml.sax.XMLReader
    public void setProperty(String name, Object value) {
    }

    @Override // org.xml.sax.XMLReader
    public void setEntityResolver(EntityResolver resolver) {
    }

    @Override // org.xml.sax.XMLReader
    public EntityResolver getEntityResolver() {
        return null;
    }

    @Override // org.xml.sax.XMLReader
    public void setDTDHandler(DTDHandler handler) {
    }

    @Override // org.xml.sax.XMLReader
    public DTDHandler getDTDHandler() {
        return null;
    }

    @Override // org.xml.sax.XMLReader
    public void setErrorHandler(ErrorHandler handler) {
        this.errorHandler = handler;
    }

    @Override // org.xml.sax.XMLReader
    public ErrorHandler getErrorHandler() {
        return this.errorHandler;
    }

    @Override // org.xml.sax.XMLReader
    public void parse(InputSource input) throws IOException, SAXException {
        Reader reader = input.getCharacterStream();
        if (reader == null) {
            reader = XMLParser.XMLStreamReader(input.getByteStream());
        }
        SourceMessages messages = new SourceMessages();
        XMLFilter filter = new XMLFilter(this);
        LineBufferedReader lin = new LineBufferedReader(reader);
        filter.setSourceLocator(lin);
        getContentHandler().setDocumentLocator(filter);
        XMLParser.parse(lin, messages, filter);
        String err = messages.toString(20);
        if (err != null) {
            throw new SAXParseException(err, filter);
        }
    }

    @Override // org.xml.sax.XMLReader
    public void parse(String systemId) {
    }
}
