package javastuff.designPatterns;
// behavorial pattern
// similar to strategy Pattern which is able to switch a strategy invocations of methods
// Follows  Single Responsibility Principle and Open/Closed Principle
public class EnumFSM {
    //https://www.baeldung.com/java-enum-simple-state-machine
    public static void main(String[] args) {
        LeaveRequestState state = LeaveRequestState.Submitted;

        state = state.nextState();
        //assertEquals(LeaveRequestState.Escalated, state);

        state = state.nextState();
        //assertEquals(LeaveRequestState.Approved, state);

        state = state.nextState();
        //assertEquals(LeaveRequestState.Approved, state);
    }
}
enum LeaveRequestState {

    Submitted {
        @Override
        public LeaveRequestState nextState() {
            return Escalated;
        }

        @Override
        public String responsiblePerson() {
            return "Employee";
        }
    },
    Escalated {
        @Override
        public LeaveRequestState nextState() {
            return Approved;
        }

        @Override
        public String responsiblePerson() {
            return "Team Leader";
        }
    },
    Approved {
        @Override
        public LeaveRequestState nextState() {
            return this;
        }

        @Override
        public String responsiblePerson() {
            return "Department Manager";
        }
    };

    public abstract LeaveRequestState nextState();
    public abstract String responsiblePerson();
}