public class Para<T> {
    private T ws_x;
    private T ws_y;

    public Para(T element1, T element2) {
        this.ws_x = element1;
        this.ws_y = element2;
    }

    public T getWs_x() {
        return ws_x;
    }

    public T getWs_y() {
        return ws_y;
    }
}
