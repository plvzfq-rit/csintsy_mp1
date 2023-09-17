package solver;

public class StateChecker {
    public boolean isStateSolved(State state) {
        for (char i : state.data.toCharArray()) {
            if (i == '$' || i == '.') return false;
        }

        return true;
    }

    public boolean isStateUnsolvable(State state) {
        char[] state_ = state.data.toCharArray();
        int h = state.h;
        int w = state.w;
        int upper = h * w - 1;

        for (int i = 0; i < upper; i++) {
            if (state_[i] == '$') {
                char[] c = { state_[i - w], state_[i - 1], state_[i + w], state_[i + 1] };
                String check = new String(c);

                if (check.contains("##") || (c[0] == '#' && c[3] == '#')) 
                    return true;
            }
        }
        

        return false;
    }
}
