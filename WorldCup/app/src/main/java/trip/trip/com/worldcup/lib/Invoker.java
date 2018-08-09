package trip.trip.com.worldcup.lib;

public class Invoker {
    private Command[] commands;
    public static final int SLOT_GAMESTART = 0;
    public static final int SLOT_PREROUND = 1;
    public static final int SLOT_LOADCHOICE = 2;
    public static final int SLOT_SELECTLEFT = 3;
    public static final int SLOT_SELECTRIGHT = 4;
    public static final int SLOT_GAMEROUNDSIXTEEN = 5;
    public static final int SLOT_GAMEROUNDTHIRTYTWO = 6;
    public static final int SLOT_GOHOMT=7;
    private Invoker() {
        commands = new Command[10];
    }


    private static final class InvokerHolder {
        private static final Invoker instance = new Invoker();
    }

    public static Invoker getInstance() {
        return InvokerHolder.instance;
    }


    public boolean setCommand(int slot, Command command) {
        if (0 <= slot && slot < commands.length) {
            commands[slot] = command;
            return true;
        } else {
            return false;
        }
    }

    public boolean runCommand(int slot){
        if (0 <= slot && slot < commands.length && commands[slot] != null) {
            commands[slot].action();
            return true;
        } else {
            return false;
        }
    }
}
