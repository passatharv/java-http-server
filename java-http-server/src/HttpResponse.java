public class HttpResponse {

    private int statusCode;
    private String contentType;
    private byte[] body;

    public HttpResponse(int statusCode,
                        String contentType,
                        byte[] body) {
        this.statusCode = statusCode;
        this.contentType = contentType;
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getContentType() {
        return contentType;
    }

    public byte[] getBody() {
        return body;
    }
}