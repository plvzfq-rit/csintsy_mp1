package solver;

public class StateChecker {
    public boolean isStateSolved(State state) {
        for (char i : state.data.toCharArray()) {
            if (i == '$' || i == '.') return false;
        }

        return true;
    }

    public boolean isStateUnsolvable(State state) {
        int h = state.h;
        int w = state.w;
        int upper = h * w - 1;
        String data = state.data;

        for (int i = 0; i < upper; i++) {
            if (data.charAt(i) == '$') {
                // char[] c = { state_[i - w], state_[i - 1], state_[i + w], state_[i + 1] };
                String c =  String.format("%c%c%c%c", data.charAt(i - w), data.charAt(i - 1), data.charAt(i + w), data.charAt(i + 1));

                if (c.contains("##") || (c.charAt(0) == '#' && c.charAt(3) == '#')) 
                    return true;
            }
        }
        

        return false;
    }
}
