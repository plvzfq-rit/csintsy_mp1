package solver;
public class State {
    public int h;
    public int w;
    public String data;
    public State(String data, int h, int w) {
        this.data = data;
        this.h = h;
        this.w = w;
    }
}