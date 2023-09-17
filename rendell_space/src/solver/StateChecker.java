package solver;

public class StateChecker {
    public boolean isStateSolved(String state) {
        for (char i : state.toCharArray()) {
            if (i == '$' || i == '.') return false;
        }

        return true;
    }

    public boolean isStateUnsolvable(String state) {
        char[] state_ = state.toCharArray();
        int h = Character.getNumericValue(state_[0]);
        int w = Character.getNumericValue(state_[1]);
        int lower = 2;
        int upper = h * w + 1;

        for (int i = 2; i < upper; i++) {
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
